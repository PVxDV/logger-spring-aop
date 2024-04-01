package com.pvxdv.loggerspringaop.api;


import com.pvxdv.loggerspringaop.dto.CreateOrUpdateUserDto;
import com.pvxdv.loggerspringaop.dto.UserDto;
import com.pvxdv.loggerspringaop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserController", description = "CRUD operations with User Entity")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Create new user",
            description = "Create new user"
    )
    @PostMapping
    public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody CreateOrUpdateUserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all users",
            description = "Get all users"
    )
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get current user",
            description = "Get current user by user_id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") @Min(0) Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update current user",
            description = "Update current user by user_id"
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserById(@PathVariable("id") @Min(0) Long id,
                               @Valid @RequestBody CreateOrUpdateUserDto userDto) {
        userService.updateUserById(id, userDto);
    }

    @Operation(
            summary = "Delete current category",
            description = "Delete current user by user_id"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") @Min(0) Long id) {
        userService.deleteUserById(id);
    }

}
