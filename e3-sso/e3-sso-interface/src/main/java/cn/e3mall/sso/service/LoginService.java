package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.E3Result;

/**
 * 用户登录 业务处理接口层
 * 
 * @author kangyong
 * @date 2018年9月5日 上午10:39:04
 * @version 1.0
 */
public interface LoginService {

	/**
	 * 用户登录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	E3Result userLogin(String username, String password);
	
}
