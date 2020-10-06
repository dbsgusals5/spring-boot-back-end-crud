package com.springboot.crud.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.crud.api.dto.Board;
import com.springboot.crud.api.dto.FileDto;
import com.springboot.crud.api.dto.User;

import java.util.List;
@Repository
public interface FileDao extends JpaRepository<FileDto,Integer> {
	FileDao findById(int id);
	
	@Query("select t FROM FileDto as t where t.bno=?1")
    List<FileDto> fileDetail(int bno);
	
	@Query("select t FROM FileDto as t where t.fno=?1")
    FileDto fileDetailByFno(int fno);
	
	@Modifying
	@Transactional
	@Query(value = "delete FROM file as t where t.bno=?1", nativeQuery = true)
	void deleteFile(int bno);
	
//	@Query("insert into file (bno, file_name, file_ori_name, file_url) values (?1, ?2, ?3, ?4)")
//    void fileInsertQuery(int bno, String fileName, String fileOriName, String fileUrl);

}
