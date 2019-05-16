package com.hon.utils;

import javax.servlet.http.HttpServletRequest;

public class GenerateMessage {
    private String message="";
    private String updateMess="";


    //======================获取信息-对外接口======================

    /**
     * 获取发送的信息
     * @param
     * @return
     */
    public String getUpdateMess(String code,int id,HttpServletRequest request){
        String path = request.getContextPath(); //当前路径
        String htt = request.getScheme();  //获取Http 头
        String server = request.getServerName(); //获取当前服务器名字
        int port = request.getServerPort();  //获取端口
        //http://localhost:8080/
        String basePath=htt+"://"+server+":"+port+path+"/";
        message = "使用链接修改密码:"+basePath+"chickUpdatePWD?code="+code+"&id="+id;
        System.out.println("发送的链接为=:"+message);
        return message;
    }

    /**
     * 获取注册信息
     * @param code
     * @return
     */
    public String getMessage(String code){
        registMessage(code);
        return message;
    }
    //============================================





   //注册信息
    private void registMessage(String code){
        GenerateCode c = new GenerateCode();
        message= "您的注册验证码为:" + code ;

    }

}
