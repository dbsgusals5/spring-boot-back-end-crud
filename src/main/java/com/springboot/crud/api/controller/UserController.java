package com.springboot.crud.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import com.springboot.crud.api.dao.UserDao;
import com.springboot.crud.api.dto.Board;
import com.springboot.crud.api.dto.User;
import com.springboot.crud.api.service.LoginService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.springboot.crud.api")
@EntityScan("com.springboot.crud.api")
@EnableJpaRepositories("com.springboot.crud.api")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/user")
    public String register(@RequestBody User user) {
    	loginService.register(user);
        return "Hi " + user.getName()+ " your Registration process successfully completed";
    }
    
    @GetMapping("/user")
    public List<User> findAllUsers() {
        return loginService.findAllUsers();
    }

    @GetMapping("/user/{email}")
    public List<User> findUser(@PathVariable String email) {
        return loginService.findUser(email);
    }

    @DeleteMapping("/user/{id}")
    public List<User> cancelRegistration(@PathVariable int id) {
    	loginService.cancelRegistration(id);
        return loginService.findAllUsers();
    }
    
    @PostMapping("/user/{email}")
    public List<User> userLogin(@PathVariable String email, @RequestBody String password ) {
    	
    	List<User> user = loginService.userLogin(email,password);
    	
    	for(int i=0; i<user.size(); i++) {
    		if(!user.get(i).getPassword().equals(password)) {
    			return new ArrayList<>();
    		}
    	}
    	return loginService.userLogin(email,password);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserController.class, args);
    }

}


