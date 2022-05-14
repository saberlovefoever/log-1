package org.whh.bz.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.whh.bz.dao.ImgDao;
import org.whh.bz.entity.Img;
import org.whh.bz.service.ImgService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class ImgServiceImpl implements ImgService {
	@Resource
	public ImgDao imgDao;
	@Resource
	private RedisTemplate<String,Img> redisTemplate;
	@Value("${sUrl}")
	private  String sUrl;
	@Value("${mUrl}")
	private  String mUrl;
	@Value("${lUrl}")
	private  String lUrl;

	@Override
	public List<Img> getAll(int page) {
		log.debug("====获取单页数据====");
		List<Img> list=null;
		/*假设每天5点定时加载数据到redis*/
		list = imgDao.getAll((page-1)*21);
		return   list;
	}

	@Override
	@Transactional
	public int add(MultipartFile[] multipartFile,String[] selectStyle) throws IOException {
		List<Img> list = new ArrayList<>();					//要被持久化的img
		HashSet<String> repeatlist = new HashSet<>();		//要返回前台的重复文件名、id
		int latestId = imgDao.getLatestId();
		for (int i = 0; i < multipartFile.length; i++) {
			String s = DigestUtils.md5Hex(multipartFile[i].getBytes());
			if (redisTemplate.opsForHash().get(s,s)!=null){
				repeatlist.add(s);
				continue;
			}
			try {
//				450*287 1202*676外面框架大小  手机壁纸控制横向  电脑壁纸控制纵向
				multipartFile[i].transferTo(new File(lUrl+s+".jpg"));
				Thumbnails.of(new File(lUrl+s+".jpg")).size(1202,676).toFile(mUrl+s+".jpg");
				Thumbnails.of(new File(lUrl+s+".jpg")).size(450,287).toFile(sUrl+s+".jpg");
			} catch (IllegalStateException e) {
				log.error("(ｷ｀ﾟДﾟ´) OPS！IllegalStateException====>非法文件名。。");
			} catch (IOException e) {
				log.error("(ｷ｀ﾟДﾟ´) OPS！IOException====>上传期间遇到问题，宕机，非法访问？");
			}
			list.add(new Img(latestId+i+1,multipartFile[i].getOriginalFilename().substring(0, multipartFile[i].getOriginalFilename().lastIndexOf(".")),selectStyle[i] , "4k"));

		}//for end
		return imgDao.add(list);
	}//Method end

	@Override
	/*id为解码之后的id？*/
	public Img findById(int id) {
		log.info("====查询壁纸编号("+id+")的信息：====");
		Img img = imgDao.findByID(id);
    	return img;
	}

	@Override
	public List<Img> findByKeyword(String keyword,int page) {
		return imgDao.findByKeyword(keyword,(page-1)*21);
	}
}