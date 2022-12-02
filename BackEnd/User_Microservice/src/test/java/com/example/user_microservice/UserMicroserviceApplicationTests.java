package com.example.user_microservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.user_microservice.model.User;
import com.example.user_microservice.dao.UserDao;

@SpringBootTest
class UserMicroservicesApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    void addTenProject(){
//        插入10行
        for (Integer count = 1; count < 10; count++) {
            User user = new User()
                    .setId("id_"+count)
                    .setEmail("email_"+count)
                    .setName("Lebron's_fans_number_"+count)
                    .setPassword("password_"+count)
                    .setStatus("status_"+count)
                    .setRole("1");
            userDao.save(user);
        }
    }
    @Test
    void contextLoads() {
    }

}
