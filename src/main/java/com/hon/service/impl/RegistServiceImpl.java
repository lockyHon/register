package com.hon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hon.dao.IRegistDao;
import com.hon.entity.User;
import com.hon.service.IRegistService;
import com.hon.utils.GenerateCode;
import com.hon.utils.GenerateMessage;
import com.hon.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class RegistServiceImpl implements IRegistService{

    @Autowired
    private IRegistDao registDao;

    /**
     *
     * @param
     * @return
     */
    @Override
    public String sendRegistEmail(String Email, HttpServletRequest request) {
        String title = "注册验证";
        GenerateCode c = new GenerateCode();
        String code = c.getCode();//生成验证码
        System.out.println("生成的验证码为:"+code);   //输出验证码
        GenerateMessage mess = new GenerateMessage();
        String message = mess.getMessage(code);//将生成的code 放入mess生成 邮件内容
        String mailResult = SendMail.send_mail(Email, title, message);//发送邮件
        request.getSession().setAttribute("code",code);
        System.out.println("mailResult+"+mailResult);
        if(mailResult=="0"){
            return "0";
        }
        return "1";
    }

    @Override
    public User getUserById(int id) {
        return registDao.selectById(id);
    }

    @Override
    public User getUserByName(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        return registDao.selectOne(wrapper);
    }

    @Override
    public User addUser(User user) {
        registDao.insert(user);
        return user;
    }


    @Override
    public int updatePWD(User user) {
        return registDao.updatePWD(user) ;
    }

    /**
     * 1发送邮件
     * 2改变数据库的code,使得 保持最新转态
     */
    @Override
    public String sendEmailToUpdate(User user, HttpServletRequest request) {
        // 1.获取唯一code
        GenerateCode generateCode = new GenerateCode();
        String updateCode = generateCode.getUpdateCode();  //获取更新code
        // 2.拼接邮件地址
        GenerateMessage generateMessage = new GenerateMessage();
        String mess = generateMessage.getUpdateMess(updateCode, user.getId(), request);
        System.out.println("mess+================"+mess);
        // 3.发送邮件
        String mailResult = SendMail.send_mail(user.getEmail(), user.getUsername() + ":账号修改密码", mess);

        // 4 判断返回值是否发送成功
        if("0".equals(mailResult)){   //失败
            return "0";
        }else {
            // 5.发送成功后 修改数据库中的Code 保持 Code 为最新数值
            user.setCode(updateCode);
            int i = registDao.updateById(user);
            System.out.println("数据库code修改结果为:"+i);
            return i+"";

        }
    }
}
