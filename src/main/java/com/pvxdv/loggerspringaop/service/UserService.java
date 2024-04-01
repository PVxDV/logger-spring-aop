package com.pvxdv.loggerspringaop.service;



import com.pvxdv.loggerspringaop.dto.CreateOrUpdateUserDto;
import com.pvxdv.loggerspringaop.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(CreateOrUpdateUserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    void updateUserById(Long userId, CreateOrUpdateUserDto userDto);

    void deleteUserById(Long userId);
}
