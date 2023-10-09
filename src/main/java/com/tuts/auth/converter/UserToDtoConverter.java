package com.tuts.auth.converter;

import org.springframework.core.convert.converter.Converter;

import com.tuts.auth.DTO.UserDto;
import com.tuts.auth.models.User;

public class UserToDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        final UserDto userDto = new UserDto(source.getId(), source.getName(), source.getUsername(), source.getRoles(),
                source.getTokens());
        return userDto;
    }

}
