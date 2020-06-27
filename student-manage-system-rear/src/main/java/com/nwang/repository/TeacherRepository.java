package com.nwang.repository;

import com.nwang.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    //根据学号模糊查询教师信息
    List<Teacher> findByNumContaining(String num);
    //根据姓名模糊查询教师信息
    List<Teacher> findByNameContaining(String name);
}