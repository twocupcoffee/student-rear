package com.nwang.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tb_student")
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Integer age;
    Integer sex;
    String num;
    String grade;
    String clazz;
    String address;
}
