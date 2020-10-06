package com.springboot.crud.api.dto;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="FILE")
public class FileDto {
	@Id
	@GeneratedValue
	private int fno;
    private int bno;
    private String fileName;     //저장할 파일
    private String fileOriName;  //실제 파일
    private String fileUrl;
 
    
    public int getFno() {
        return fno;
    }
 
    public void setFno(int fno) {
        this.fno = fno;
    }
 
    public int getBno() {
        return bno;
    }
 
    public void setBno(int bno) {
        this.bno = bno;
    }
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
 
    public String getFileOriName() {
        return fileOriName;
    }
 
    public void setFileOriName(String fileOriName) {
        this.fileOriName = fileOriName;
    }
 
    public String getFileUrl() {
        return fileUrl;
    }
 
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
 
}
