package org.whh.bz.dao;


import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.whh.bz.entity.Img;

import java.util.List;
@Repository
public interface ImgDao {

	List<Img> getAll(int page);
	
	int add(List<Img> list );

	Img findByID(int id);
	
	int getLatestId();

	List<Img> findByKeyword(@Param("keyword")String keyword, @Param("page")int page);

	@Select("select ifnull((select 1 from img where img_hash = #{hash}),0)")
	int findByHash(String hash);
}
