package com.nwang.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String roleName;	//角色名称
    @OneToMany(cascade = {CascadeType.ALL} , fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    List<Permission> permissions;
}