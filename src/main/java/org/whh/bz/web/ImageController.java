package org.whh.bz.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.whh.bz.dao.ImgDao;
import org.whh.bz.entity.Img;
import org.whh.bz.service.ImgService;
import org.whh.bz.utils.ImageUtils;
import reactor.util.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;


//@ControllerAdvice  注解作用：全局异常处理  数据绑定  数据预处理
@Controller
@RequestMapping("/wallpaper")
@Slf4j
public class ImageController {

	private final ImgService imgService;

	private final ImgDao imgDao;

	@Value("${maxCount}")
	private int  maxCount;

	@Value("${picUrl}")
	private String url;

	@Autowired
	public ImageController(ImgService imgService,ImgDao imgDao) {
		this.imgService = imgService;
		this.imgDao = imgDao;
	}


	//关键字搜索和显示所有图片
	@RequestMapping(value = "/search/{page}",method = RequestMethod.GET)
	private ModelAndView index_num(
			@Nullable@RequestParam(value = "keyboard",defaultValue = "")String keyboard,
			ModelAndView mav,
			@PathVariable("page") int page) {
		Map map = imgService.getAll(keyboard,page);
		List<Img> list = (List)map.get("list");
		mav.addObject("imglists",list);
		mav.addObject("total",map.get("count"));
		mav.addObject("currentPage",page);
		mav.addObject("perPage",maxCount);
		mav.addObject("keyboard",keyboard);
		mav.setViewName("/bian");
		return  mav;
	}
	//   展示单图片请求
	@RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
	private String details(@PathVariable("id") int id,HttpServletRequest request) {
		Img pic = imgService.findById(id);
		request.setAttribute("pic", pic);
		return "/details";
	}
	//	图片缩放写入流1 图片src1
	@RequestMapping(value = "/sample/{id}",method = RequestMethod.GET)
	private void sample(@PathVariable("id") int id, HttpServletResponse resp){
		try {
			ImageUtils.writeToReq(resp,id,0.3,url);
		} catch (IOException e) { e.printStackTrace(); }
	}
	//图片缩放写入流2  detail  图片src2

	@RequestMapping(value = "/sample2/{id}",method = RequestMethod.GET)
	private void one(@PathVariable("id") int id,HttpServletResponse resp){
		try {
			ImageUtils.writeToReq(resp,id,0.7,url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 图片下载
	 *
	 * */
	@RequestMapping(value = "/download/{id}")
	private void imgDownload(HttpServletResponse resp,
							 @PathVariable(value = "id")String id) throws IOException {
		Img img= imgService.findById(Integer.parseInt(id));
		if (img==null){
			throw new IllegalStateException();
		}
		resp.setHeader("Pragma", "No-cache"); // 设置响应头信息，告诉浏览器不要缓存此内容
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Content-Disposition",
				"attachment;filename="
						+new String((img.getImgKeywords()+".jpg").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
		resp.setContentType("application/octet-stream");
		FileInputStream fis = new FileInputStream(
				url+img.getImgId()+".jpg");
		OutputStream os= resp.getOutputStream();
		byte[] bytes = new byte[1024];
		while (fis.read(bytes)!=-1){
			os.write(bytes);
		}
		os.flush();
		os.close();
		fis.close();
	}


	//上传界面导航

	@RequestMapping(value = "/upload" , method = RequestMethod.GET)
	private String uploads1(){
		return "upload";
	}

	//上传响应
	@RequestMapping(value = "/uploadResult",method = RequestMethod.POST)
	private ModelAndView uploads1(
			@Nullable ModelAndView mav, StandardMultipartHttpServletRequest req
			) throws IllegalStateException {
			MultiValueMap<String, MultipartFile> map =req.getMultiFileMap();
			int up = imgService.add(map);
			mav.addObject("msg", "上传图片数量"+up );
			mav.setViewName("result");
			return mav;
		}
}