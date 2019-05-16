package com.hon.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    /**
     * 返回值如果为0 发送失败
     *             1 发送成功
     * @param to
     * @param title
     * @param text
     * @return
     */
    public static String send_mail(String to, String title,String text) {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        properties.put("mail.smtp.host", "smtp.qq.com");
        //发送端口（根据实际情况填写，一般均为25）
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码(注意，如果配置后不生效，请检测是否开启了 POP3/SMTP 服务，QQ邮箱对应设置位置在: [设置-账户-POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务])
                return new PasswordAuthentication("441259430@qq.com", "msvmaulxceyjbgjh");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);

        try {
            //设置发件人
            message.setFrom(new InternetAddress("441259430@qq.com"));
            //设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //设置主题
            message.setSubject(title);
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent(text, "text/html;charset=UTF-8");
            //发送一封邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }


}
