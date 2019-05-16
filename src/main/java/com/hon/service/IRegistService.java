package com.hon.service;

import com.hon.entity.User;
import com.sun.org.apache.bcel.internal.classfile.Code;

import javax.servlet.http.HttpServletRequest;

public interface IRegistService {

    public String sendRegistEmail(String email, HttpServletRequest request);
    //================================
    public User getUserById(int id);
    public User getUserByName(String username);
    public User addUser(User user);
    public int updatePWD(User user);

    public String sendEmailToUpdate(User user, HttpServletRequest request);
}
