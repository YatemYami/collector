/*
 * Copyright (c) ...
 */
package com.andy.collector.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.andy.collector.model.Note;
import com.andy.collector.model.User;

import java.sql.ResultSet;

/**
 * The AlbumCommands provides methods for new user registration, login, loading data from database and
 * showing them in the gui table and also provide method for removing chosen card from database.
 * 
 * @version		0.1 14. May 2020
 * @author 		Andrej Marcan
 */
@Repository
public class UserRepository extends BCryptPasswordEncoder{
    
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/* Method addUser is used for new user registration */
    public User addUser(User user) throws SQLException {
    	final String QUERY = "INSERT INTO users (nickname, password) VALUES (?,?)";
    	jdbcTemplate.update(QUERY, user.getNickname(), encode(user.getPassword()));
		return user;
    }
    
    /* Method deleteCard will delete data for selected card by ID of the card */
    public boolean deleteUser(String cell) throws SQLException {
    	final String query = "DELETE FROM users WHERE id_user = " + cell; // cell represents table block where card ID is found
        
        return jdbcTemplate.update(query) > 0;
    }
    
    /* Method login checks if user name and password are right */
    public boolean login(User user, String id) throws SQLException {
        final String query = "SELECT password FROM users WHERE id_user = " + id;
        
        String rawPass = user.getPassword();        
        String hashedPass = jdbcTemplate.queryForObject(query, String.class);
        
        return matches(rawPass, hashedPass);
    }
    
}