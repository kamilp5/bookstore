package io.spring.bookstore.rest;

import io.spring.bookstore.model.Order;
import io.spring.bookstore.service.OrderService;
import io.spring.bookstore.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order saveOrder(){
        return orderService.saveOrder();
    }
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping(value = "/{orderId}")
    public Order getOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }
    @GetMapping(value = "/getStatuses")
    public List<OrderStatus> getStatuses(){
        return Arrays.asList(OrderStatus.values());
    }
    @PutMapping
    public Order updateOrder(@RequestBody Order order){
        return orderService.updateOrder(order);
    }

}
