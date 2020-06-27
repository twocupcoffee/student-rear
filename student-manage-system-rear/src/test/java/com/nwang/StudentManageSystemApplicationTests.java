package com.nwang;

import com.nwang.util.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.nwang.entity.Permission;
import com.nwang.entity.Role;
import com.nwang.entity.User;
import com.nwang.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTest {
	@Autowired
	UserRepository userRepository;

	@Test
	public void testAddUser() throws Exception {
		List<Role> roles = new ArrayList<Role>();
		List<Permission> permissions = new ArrayList<Permission>();
		User user = new User();
		Role role = new Role();
		Permission p1 = new Permission("create");
		Permission p2 = new Permission("delete");
		permissions.add(p1);
		permissions.add(p2);
		role.setRoleName("admin");
		role.setPermissions(permissions);
		roles.add(role);
		user.setUserName("Negen");
		user.setPassword(new BCryptPasswordEncoder().encode("123456"));
		user.setRoles(roles);
		String token = TokenUtil.createToken("", "Negen");
		user.setToken(token);
		userRepository.save(user);
		System.out.println("====>添加用户成功");
	}
}