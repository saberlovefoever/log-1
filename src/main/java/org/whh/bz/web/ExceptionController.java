package org.whh.bz.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.whh.bz.enums.UploadStatus;
import org.whh.bz.exceptions.UploadException;
import org.whh.bz.exceptions.UserSessionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice  //注解作用：全局异常处理  数据绑定  数据预处理
public class ExceptionController {
	@ExceptionHandler({UploadException.class})
	@ResponseStatus(HttpStatus.ACCEPTED)
	private String uploadException(HttpServletRequest req, UploadException e) {
		req.setAttribute(
				"errorMsg", "<h1>ErrorCode:"+ e.getA() +"UploadStatus:"+e.getS()+"</h1>"
		);
		e.printStackTrace();
		return "error";
	}
	@ExceptionHandler({UserSessionException.class})
	private void IllegalStateException(HttpServletRequest req,UserSessionException e,
										 HttpServletResponse resp) throws IOException {
		req.setAttribute("errorMsg","请勿随意删除页面元素，即将跳转首页");
		e.printStackTrace();
		resp.sendRedirect("/page/1");
	}
}
