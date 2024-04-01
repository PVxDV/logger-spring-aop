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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CreateOrUpdateUserDtoToUserMapper createOrUpdateUserDtoToUserMapper;
    private final UserToUserDtoMapper userToUserDtoMapper;
    private final String USER_NOT_FOUND = "User with id=%d not found";
    private final String SAVE_USER = "Saving user=%s";

    @Override
    public UserDto createUser(CreateOrUpdateUserDto userDto) {
        User userToSave = createOrUpdateUserDtoToUserMapper.map(userDto);
        log.debug(SAVE_USER.formatted(userToSave));

        return userToUserDtoMapper.map(userRepository.save(userToSave));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(userToUserDtoMapper::map)
                .toList();
        log.debug("Find users: %s".formatted(users));
        return users;
    }

    @Override
    public UserDto getUserById(Long userId) {
        UserDto user = userToUserDtoMapper.map(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(userId))));
        log.debug("Find user: %s".formatted(user));
        return user;
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

            log.debug(SAVE_USER.formatted(userToUpdate));
            userRepository.save(userToUpdate);
        } else {
            throw new ResourceNotFoundException(USER_NOT_FOUND.formatted(userId));
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userExist(userId)) {
            userRepository.deleteById(userId);
            log.debug("User with id=%d delete successfully".formatted(userId));
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
