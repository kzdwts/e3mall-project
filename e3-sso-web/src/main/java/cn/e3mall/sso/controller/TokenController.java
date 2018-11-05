package cn.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;

/**
 * 用户token管理 控制层
 * 
 * @author kangyong
 * @date 2018年9月5日 下午10:49:45
 * @version 1.0
 */
@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;

	/**
	 * 根据token获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		E3Result result = tokenService.getUserByToken(token);
		// 判断是否是jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			// return callback + "(" + JsonUtils.objectToJson(result) + ");";
			// 版本升级
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}

		return JsonUtils.objectToJson(result);
	}
}
