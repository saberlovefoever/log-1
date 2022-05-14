package org.whh.bz.dao;


import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.whh.bz.entity.Img;

import java.util.List;
@Component
public interface ImgDao {

	List<Img> getAll(int page);
	
	int add(List<Img> list );

	Img findByID(int id);
	
	int getLatestId();

	List<Img> findByKeyword(@Param("keyword")String keyword, @Param("page")int page);

}
