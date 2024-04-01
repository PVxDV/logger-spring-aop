package com.pvxdv.loggerspringaop.mapper.impl;

import com.pvxdv.loggerspringaop.dto.CreateOrUpdateUserDto;
import com.pvxdv.loggerspringaop.mapper.Mapper;
import com.pvxdv.loggerspringaop.model.User;
import org.springframework.stereotype.Component;

@Component
public class CreateOrUpdateUserDtoToUserMapper implements Mapper<CreateOrUpdateUserDto, User> {

    @Override
    public User map(CreateOrUpdateUserDto userDto) {

        return User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .build();

    }
}
