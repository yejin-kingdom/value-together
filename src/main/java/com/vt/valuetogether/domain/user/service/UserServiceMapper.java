package com.vt.valuetogether.domain.user.service;

import com.vt.valuetogether.domain.user.dto.response.UserUpdateUsernameRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserServiceMapper {
    UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

}
