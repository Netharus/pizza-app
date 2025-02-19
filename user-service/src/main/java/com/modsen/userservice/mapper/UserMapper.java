package com.modsen.userservice.mapper;

import com.modsen.userservice.domain.User;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User fromUserCreateDto(UsersCreateDto userCreateDto);

    UsersResponseDto fromUserToUsersResponseDto(User user);
}
