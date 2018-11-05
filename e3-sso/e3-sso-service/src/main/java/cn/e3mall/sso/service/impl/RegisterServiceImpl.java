package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;

/**
 * 用户注册 业务实现层
 * 
 * @author kangyong
 * @date 2018年9月4日 下午10:14:15
 * @version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;

	@Override
	public E3Result checkData(String param, int type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 判断类型，设置查询条件，1、用户名 2、手机号 3、邮箱
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return E3Result.build(400, "类型参数错误");
		}

		// 执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}

	@Override
	public E3Result register(TbUser user) {
		// 数据有效性校验
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPhone())) {
			return E3Result.build(400, "数据不完整");
		}
		// 类型校验，1、用户名 2、手机号 3、邮箱
		E3Result result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "用户名已经被占用");
		}
		result = checkData(user.getPhone(), 2);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "手机号已经被占用");
		}
		// 补全pojo
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 密码MD5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		// 插入到数据库
		userMapper.insert(user);
		// 返回信息
		return E3Result.ok();
	}

}
