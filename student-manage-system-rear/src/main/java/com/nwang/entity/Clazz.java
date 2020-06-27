package com.nwang.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tb_clazz")
@Entity
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String grade;
    String clazz;
    String headTeacher;
    Integer totalStudent;           //限定总人数
    Integer currentTotalStudent;    //当前总人数
}
