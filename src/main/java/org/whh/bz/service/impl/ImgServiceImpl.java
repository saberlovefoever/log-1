package org.whh.bz.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.whh.bz.dao.ImgDao;
import org.whh.bz.entity.Img;
import org.whh.bz.service.ImgService;
import org.whh.bz.utils.ImageUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ImgServiceImpl implements ImgService {
	@Resource
	public ImgDao imgDao;

	@Value("${picUrl}")
	private  String picUrl;

//单页最大展示量
	@Value("${maxCount}")
	private int  maxCount;


	@Override
	public Map<String,Object> getAll(String keywords, int page) {
		Map a = new ConcurrentHashMap();
		log.debug("====获取单页数据====");
		List<Img> list=null;
		list = imgDao.getAll(keywords,(page-1)*maxCount,maxCount);
		int count = imgDao.count(keywords);
		a.put("count",count);
		a.put("list",list);
		return a;
	}


	@Override
	@Transactional
	public int add(Map map) {
		List<Img> addlist = new ArrayList<>();                    //要被持久化的imgs
		List<MultipartFile> lists= (ArrayList) map.get("imageFile");
		if(lists.size()<=1&& (lists.get(0).getOriginalFilename().equals("")))
			return 0;
		lists.stream().forEach(p->{
			File file = new File(picUrl+p.getOriginalFilename());
			if (file.exists())
				return;
			String s = null;
			try {
				p.transferTo(file);
				 s= ImageUtils.imgHash(file);

			} catch (IOException e) {
				e.printStackTrace();
			}int hashResult = imgDao.findByHash(s);
			Img img  = new Img(p.getOriginalFilename(),s);
			addlist.add(img);
			if (hashResult!=0){
				file.delete();
				addlist.remove(addlist.size()-1);
			}
		});
		if (addlist.size()==0)
			return 0;
		int result = imgDao.add(addlist);
		return result;
	}


	@Override
	public Img findById(int id) {
		return imgDao.findByID(id);
	}

}