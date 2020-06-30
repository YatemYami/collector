package com.andy.collector.controller;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.andy.collector.Main;
import com.andy.collector.model.User;
import com.andy.collector.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest {
	protected User user;
	protected UserController controller;
	
	@Autowired 
	UserService userService;
	
	@BeforeAll
	void init() {
		controller = new UserController(userService);
		
		user = new User();
		user.setNickname("mordos");
		user.setPassword("nieco");
		
		//SpringApplication app = new SpringApplication(Main.class);
     	//app.run();
	}
	
	@Test
	@Order(1)
	public void testAddUser() throws SQLException {
		ResponseEntity<String> ent = controller.addUser(user);
		assertEquals(new ResponseEntity<String>("user saved",HttpStatus.OK), ent);	
	}	
	
	@Test
	@Order(2)
	public void testUpdateUser() throws SQLException {
		ResponseEntity<String> ent = controller.editUser(user, "3");
		assertEquals(new ResponseEntity<String>("user saved",HttpStatus.OK), ent);	
	}
	
	@Test
	@Order(3)
	public void testShowUser() throws SQLException {
		ResponseEntity<Optional<User>> ent = controller.showUser("3");
		assertEquals(new ResponseEntity<Optional<User>>(ent.getBody(),HttpStatus.OK), ent);	
	}
	
	@Test
	@Order(4)
	public void testShowAll() throws SQLException {
		ResponseEntity<List<User>> ent = controller.showUser();
		assertEquals(new ResponseEntity<List<User>>(ent.getBody(),HttpStatus.OK), ent);	
	}
	
	@Test
	@Order(5)
	public void testDelete() throws SQLException {
		ResponseEntity<String> ent = controller.deleteUser("452");
		assertEquals(new ResponseEntity<String>("user removed",HttpStatus.OK), ent);	
	}
}
