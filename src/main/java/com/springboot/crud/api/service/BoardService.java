package com.springboot.crud.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.crud.api.dao.BoardDao;
import com.springboot.crud.api.dao.FileDao;
import com.springboot.crud.api.dto.Board;
import com.springboot.crud.api.dto.FileDto;


@Service
@Transactional
public class BoardService {
	@Autowired
    private BoardDao repository;
	@Autowired
    private FileDao FileRepository;

    public String register(Board board) {
    	repository.save(board);
        return "Hi " + board.getName()+ " your Registration process successfully completed";
    }
    
    public List<Board> findAllBoard() {
        return repository.findAll();
    }
    
    public List<Board> deletePost(int id) {
        repository.deleteById(id);
        FileRepository.deleteFile(id);
        return repository.findAll();
    }
    
    public Board getPost(int id) {
        return repository.findById(id);
    }
    
    public String fileInsertService(FileDto fileDto) {
    	FileRepository.save(fileDto);
        return "Hi " + fileDto.getBno()+ " your Registration process successfully completed";
    }
    
    public List<FileDto> fileDetail(int bno) {
        return FileRepository.fileDetail(bno);
    }
    
    public FileDto fileDetailByFno(int fno){
  	  return FileRepository.fileDetailByFno(fno);
  }
  
}
