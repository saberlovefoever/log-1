package org.whh.bz.service;


import org.springframework.web.multipart.MultipartFile;
import org.whh.bz.entity.Img;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface ImgService {	
	List<Img> getAll(int page);
	
	int add(MultipartFile[] multipartFile, String[] selectStyle) throws IOException;
	
	Img findById(int id);

	List<Img>  findByKeyword(String keyword,int page);

}
