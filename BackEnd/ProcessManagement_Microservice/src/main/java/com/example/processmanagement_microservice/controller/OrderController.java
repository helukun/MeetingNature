package com.example.processmanagement_microservice.controller;

import com.example.processmanagement_microservice.dao.OrderDao;
import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    //good
    @PostMapping("/processmanagement-microservice/processmanagement/order")
    public int addOrder(Order newOrder){
        return orderService.addOrder(newOrder);
    }

    //good
    @DeleteMapping("/processmanagement-microservice/processmanagement/order")
    public void deleteOrder(String id){
        orderService.deleteOrder(id);
    }

    //good
    @PutMapping("/processmanagement-microservice/processmanagement/order")
    public int changeOrder(Order newOrder){
        return orderService.changeOrder(newOrder);
    }

    @GetMapping("/processmanagement-microservice/processmanagement/order")
    public Optional<Order> findOrder(String id){
        return orderService.findById(id);
    }

    @GetMapping("/processmanagement-microservice/processmanagement/order/IC")
    public List findAllIncompleteOrders(String sponsorid){
        return orderService.getAllIncompleteOrders(sponsorid);
    }

    @GetMapping("/processmanagement-microservice/processmanagement/order/C")
    public List findAllCompleteOrders(String sponsorid){
        return orderService.getAllCompleteOrders(sponsorid);
    }

    @GetMapping("/processmanagement-microservice/processmanagement/order/All")
    public List findAllOrders(String sponsorid){
        return orderService.getAllOrders(sponsorid);
    }
}
