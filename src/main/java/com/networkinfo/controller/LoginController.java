package com.networkinfo.controller;

import com.alibaba.fastjson.JSON;
import com.networkinfo.bean.User;
import com.networkinfo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController {
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/login") //登陆
    public String login(@RequestBody User user){
        String flag = "error";
        User u = userDao.getUser(user.getUsername(),user.getPassword());
        HashMap<String,Object> res = new HashMap<>();
        if(u!=null){
            flag = "ok";
        }
        res.put("flag",flag);
        res.put("user",u);
        String  res_json = JSON.toJSONString(res);
        return res_json;
    }
    
}
