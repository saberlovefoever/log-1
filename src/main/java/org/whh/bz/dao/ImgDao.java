package org.whh.bz.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.whh.bz.entity.Img;

import java.util.Collection;
import java.util.List;
public interface ImgDao {

	int count(String keywords);

	List<Img> getAll(String keywords, int index, int range);

	int add(List list );

	Img findByID(int id);

    int findByHash(String hash);
}
