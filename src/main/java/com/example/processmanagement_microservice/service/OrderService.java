package com.example.processmanagement_microservice.service;

import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：OrderService
 * @date ：2022-12-9 15:47
 * @version : 1.0
 */

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public Optional<Order> findById(String id){
        Optional<Order> order = orderDao.findById(id);
        return order;
    }

    public void deleteOrder(String id){
        orderDao.deleteById(id);
    }

    public int addOrder(Order order){
        boolean exists=orderDao.existsById(order.getOrderId());
        int success=0;
        if(exists==true){
            System.out.println("订单已存在，无法添加！");
        }
        else{
            orderDao.save(order);
            success=1;
            System.out.println("添加成功！");
        }
        return success;
    }

    public int changeOrder(Order newOrder){
        boolean exists=orderDao.existsById(newOrder.getOrderId());
        int success=0;
        if(exists!=true){
            System.out.println("订单不存在，无法修改！");
        }
        else{
            orderDao.save(newOrder);
            success=1;
            System.out.println("修改成功！");
        }
        return success;
    }

    public List getAllCompleteOrders(String sponsorId){
        Order checkOrder=new Order();
        checkOrder.setSponsorId(sponsorId)
                .setStatus("complete");
        Example<Order> orderExample=Example.of(checkOrder);
        List<Order> orderList=orderDao.findAll(orderExample);
        return orderList;
    }

    public List getAllIncompleteOrders(String sponsorId){
        Order checkOrder=new Order();
        checkOrder.setSponsorId(sponsorId)
                .setStatus("incomplete");
        Example<Order> orderExample=Example.of(checkOrder);
        List<Order> orderList=orderDao.findAll(orderExample);
        return orderList;
    }

    public List getAllOrders(String sponsorId){
        Order checkOrder=new Order();
        checkOrder.setSponsorId(sponsorId);
        Example<Order> orderExample=Example.of(checkOrder);
        List<Order> orderList=orderDao.findAll(orderExample);
        return orderList;
    }
}
