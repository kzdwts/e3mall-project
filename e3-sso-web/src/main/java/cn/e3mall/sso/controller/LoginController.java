package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.sso.service.LoginService;

/**
 * 用户登录 控制层
 * 
 * @author kangyong
 * @date 2018年9月4日 下午5:07:55
 * @version 1.0
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;

	/**
	 * 显示登录页面
	 * 
	 * @return
	 */
	@RequestMapping("/page/login")
	public String showLogin(String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}

	/**
	 * 用户登录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		E3Result result = loginService.userLogin(username, password);
		// 判断是否登录成功
		if (result.getStatus() == 200) {
			String token = result.getData().toString();
			// 将token写入cookie
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		}

		return result;
	}
}
