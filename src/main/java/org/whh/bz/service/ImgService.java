package org.whh.bz.service;


import org.springframework.web.multipart.MultipartFile;
import org.whh.bz.entity.Img;

import java.io.IOException;
import java.util.List;


public interface ImgService {	
	List<Img> getAll(int page);
	
	List<String> add(MultipartFile[] multipartFile, String[] selectStyle) throws IOException;
	
	Img findById(int id);

	List<Img>  findByKeyword(String keyword,int page);

}
