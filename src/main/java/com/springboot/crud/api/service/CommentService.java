package com.springboot.crud.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.crud.api.dao.CommentDao;
import com.springboot.crud.api.dto.Comment;

@Service
public class CommentService {
	@Autowired
    private CommentDao repository;
	
	public String register(Comment comment) {
    	repository.save(comment);
        return "Hi " + comment.getContent()+ " your Registration process successfully completed";
    }
	
	public List<Comment> getComment(int bno) {
        return repository.getComment(bno);
    }
	
	 public Comment findById(int cno) {
        return repository.findById(cno);
    }
	
	public int commentCount(int bno) {
		return repository.commentCount(bno);
	}
	
	public void deleteComment(int cno) {
		repository.deleteComment(cno);
	}
}
