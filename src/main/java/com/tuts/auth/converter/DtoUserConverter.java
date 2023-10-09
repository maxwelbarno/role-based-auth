package com.tuts.auth.converter;

import org.springframework.core.convert.converter.Converter;

import com.tuts.auth.DTO.UserDto;
import com.tuts.auth.models.User;

public class DtoUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        User user = new User();
        user.setName(source.username());
        user.setUsername(source.username());
        user.setRoles(source.roles());
        user.setTokens(source.tokens());
        return user;
    }

}
