package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginMobileController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("check")
    public Result check(@RequestBody Map map){

        try {
            //获取电话
            String telephone = (String) map.get("telephone");

            //获取用户提交的验证码
            String validateCode = (String) map.get("validateCode");

            //获取redis中的验证码
            String key = telephone+ RedisMessageConstant.SENDTYPE_LOGIN;
            String code = jedisPool.getResource().get(key);

            //判断用户输入的验证码是否正确
            if (code ==null || !code.equals(validateCode)){
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            //查询是否是会员
            Member member = memberService.findById(telephone);
            if (member==null){
                //注册为会员
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());

                //执行新增会员
                memberService.add(member);
            }
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.LOGIN_ERROR);
        }

    }
}
