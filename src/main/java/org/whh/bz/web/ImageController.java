package org.whh.bz.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.whh.bz.dao.ImgDao;
import org.whh.bz.entity.Img;
import org.whh.bz.enums.UploadStatus;
import org.whh.bz.exceptions.UploadException;
import org.whh.bz.service.ImgService;
import org.whh.bz.utils.ImageUtils;
import reactor.util.annotation.Nullable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


//@ControllerAdvice  注解作用：全局异常处理  数据绑定  数据预处理
@Controller
@Slf4j
public class ImageController {

	private final ImgService imgService;

	private final ImgDao imgDao;

	@Value("${picUrl}")
	private String url;

	@Autowired
	public ImageController(ImgService imgService,ImgDao imgDao) {
		this.imgService = imgService;
		this.imgDao = imgDao;
	}


	//	图片搜索
	@RequestMapping(value={"/search"},method = RequestMethod.POST)
	private ModelAndView search(ModelAndView mov,HttpServletRequest req,@RequestParam("keyboard") String keyboard) {
		StringBuffer sb =new StringBuffer(keyboard.trim());
		List<Img>  list = imgService.findByKeyword( sb.toString(),1);
		req.setAttribute("list", list);
		req.setAttribute("keyboard", keyboard);
		mov.setViewName("bian");
		return mov;
	}

	//图片浏览+restful分页
	@RequestMapping(value = "/page/{page}",method = RequestMethod.GET)
	private ModelAndView index_num(HttpServletResponse resp,
								   ModelAndView mav,@PathVariable("page") int page
								  ) {
		//在页面域、cookie、redis存入匿名用户session
		List<Img> list = imgService.getAll(page);
		mav.addObject("total", imgDao.getLatestId());	//图片总数
		mav.addObject("list", list);
		mav.setViewName("bian");
		return  mav;
	}

//	图片缩放写入流
	@RequestMapping(value = "/sample/{id}",method = RequestMethod.GET)
	private void sample(@PathVariable("id") int id,
						HttpServletResponse resp
	) throws IOException {
		ImageUtils.writeToReq(resp,id,0.7,url);
	}
//   单图片
	@RequestMapping(value = "/one/{id}",method = RequestMethod.GET)
	private String details(@PathVariable("id") int id,HttpServletRequest request) {
		Img  pic=  imgService.findById(id);
		request.setAttribute("pic", pic);
		return "details";
	}
	@RequestMapping(value = "/getOne/{id}",method = RequestMethod.GET)
	private void one(@PathVariable("id") int id,HttpServletResponse resp,HttpServletRequest req) throws IOException {
		ImageUtils.writeToReq(resp,id,0.7,url);
	}

	/**
	 * 图片下载
	 *
	 * */
	@RequestMapping(value = "/download/{id}")
	private void imgDownload(HttpServletResponse resp,
							 @PathVariable(value = "id")String id) throws IOException {
		Img img= imgService.findById(Integer.valueOf(id));
		if (img==null){
			throw new IllegalStateException();
		}
		resp.setHeader("Pragma", "No-cache"); // 设置响应头信息，告诉浏览器不要缓存此内容
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Content-Disposition",
				"attachment;filename="
						+new String((img.getImgKeywords()+".jpg").getBytes("UTF-8"),"iso-8859-1"));
		resp.setContentType("application/octet-stream");
		FileInputStream fis = new FileInputStream(
				new File("E:/ideaProject/temp/src/main/webapp/pics/"+img.getImgId()+".jpg"));
		OutputStream os= resp.getOutputStream();
		int len = -1;
		byte[] bytes = new byte[1024];
		while (fis.read(bytes)!=-1){
			os.write(bytes);
		}
		os.flush();
		os.close();
		fis.close();
	}
	//上传
	@RequestMapping(value = "/upload/index" , method = RequestMethod.GET)
	private String uploads1(){
		return "upload";
	}
//	上传响应
	@RequestMapping(value = "/upload/result" , method = RequestMethod.POST)
	private ModelAndView uploads1(
			@Nullable @RequestParam(value = "imageFile")MultipartFile[] multipartFile,
			HttpServletRequest req,HttpServletResponse resp,
			@Nullable @RequestParam(value = "imgStyle") String[] selectStyle,
			@Nullable ModelAndView mav
			) throws IllegalStateException, IOException, UploadException {
		mav.setViewName("error");
		if (multipartFile==null||selectStyle==null) {
			throw new UploadException(UploadStatus.NULL.getCode(),UploadStatus.NULL.getMsg());
		}
		UploadStatus up = imgService.add(multipartFile,selectStyle);
		mav.addObject("msg",up.getMsg());

		return mav;
	}
}