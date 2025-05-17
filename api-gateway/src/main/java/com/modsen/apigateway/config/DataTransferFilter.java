package com.modsen.apigateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DataTransferFilter extends AbstractGatewayFilterFactory<DataTransferFilter.Config> {

    private final WebClient webClient;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.introspection-uri}")
    private String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String secret;

    public DataTransferFilter(WebClient webClient) {
        super(Config.class);
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String opaqueToken = authHeader.substring(7);

                return introspectToken(opaqueToken)
                        .flatMap(jwt -> {
                            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                                    .build();
                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
                        });
            }

            return chain.filter(exchange);
        };
    }

    private Mono<String> introspectToken(String token) {
        return webClient.post()
                .uri(introspectionUri)
                .header(HttpHeaders.ACCEPT, "application/jwt")
                .body(BodyInserters.fromFormData("token", token)
                        .with("client_id", clientId)
                        .with("client_secret", secret))
                .retrieve()
                .bodyToMono(TokenIntrospectionResponse.class)
                .map(TokenIntrospectionResponse::getJwt);
    }

    @Override
    public String name() {
        return "DataTransferFilter";
    }

    public static class Config {
    }

    @Data
    private static class TokenIntrospectionResponse {
        private String jwt;
    }
}

