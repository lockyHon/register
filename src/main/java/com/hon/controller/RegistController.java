package com.hon.controller;


import com.hon.entity.User;
import com.hon.service.IRegistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegistController {

    @Autowired
    private IRegistService registService;

    /**
     * ===================================页面跳转===================================
     */
    /**
     * 注册
     */
    @RequestMapping("/toRegist")
    public String toRegist(){
        return "regist";
    }

    /**
     * 跳转至 根据用户名修改密码
     */
    @RequestMapping("/toUpdatePWD")
    public String toUpdatePWD(){
        return "accordingToNameUpdate";
    }

    /**
     * ===================================修改密码模块===================================
     */
    /**
     *   1 根据用户名发送 邮件
     */
    @RequestMapping("/sendEmailUp")
    @ResponseBody
    public String sendEmailUp(String username,HttpServletRequest request){  //获取request 让Service获取本地信息拼接邮件信息
        User user = registService.getUserByName(username);
        if (user !=null){                                      //判断用户是否存在
            //发送邮件
            return registService.sendEmailToUpdate(user, request);
        }
        return "0";
    }

    /**
     *     2 跳转页面修改密码--
     *        a 根据 id ,code 查询是否一致.
     */
    @RequestMapping("chickUpdatePWD")
    public String chickUpdatePWD(User user,Model m){

      if(user.getId()==0){   //非空判断
          m.addAttribute("msg","该链接已失效,请重新发送邮件");
          return "accordingToNameUpdate";
      }
        User u = registService.getUserById(user.getId());  //根据ID查询用户

        if(u.getCode()!=null&u.getCode().equals(user.getCode()) ){
            m.addAttribute("u",u);  //将数据存入model,
            return "updatePWD";  //跳转至修改密码页面,根据 user判断是否 可修改
        }
        m.addAttribute("msg","该链接已失效,请重新发送邮件");
        return "accordingToNameUpdate";

    }

    /**
     *     3 修改密码
     */
    @RequestMapping("/updateEnd")
    public String updateEnd(User user,Model m){
        if(user.getCode()!=null){
            int i = registService.updatePWD(user);
            m.addAttribute("msg","修改成功,请用新密码登录");
            return "index";
        }
        m.addAttribute("msg","修改失败,Cede失效,请重新发送邮件获取新Code");
        return "accordingToNameUpdate";
    }


    /**
     * ===================================注册模块===================================
     */
    /**
     *发送注册code
     */
    @RequestMapping(value = "sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("发送验证码");
        Long star = System.currentTimeMillis();  //开始时间
        String title = "注册验证";
        //获取验证信息
        if (email == "" || email == null) {
            return "-1";
        } else {
            String result = registService.sendRegistEmail(email,request);  //"0"说明发送失败.
           return result;                                                  //"1" 发送成功
        }
    }

    //#######    2 根据验证码注册     ########
    @RequestMapping(value = "/register")
    public String register(User user, HttpServletRequest request, Model m){
        String code = (String) request.getSession().getAttribute("code");  //获取Session 中Code的值
        System.out.println("Session中的Code为:"+code);
      try{
          if(code.equals(user.getCode())){
              registService.addUser(user);
              //  response.setHeader("refresh","3;index.html");
              // return "<font color='green' size='20'>注册成功3秒之后发生跳转到主页....</font>";
              m.addAttribute("msg","注册成功");
              return "index";
          }else {
              //response.setHeader("refresh","3;regist.html");
              //return "<font color='green' size='20'>验证码错误,3秒之后发生跳转到注册页面....</font>";
              m.addAttribute("msg","验证码无效,请重新注册");
              return "regist";
          }
      }catch (NullPointerException e){
        e.printStackTrace();
          m.addAttribute("msg","您未获取验证码.清先获取验证码");   //因为Code存在Session ,没有的话会抛异常
          return "regist";
      }
    }
}
