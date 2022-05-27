package org.whh.bz.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.whh.bz.exceptionController.UserSessionException;

import javax.servlet.http.HttpServletRequest;
//@ControllerAdvice  注解作用：全局异常处理  数据绑定  数据预处理
//@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler({IllegalStateException.class})
	private String connectException(HttpServletRequest req, Exception e) {
		req.setAttribute(
				"errorMsg", "<h1>0(ｷ｀ﾟДﾟ´)0<啊呦!>RedisProblem！</h1></br>点击此按钮通知管理员！<button class=\"notice\">通知</button>"
						+ "<script>document.getElementsByClassName(\"notice\")[0].addEventListener(\"click\",function(){var req = new XMLHttpRequest();req.open(\"Get\",\"/RedisProblem\");req.send();});</script>"
		);
		req.setAttribute("errorMsg","<h1>IllegalStateException</h1>");
		e.printStackTrace();
		return "error";
	}
	@ExceptionHandler({UserSessionException.class})
	private String IllegalStateException(HttpServletRequest req,Exception e) {
		req.setAttribute(
				"errorMsg", "<h1>0(ｷ｀ﾟДﾟ´)0<啊呦!>RedisProblem！</h1></br>点击此按钮通知管理员！<button class=\"notice\">通知</button>"
						+ "<script>document.getElementsByClassName(\"notice\")[0].addEventListener(\"click\",function(){var req = new XMLHttpRequest();req.open(\"Get\",\"/RedisProblem\");req.send();});</script>"
		);
		req.setAttribute("errorMsg","<h1>请勿随意删除页面元素，即将跳转首页。。。</h1>");
		e.printStackTrace();
		return "page/1";
	}
}
