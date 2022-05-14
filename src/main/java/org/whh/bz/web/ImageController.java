package org.whh.bz.web;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.whh.bz.dao.ImgDao;
import org.whh.bz.entity.Img;
import org.whh.bz.entity.WxUser;
import org.whh.bz.enums.UserStatus;
import org.whh.bz.service.ImgService;
import org.whh.bz.service.RedisUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@ControllerAdvice  注解作用：全局异常处理  数据绑定  数据预处理
@Controller
@Slf4j
public class ImageController {
	@Resource
	private ImgService imgService;
	@Resource
	private ImgDao imgDao;
	@Resource
	private RedisUserService redisUserService;
	//	图片搜索
	@RequestMapping(value={"/search"},method = RequestMethod.POST)
	private String search(HttpServletRequest req,@RequestParam("keyboard") String keyboard) {
		List<Img>  list = imgService.findByKeyword( keyboard,1);
		req.setAttribute("list", list);
		req.setAttribute("keyboard", keyboard);
		return "bian";
	}

	//=================	IMAGEDetailes===================

	/*此注解改变返回数据格式，并且直接写入response 正文
	  不会经过视图解析器！！！！！！---->return "bian";改掉 */
	@RequestMapping(value = "/page/{page}",method = RequestMethod.GET)
	@ResponseBody
	private ModelAndView index_num(HttpServletRequest request,ModelAndView mav,@PathVariable("page") int page) {
		List<Img> list = imgService.getAll(page);
		mav.addObject("total", imgDao.getLatestId());	//图片总数
		mav.setViewName("bian");
		mav.addObject("list", list);
		return mav;
	}
	@RequestMapping(value = "/bizhi/{id}",method = RequestMethod.GET)
	private String details(@PathVariable("id") int id,HttpServletRequest request) {
		Img  pic=  imgService.findById(id);
		request.setAttribute("pic", pic);
		return "details";
	}
	//===================IMAGE下载================
	@RequestMapping(value = "/getUserState/{id}")
	@ResponseBody
	private String imgDownload(@CookieValue(name = "sessionID",defaultValue = "0",required = false)String sessionID,
							   HttpServletRequest req, HttpServletResponse resp,
							   @PathVariable("id")String id) throws IOException {
			Map map =new HashMap();

			/*需要补 用户验证登录代码*/
			WxUser wxUser = redisUserService.findUser(sessionID);
			if(wxUser==null||"0".equals(sessionID)){
				map.put("status", UserStatus.NOT_LOGIN.getCode());
				map.put("msg", UserStatus.NOT_LOGIN.getMsg());
				map.put("user", wxUser);
				return JSON.toJSONString(map);
			}
			//会员业务。。会员等级。。是否到期。。省略
				map.put("status", UserStatus.NOT_LOGIN.getCode());
				map.put("msg", UserStatus.NOT_LOGIN.getMsg());
				map.put("user", wxUser);
				Base64.Encoder e = Base64.getEncoder();
				String idEncode = e.encodeToString(id.getBytes());
				map.put("pic", "/download/" + idEncode);

		return JSON.toJSONString(map);
	}
	@RequestMapping(value = "/download/{id}")
	private void imgDownload(HttpServletResponse resp,
							 @PathVariable(value = "id")String id) throws IOException {
		String idDecode = new String(Base64.getDecoder().decode(id));
		System.out.println(idDecode);
		Img img= imgService.findById(Integer.valueOf(idDecode));
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
	/**
	 * 响应输出图片文件
	 * @param response
	 * @param imgFile
	 */
	private void responseFile(HttpServletResponse response, File imgFile) {
		try(InputStream is = new FileInputStream(imgFile);
			OutputStream os = response.getOutputStream();){
			byte [] buffer = new byte[1024]; // 图片文件流缓存池
			while(is.read(buffer) != -1){
				os.write(buffer);
			}
			os.flush();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	//===================IMAGE上传界面导航================	
	@RequestMapping(value = "/imgadd",method = RequestMethod.GET)
	private String imageLogin() {
		return "imageUpload";
	}
	//===================IMAGE上传==================	
	@RequestMapping(value = "/imageUp" , method = RequestMethod.POST)
	private String imageUpload(
			@RequestParam(value = "imageFile")MultipartFile[] multipartFile,
			HttpServletRequest req,
			@RequestParam(value = "imgStyle") String[] selectStyle
			) throws IllegalStateException, IOException {
		if (multipartFile==null) {
			req.setAttribute("msg", "没有选择文件！");
			return "forward:/imgadd";
		}
		imgService.add(multipartFile,selectStyle);
		return "redirect:/imgadd";		//重定向到首页
	}
}