package com.nwang.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tb_teacher")
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Integer age;
    Integer sex;
    String num;
    String course;
}
