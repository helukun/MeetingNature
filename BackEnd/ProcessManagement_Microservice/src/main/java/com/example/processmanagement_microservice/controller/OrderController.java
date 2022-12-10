package com.example.processmanagement_microservice.controller;

import com.example.processmanagement_microservice.dao.OrderDao;
import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：OrederController
 * @date ：2022-12-10 14:08
 * @version : 1.1
 */

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    //good
    @PostMapping("/v1.1/processmanagement-microservice/orders")
    public int addOrder(Order newOrder){
        return orderService.addOrder(newOrder);
    }

    //good
    @DeleteMapping("/v1.1/processmanagement-microservice/orders")
    public void deleteOrder(String id){
        orderService.deleteOrder(id);
    }

    //good
    @PutMapping("/v1.1/processmanagement-microservice/orders")
    public int changeOrder(Order newOrder){
        return orderService.changeOrder(newOrder);
    }

    //good
    @GetMapping("/v1.1/processmanagement-microservice/orders/Id")
    public Optional<Order> findOrder(String Id){
        return orderService.findById(Id);
    }

    //good
    @GetMapping("/v1.1/processmanagement-microservice/orders/incomplete")
    public List findAllIncompleteOrders(String sponsorId){
        return orderService.getAllIncompleteOrders(sponsorId);
    }

    //good
    @GetMapping("/v1.1/processmanagement-microservice/orders/complete")
    public List findAllCompleteOrders(String sponsorId){
        return orderService.getAllCompleteOrders(sponsorId);
    }

    //good
    @GetMapping("/v1.1/processmanagement-microservices/order/sponsorId")
    public List findAllOrders(String sponsorId){
        return orderService.getAllOrders(sponsorId);
    }
}
