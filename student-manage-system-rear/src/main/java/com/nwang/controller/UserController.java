package com.nwang.controller;

import com.alibaba.fastjson.JSONObject;
import com.nwang.common.ResponseEnum;
import com.nwang.common.ServerResponse;
import com.nwang.entity.User;
import com.nwang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    IUserService userService;

    @RequestMapping(value = "user/create", method = RequestMethod.POST)
    ServerResponse userRegister(@RequestBody User user){
        return userService.userRegiste(user);
    }


    @RequestMapping(value = "user/login")
    ServerResponse userLogin(){
        return ServerResponse.getInstance().responseEnum(ResponseEnum.ACCOUT_NOT_LOGIN);
    }

    @RequestMapping(value = "user/info", method = RequestMethod.GET)
    ServerResponse getUserInfo(HttpServletRequest request){
        String token = request.getParameter("token");
        return userService.getUserInfoByToken(token);
    }


    @RequestMapping(value = "user/info/update", method = RequestMethod.POST)
    ServerResponse updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @RequestMapping(value = "user/info/updatePassword", method = RequestMethod.POST)
    ServerResponse updatePassword(@RequestBody JSONObject jsonObject){
        return userService.updateUserPassword(jsonObject);
    }
}
