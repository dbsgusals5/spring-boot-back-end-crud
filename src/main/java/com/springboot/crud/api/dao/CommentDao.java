package com.springboot.crud.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.crud.api.dto.Board;
import com.springboot.crud.api.dto.Comment;
import com.springboot.crud.api.dto.FileDto;
import com.springboot.crud.api.dto.User;


@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {
	@Query("select t FROM Comment as t where t.bno=?1")
    List<Comment> getComment(int bno);
	
	@Query("select t FROM Comment as t where t.cno=?1")
	Comment findById(int id);
	
	@Query("select count(t) FROM Comment as t where t.bno=?1")
	int commentCount(int bno);
	
	@Modifying
	@Transactional
	@Query(value = "delete FROM Comment as t where t.cno=?1", nativeQuery = true)
	void deleteComment(int cno);
}
