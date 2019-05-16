package com.hon.utils;

import java.util.Random;

public class GenerateCode {
    private String code="";
    private Integer nums=5;
    private Integer randomNum=22;
    final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    /**
     * 生成验证码
     * @return
     */
    public String getCode(){
        initCode();
        return code;
    }


    /**
     *  获取注册code
     */
    private void initCode(){
        Random random = new Random();
        for (int i = 0; i < nums; i++) {
            String str = random.nextInt(2)%2==0?"num":"char";
            if ("num".equals(str)){
                code+=String.valueOf(random.nextInt(10));
            }else {
                int num=random.nextInt(2)%2==0?65:97;
                code += (char)(random.nextInt(26)+num);
            }
        }
    }

    /**
     * 获取 更新Code
     * @return
     */
    public String getUpdateCode(){
        Random random = new Random();
        char[] cs = new char[randomNum];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = digits[random.nextInt(digits.length)];
        }
        return new String(cs);
    }





}
