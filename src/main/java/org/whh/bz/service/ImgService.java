package org.whh.bz.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.whh.bz.entity.Img;
import org.whh.bz.enums.UploadStatus;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ImgService {
	Map getAll(String keywords, int page);
	
	int add(Map multipartFile) ;
	
	Img findById(int id);

}
