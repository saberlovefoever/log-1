package org.whh.bz.web;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.whh.bz.dao.WebsiteDao;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class navigationController {

    @Resource
    WebsiteDao websiteDao ;

    @RequestMapping("/")
    private String getWebsites(HttpServletRequest request){
        List websiteList = websiteDao.getAllWebsite();
        request.setAttribute("websiteList",websiteList);
        return "index";
    }
}
