package cn.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * token业务处理 实现层
 * 
 * @author kangyong
 * @date 2018年9月5日 下午10:35:14
 * @version 1.0
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${SESSION}")
	private String SESSION;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public E3Result getUserByToken(String token) {
		// 取token
		String json = jedisClient.get(SESSION + ":" + token);
		if (StringUtils.isBlank(json)) {
			// 取不到用户信息，返回重新登录
			return E3Result.build(201, "用户身份已过期，请重新登录");
		}
		// 取到用户信息，更新过期时间
		jedisClient.expire(SESSION + ":" + token, SESSION_EXPIRE);
		// 获取用户信息
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		// 返回结果
		return E3Result.ok(user);
	}

}
