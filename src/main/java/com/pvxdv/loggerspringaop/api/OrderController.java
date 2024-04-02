package com.pvxdv.loggerspringaop.api;


import com.pvxdv.loggerspringaop.dto.OrderDto;
import com.pvxdv.loggerspringaop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "OrderController", description = "CRUD operations with Order Entity")
@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "Create new order",
            description = "Create new order"
    )
    @PostMapping
    public ResponseEntity<OrderDto> createNewOrder(@Valid @RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all orders",
            description = "Get all orders"
    )
    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get current order",
            description = "Get current order by order_id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") @Min(0) Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update current order",
            description = "Update current order by order_id"
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderById(@PathVariable("id") @Min(0) Long id,
                                @Valid @RequestBody OrderDto orderDto) {
        orderService.updateOrderById(id, orderDto);
    }

    @Operation(
            summary = "Delete current category",
            description = "Delete current order by order_id"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable("id") @Min(0) Long id) {
        orderService.deleteOrderById(id);
    }
}
