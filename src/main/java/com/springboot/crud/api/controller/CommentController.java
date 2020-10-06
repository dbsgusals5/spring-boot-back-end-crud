package com.springboot.crud.api.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.crud.api.dao.BoardDao;
import com.springboot.crud.api.dao.UserDao;
import com.springboot.crud.api.dto.Board;
import com.springboot.crud.api.dto.Comment;
import com.springboot.crud.api.dto.FileDto;
import com.springboot.crud.api.dto.User;
import com.springboot.crud.api.service.BoardService;
import com.springboot.crud.api.service.CommentService;

import org.springframework.data.domain.Sort.Direction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @PostMapping("/comment")
    public int register(
    		@RequestBody Comment comment)throws Exception {   
//    	  	
    	commentService.register(comment);  	  		
   	
        return comment.getCno();
    }
    
    @GetMapping("/comment/all/{bno}")
    public List<Comment> findComment(@PathVariable int bno) {

    	return commentService.getComment(bno);
    }
    
    @GetMapping("/comment/{cno}")
    public Comment findCommentByCno(@PathVariable int cno) {

    	return commentService.findById(cno);
    }
     
      
    @DeleteMapping("/comment/{cno}")
    public String cancelRegistration(@PathVariable int cno) {
    	commentService.deleteComment(cno);
        return "delete complete";
    }
    
    @PutMapping("/comment/{cno}")
    public int updateComment(@PathVariable int cno, @RequestBody String newContent) {
    	Comment comment = new Comment();
    	comment = commentService.findById(cno);
    	comment.setContent(newContent);
    	commentService.register(comment);
        return comment.getBno();
    }
    
    @GetMapping("/comment/count/{bno}")
    public int commentCount(@PathVariable int bno) {

    	return commentService.commentCount(bno);
    }
    
}


