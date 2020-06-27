package com.nwang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nwang.common.ResponseEnum;
import com.nwang.common.ServerResponse;
import com.nwang.entity.Teacher;
import com.nwang.repository.TeacherRepository;
import com.nwang.service.ITeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TeacherService implements ITeacherService {
    private static final String NUM = "1";
    private static final String NAME = "2";
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public ServerResponse addTeacher(Teacher teacher) {
        try {
            teacherRepository.save(teacher);
            return ServerResponse.getInstance().responseEnum(ResponseEnum.ADD_SUCCESS);
        }catch (Exception e){
            log.info(e.getMessage());
            return ServerResponse.getInstance().responseEnum(ResponseEnum.INNER_ERROR);
        }
    }


    @Override
    public ServerResponse updateTeacher(Teacher teacher) {
        try {
            long id = teacher.getId();
            Teacher _teacher = teacherRepository.getOne(id);
            _teacher.setNum(teacher.getNum());
            _teacher.setAge(teacher.getAge());
            _teacher.setSex(teacher.getSex());
            _teacher.setName(teacher.getName());
            _teacher.setCourse(teacher.getCourse());
            teacherRepository.save(_teacher);
            return ServerResponse.getInstance().responseEnum(ResponseEnum.UPDATE_SUCCESS);
        }catch (Exception e){
            log.info(e.getMessage());
            return ServerResponse.getInstance().responseEnum(ResponseEnum.INNER_ERROR);
        }
    }

    @Override
    public ServerResponse listTeacher() {
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            return ServerResponse.getInstance().responseEnum(ResponseEnum.GET_SUCCESS).data(teachers);
        }catch (Exception e){
            log.info(e.getMessage());
            return ServerResponse.getInstance().responseEnum(ResponseEnum.INNER_ERROR);
        }
    }

    @Override
    public ServerResponse deleteTeacher(Long id) {
        try {
            teacherRepository.deleteById(id);
            return ServerResponse.getInstance().responseEnum(ResponseEnum.DELETE_SUCCESS);
        }catch (Exception e){
            log.info(e.getMessage());
            return ServerResponse.getInstance().responseEnum(ResponseEnum.INNER_ERROR);
        }
    }

    @Override
    public ServerResponse searchTeacher(JSONObject obj) {
        try {
            String select = obj.getString("select");
            String content = obj.getString("content");
            List<Teacher> students = new ArrayList<>();
            switch (select){
                case NUM:
                    students = teacherRepository.findByNumContaining(content);
                    break;
                case NAME:
                    students = teacherRepository.findByNameContaining(content);
                    break;
                default:
                    break;
            }
            return ServerResponse.getInstance().responseEnum(ResponseEnum.GET_SUCCESS).data(students);

        }catch (Exception e){
            log.info(e.getMessage());
            return ServerResponse.getInstance().responseEnum(ResponseEnum.INNER_ERROR);
        }
    }
}
