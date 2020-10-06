package com.springboot.crud.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.crud.api.dto.Board;
import com.springboot.crud.api.dto.User;

import java.util.List;
@Repository
public interface BoardDao extends JpaRepository<Board,Integer> {
	Board findById(int id);
    
}
