//package org.whh.bz.web;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@ControllerAdvice  //注解作用：全局异常处理  数据绑定  数据预处理
//public class ExceptionController {
//	@ExceptionHandler
//	private ModelAndView uploadException(ModelAndView modelAndView,Exception e) {
//		modelAndView.addObject("errorMsg", "<h1>ErrorCode:"+ e.getMessage() +"</h1>");
//		e.printStackTrace();
//		modelAndView.setViewName("error");
//		return modelAndView;
//	}
//	@ExceptionHandler
//	private void IllegalStateException(HttpServletRequest req,UserSessionException e,
//										 HttpServletResponse resp) throws IOException {
//		req.setAttribute("errorMsg","缓存已被删除请重新登陆");
//		e.printStackTrace();
//		resp.sendRedirect("/showQRCode");
//	}
//
//}
