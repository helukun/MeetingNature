package com.example.processmanagement_microservice.service;

import com.example.processmanagement_microservice.model.FeedBack;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    public String setNextId(){
        List<Order> OrderList=orderDao.findAll();
        int curMaxId=0;
        for(Order o:OrderList){
            curMaxId=Math.max(curMaxId,Integer.parseInt(o.getOrderId()));
        }
        int result=curMaxId+1;
        return result+"";
    }

    /*给订单也设置为了自生成的id，只要没有出现报错，则永远添加成功，所以返回1*/
    public String addOrder(Order order){
        order.setOrderId(setNextId());
        order.setStatus("incomplete");
        order.setSetupTime(String.valueOf(LocalDateTime.now()));
        orderDao.save(order);
        return order.getOrderId();
    }

    /*/
    public int changeOrderStatus(Order newOrder){
        boolean exists=orderDao.existsById(newOrder.getOrderId());
        int success=0;
        if(exists!=true){
            System.out.println("订单不存在，无法修改！");
        }
        else{
            orderDao.save(newOrder);
            System.out.println("修改成功！");
        }
        return success;
    }*/

    /*将订单的状态从incomplete改为complete*/
    /*如果做出修改则返回1，订单已经为已完成或者未存在返回0*/
    public int changeOrderStatus(String orderId){
        boolean exists=orderDao.existsById(orderId);
        if(exists==false)
            return 0;

        Order orderCheck=new Order();
        orderCheck.setOrderId(orderId);
        Example<Order> orderExample=Example.of(orderCheck);
        List<Order> originalOrderList=orderDao.findAll(orderExample);
        Order originalOrder=originalOrderList.get(0);
        if(originalOrder.getStatus().equals("incomplete"))
        {
            originalOrder.setStatus("complete");
            orderDao.save(originalOrder);
            return 1;
        }
        else
            return 0;
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
