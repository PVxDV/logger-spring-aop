package com.pvxdv.loggerspringaop.api;

import com.pvxdv.loggerspringaop.dto.CreateOrUpdateUserDto;
import com.pvxdv.loggerspringaop.dto.OrderDto;
import com.pvxdv.loggerspringaop.dto.UserDto;
import com.pvxdv.loggerspringaop.enums.OrderStatus;
import com.pvxdv.loggerspringaop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private CreateOrUpdateUserDto createOrUpdateUserDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        createOrUpdateUserDto = new CreateOrUpdateUserDto("TestName1", "test1@mail.ru");
        userDto = new UserDto(1L, "TestName2", "test2@mail.ru",
                List.of(new OrderDto(1L, "some details", OrderStatus.COMPLETED, 2L)));
    }

    @Test
    void createNewUserSuccessfully() throws Exception {
        UserDto response = new UserDto(1L, "TestName2", "test2@mail.ru", new ArrayList<>());
        Mockito.when(userService.createUser(Mockito.any(CreateOrUpdateUserDto.class))).thenReturn(response);


        String content = """
                {
                  "name": "ivan",
                  "email": "ivan@mail.ru"
                }
                """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("TestName2"))
                .andExpect(jsonPath("$.email").value("test2@mail.ru"))
                .andExpect(jsonPath("$.orders", hasSize(0)));
    }

    @Test
    void getAllUsersSuccessfully() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("TestName2"))
                .andExpect(jsonPath("$[0].email").value("test2@mail.ru"))
                .andExpect(jsonPath("$[0].orders", hasSize(1)));
    }

    @Test
    void getUserByIdSuccessfully() throws Exception {
        Mockito.when(userService.getUserById(userDto.id())).thenReturn(userDto);
        mockMvc.perform(get("/api/v1/users/" + userDto.id()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("TestName2"))
                .andExpect(jsonPath("$.email").value("test2@mail.ru"))
                .andExpect(jsonPath("$.orders", hasSize(1)));
    }

    @Test
    void updateUserByIdSuccessfully() throws Exception {
        Mockito.doNothing().when(userService).updateUserById( 1L, createOrUpdateUserDto);

        String content = """
                {
                  "name": "ivanUpd",
                  "email": "ivanUpd@mail.ru"
                }
                """;

        mockMvc.perform(put("/api/v1/users/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteUserByIdSuccessfully() throws Exception {
        Mockito.doNothing().when(userService).deleteUserById(1L);
        mockMvc.perform(delete("/api/v1/users/" + 1L))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void createNewUserFailedWith500() {
        //todo
    }

    @Test
    void createNewUserFailedWith400() {
        //todo
    }

    @Test
    void getAllUsersFailedWith500() {
        //todo
    }

    @Test
    void getAllUsersFailedWith400() {
        //todo
    }

    @Test
    void getUserByIdFailedWith500() {
        //todo
    }

    @Test
    void getUserByIdFailedWith400() {
        //todo
    }

    @Test
    void updateUserByIdFailedWith500() {
        //todo
    }

    @Test
    void updateUserByIdFailedWith400() {
        //todo
    }

    @Test
    void deleteUserByIdFailedWith500() {
        //todo
    }

    @Test
    void deleteUserByIdFailedWith400() {
        //todo
    }
}