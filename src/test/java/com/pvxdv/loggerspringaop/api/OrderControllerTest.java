package com.pvxdv.loggerspringaop.api;

import com.pvxdv.loggerspringaop.dto.OrderDto;
import com.pvxdv.loggerspringaop.enums.OrderStatus;
import com.pvxdv.loggerspringaop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto(1L, "some details", OrderStatus.COMPLETED, 2L);
    }

    @Test
    void createNewOrderSuccessfully() throws Exception {
        Mockito.when(orderService.createOrder(Mockito.any(OrderDto.class))).thenReturn(orderDto);

        String content = """
                {
                  "id": 1,
                  "description": "some details",
                  "orderStatus": "COMPLETED",
                  "userId": 2
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("some details"))
                .andExpect(jsonPath("$.orderStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.userId").value("2"));
    }

    @Test
    void getAllOrdersSuccessfully() throws Exception {
        Mockito.when(orderService.getAllOrders()).thenReturn(List.of(orderDto));

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].description").value("some details"))
                .andExpect(jsonPath("$[0].orderStatus").value("COMPLETED"))
                .andExpect(jsonPath("$[0].userId").value("2"));
    }

    @Test
    void getOrderByIdSuccessfully() throws Exception {
        Mockito.when(orderService.getOrderById(orderDto.id())).thenReturn(orderDto);
        mockMvc.perform(get("/api/v1/orders/" + orderDto.id()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("some details"))
                .andExpect(jsonPath("$.orderStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.userId").value("2"));
    }

    @Test
    void updateOrderByIdSuccessfully() throws Exception {
        Mockito.doNothing().when(orderService).updateOrderById(orderDto.id(), orderDto);

        String content = """
                {
                  "id": 1,
                  "description": "some details",
                  "orderStatus": "COMPLETED",
                  "userId": 2
                }
                """;

        mockMvc.perform(put("/api/v1/orders/" + orderDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteOrderByIdSuccessfully() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrderById(orderDto.id());
        mockMvc.perform(delete("/api/v1/orders/" + orderDto.id()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void createNewOrderFailedWith500() throws Exception {
       //todo
    }

    @Test
    void createNewOrderFailedWith400() throws Exception {
        //todo
    }

    @Test
    void getAllOrdersFailedWith500() throws Exception {
        //todo
    }

    @Test
    void getAllOrdersFailedWith400() throws Exception {
        //todo
    }

    @Test
    void getOrderByIdFailedWith500() throws Exception {
        //todo
    }

    @Test
    void getOrderByIdFailedWith400() throws Exception {
        //todo
    }

    @Test
    void updateOrderByIdFailedWith500() throws Exception {
        //todo
    }
    @Test
    void updateOrderByIdFailedWith400() throws Exception {
        //todo
    }

    @Test
    void deleteOrderByIdFailedWith500() throws Exception {
        //todo
    }

    @Test
    void deleteOrderByIdFailedWith400() throws Exception {
        //todo
    }
}