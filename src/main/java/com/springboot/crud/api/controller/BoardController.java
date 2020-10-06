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
import com.springboot.crud.api.dto.FileDto;
import com.springboot.crud.api.dto.User;
import com.springboot.crud.api.service.BoardService;

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
public class BoardController {

    @Autowired
    private BoardService boardService;
    


    @PostMapping("/board")
    public int register(
    		@RequestBody Board board)throws Exception {   
//    	  	
    		boardService.register(board);  	
    	 	
  	
        return board.getId();
    }
    
    @PostMapping("/board/file")
    public String fileRegister(
    		@RequestParam("bno") int bno,
    		@RequestParam("file") MultipartFile[] files)throws Exception {
    	  	
    	System.out.println(files.length + " length");
    	
    	for(int i=0; i<files.length; i++) {
    		FileDto fileDto = new FileDto();	
 
    		String fileName = files[i].getOriginalFilename();
        	String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        	FileDto destinationFile;
        	String destinationFileName;
        	String fileUrl = "C:/Users/dbsgu/Downloads/springBootCrudPrj/src/main/java/uploadFiles/";        
      
        	do {
        		destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension; 
                destinationFile = new FileDto(fileUrl + destinationFileName); 
        	} while (destinationFile.exists());
        	       	
        	
        	destinationFile.getParentFile().mkdirs();
        	files[i].transferTo(destinationFile);
        	  
        	fileDto.setFileName(destinationFileName);
        	fileDto.setFileOriName(fileName);
        	fileDto.setFileUrl(fileUrl);
        	fileDto.setBno(bno);
        	System.out.println(i + " 번쨰@@@");
        	
        	boardService.fileInsertService(fileDto);
        	  	     	
        	System.out.println("inserted");
    	}  	
    	return "file inserted";
    }
       
    @GetMapping("/board")
    public List<Board> findAllBoard() {
        return boardService.findAllBoard();
    }
    @DeleteMapping("/board/{id}")
    public List<Board> cancelRegistration(@PathVariable int id) {
    	boardService.deletePost(id);
        return boardService.findAllBoard();
    }
    
    @GetMapping("/board/{id}")
    public Map<String,Object> findPost(@PathVariable int id, Model model) {
 	
    	ArrayList<Object> list = new ArrayList<Object>();
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	map.put("post", boardService.getPost(id));
    	map.put("files",boardService.fileDetail(id));
    	
    	return map;
    }
    
    @PutMapping("/board/{id}")
    public String updatePost(@PathVariable int id, @RequestBody Board newBoard) {
    	Board board = new Board();
    	board = boardService.getPost(id);
    	board.setTitle(newBoard.getTitle());
    	board.setContent(newBoard.getContent());
    	boardService.register(board);
        return "Hi " + board.getName()+ " your modify process successfully completed";
    }
    
    @GetMapping("/file/{fno}")
    private void fileDown(@PathVariable int fno, HttpServletRequest request, HttpServletResponse response) throws Exception{
        
    	System.out.println("fileDown access");
    	
        request.setCharacterEncoding("UTF-8");
        
        FileDto fileVO = boardService.fileDetailByFno(fno);
                  
        //파일 업로드된 경로 
        try{
            String fileUrl = fileVO.getFileUrl();
            //fileUrl += "/";
            String savePath = fileUrl;
            String fileName = fileVO.getFileName();
            
            //실제 내보낼 파일명 
            String oriFileName = fileVO.getFileOriName();
            InputStream in = null;
            OutputStream os = null;
            FileDto file = null;
            boolean skip = false;
            String client = "";
            
            System.out.println(savePath+fileName);
       
            
            //파일을 읽어 스트림에 담기  
            try{
                file = new FileDto(savePath, fileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }
            
            client = request.getHeader("User-Agent");
            
            //파일 다운로드 헤더 지정 
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");
                          
            if (!skip) {
                // IE      	
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                os = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
    		
            in.close();
            os.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
}


