package com.nwang.controller;

import com.alibaba.fastjson.JSONObject;
import com.nwang.common.ServerResponse;
import com.nwang.entity.Teacher;
import com.nwang.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    ITeacherService teacherService;
    //添加
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ServerResponse addTeacher(@RequestBody Teacher teacher){
        return teacherService.addTeacher(teacher);
    }

    //修改
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse updateTeacher(@RequestBody Teacher teacher){
        return teacherService.updateTeacher(teacher);
    }

    //查看
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ServerResponse listTeacher(){
        return teacherService.listTeacher();
    }
    //删除
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public ServerResponse deleteTeacher(@PathVariable long id){
        return teacherService.deleteTeacher(id);
    }
    //查询
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ServerResponse searchTeacher(@RequestBody JSONObject jsonObj){
        return teacherService.searchTeacher(jsonObj);
    }

}
