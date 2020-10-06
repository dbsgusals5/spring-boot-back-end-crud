package com.springboot.crud.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.crud.api.dao.UserDao;
import com.springboot.crud.api.dto.User;

@Service
public class LoginService {

	@Autowired
    private UserDao repository;

    public String register(@RequestBody User user) {
        repository.save(user);
        return "Hi " + user.getName()+ " your Registration process successfully completed";
    }
    
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public List<User> findUser(String email) {
        return repository.findByEmail(email);
    }

    public List<User> cancelRegistration(int id) {
        repository.deleteById(id);
        return repository.findAll();
    }
    
    public List<User> userLogin(String email, String password ) {
    	
    	List<User> user = repository.findLoginInfo(email);
    	
    	for(int i=0; i<user.size(); i++) {
    		if(!user.get(i).getPassword().equals(password)) {
    			return new ArrayList<>();
    		}
    	}
    	return repository.findLoginInfo(email);
    }

}
