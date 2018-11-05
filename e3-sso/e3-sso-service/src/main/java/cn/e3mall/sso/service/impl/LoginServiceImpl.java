package cn.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.LoginService;

/**
 * 用户登录 业务处理实现层
 * 
 * @author kangyong
 * @date 2018年9月5日 上午10:40:40
 * @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${SESSION}")
	private String SESSION;

	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public E3Result userLogin(String username, String password) {
		// 1、用户登录
		// 根据用户名查询
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		// 执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			// 2、登录失败，返回失败信息
			return E3Result.build(400, "用户名或密码错误");
		}
		// 获取用户信息
		TbUser user = list.get(0);
		// 比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return E3Result.build(400, "用户名或密码错误");
		}
		// 3、登录成功，生成token
		String token = UUID.randomUUID().toString();
		// 4、用户信息写入redis；key：token ；value：用户信息
		// 5、设置session过期时间，
		user.setPassword(null);
		jedisClient.set(SESSION + ":" + token, JsonUtils.objectToJson(user));
		jedisClient.expire(SESSION + ":" + token, SESSION_EXPIRE);
		// 6、返回token
		return E3Result.ok(token);
	}

}
