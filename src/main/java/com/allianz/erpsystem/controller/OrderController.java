package com.allianz.erpsystem.controller;

import com.allianz.erpsystem.exceptions.StockException;
import com.allianz.erpsystem.model.dto.UpdateOrderDTO;
import com.allianz.erpsystem.model.entity.OrderEntity;
import com.allianz.erpsystem.model.dto.CreateOrderDTO;
import com.allianz.erpsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("add")
    public ResponseEntity<CreateOrderDTO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        OrderEntity orderEntity1 = orderService.create(createOrderDTO);
        if (orderEntity1 != null) {
            return new ResponseEntity<>(createOrderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("get-all")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("get-by-uuid/{uuid}")
    public ResponseEntity<OrderEntity> getByUUID(@PathVariable UUID uuid) {
        OrderEntity orderEntity = orderService.getByUUID(uuid);
        if (orderEntity != null) {
            return new ResponseEntity<>(orderEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete-by-uuid/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByUUID(@PathVariable UUID uuid) {
        orderService.deleteByUUID(uuid);
    }

    @PutMapping("update-by-uuid/{uuid}")
    public ResponseEntity<OrderEntity> updateCustomerByUUID(@PathVariable UUID uuid,
                                                            @RequestBody UpdateOrderDTO updateOrderDTO) {
        OrderEntity orderEntity1 = orderService.updateByUUID(uuid, updateOrderDTO);
        if (orderEntity1 != null) {
            return new ResponseEntity<>(orderEntity1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("approve-order-generate-bill/{uuid}")
    public ResponseEntity<Boolean> approveOrderGenerateBill(@PathVariable UUID uuid) {
        try {
            orderService.approveOrder(uuid);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (StockException stockException) {
            System.err.println(stockException.getMessage());
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
    }


}
