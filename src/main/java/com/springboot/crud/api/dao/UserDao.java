package com.springboot.crud.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.crud.api.dto.Board;
import com.springboot.crud.api.dto.Comment;
import com.springboot.crud.api.dto.User;

import java.util.List;
@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    
	@Query("select t FROM User as t where t.email=?1")
	List<User> findByEmail(String email);
    
    @Query("select t FROM User as t where t.email=?1")
    List<User> findLoginInfo(String email);

    Comment findById(int id);
}
