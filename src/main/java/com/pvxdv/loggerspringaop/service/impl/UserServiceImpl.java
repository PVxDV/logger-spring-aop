package com.pvxdv.loggerspringaop.service.impl;


import com.pvxdv.loggerspringaop.dto.CreateOrUpdateUserDto;
import com.pvxdv.loggerspringaop.dto.UserDto;
import com.pvxdv.loggerspringaop.exception.ResourceNotFoundException;
import com.pvxdv.loggerspringaop.mapper.impl.CreateOrUpdateUserDtoToUserMapper;
import com.pvxdv.loggerspringaop.mapper.impl.UserToUserDtoMapper;
import com.pvxdv.loggerspringaop.model.User;
import com.pvxdv.loggerspringaop.repository.UserRepository;
import com.pvxdv.loggerspringaop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CreateOrUpdateUserDtoToUserMapper createOrUpdateUserDtoToUserMapper;
    private final UserToUserDtoMapper userToUserDtoMapper;
    private final String USER_NOT_FOUND = "User with id=%d not found";

    @Override
    public UserDto createUser(CreateOrUpdateUserDto userDto) {
        User userToSave = createOrUpdateUserDtoToUserMapper.map(userDto);
        return userToUserDtoMapper.map(userRepository.save(userToSave));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return  userRepository.findAll().stream().map(userToUserDtoMapper::map).toList();
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userToUserDtoMapper.map(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(userId))));
    }

    @Override
    public void updateUserById(Long userId, CreateOrUpdateUserDto userDto) {
        if (userExist(userId)) {
            User userToUpdate = userRepository.findById(userId).get();

            if (userDto.name() != null) {
                userToUpdate.setName(userDto.name());
            }

            if (userDto.email() != null) {
                userToUpdate.setEmail(userDto.email());
            }

            userRepository.save(userToUpdate);
        } else {
            throw new ResourceNotFoundException(USER_NOT_FOUND.formatted(userId));
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userExist(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException(USER_NOT_FOUND.formatted(userId));
        }
    }

    private Boolean userExist(Long userId) {
        if (userId != null) {
            return userRepository.findById(userId).isPresent();
        } else {
            return false;
        }
    }
}
