package com.modsen.userservice.mapper;

import com.modsen.userservice.domain.User;
import com.modsen.userservice.dto.PageContainerDto;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;
import com.modsen.userservice.dto.UsersUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User fromUserCreateDto(UsersCreateDto userCreateDto);

    UsersResponseDto fromUserToUsersResponseDto(User user);

    User fromUserUpdateDto(UsersUpdateDto userUpdateDto);

    @Mapping(target = "pageNum", source = "number")
    PageContainerDto<UsersResponseDto> toUsersPageContainerDto(Page<User> users);
}
