package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;

/**
 * 注册页面 控制层
 * 
 * @author kangyong
 * @date 2018年9月4日 下午5:00:53
 * @version 1.0
 */
@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	/**
	 * 显示注册页面
	 * 
	 * @return
	 */
	@RequestMapping("/page/register")
	private String showRegister() {
		return "register";
	}

	/**
	 * 校验数据是否重复
	 * 
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
		return registerService.checkData(param, type);
	}

	/**
	 * 用户注册
	 * 
	 * @param user 用户信息
	 * @return
	 */
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser user) {
		return registerService.register(user);
	}

}
