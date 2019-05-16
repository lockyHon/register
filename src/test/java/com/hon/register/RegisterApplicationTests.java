package com.hon.register;

import com.hon.dao.IRegistDao;
import com.hon.entity.User;
import com.hon.service.IRegistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLOutput;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterApplicationTests {

    @Autowired
    private IRegistService service;
    @Test
    public void contextLoads() {
        User user = service.getUserById(3);
        System.out.println(user.getEmail());
    }
    @Test
    public void getUserByName() {
        User user = service.getUserByName("admin");
        System.out.println(user.getEmail());
    }

    @Test
    public void insert() {
        User u= new User();
        u.setUsername("武松");
        u.setPassword("123");
        service.addUser(u);
        System.out.println(u.getId());
    }

    @Test
    public void updatePWD() {
        User u= new User();
        u.setId(7);
        u.setPassword("123");
        u.setCode("K15r0");
        service.updatePWD(u);
        System.out.println(u.getId());
    }
}
