package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.E3Result;

/**
 * token业务处理接口层
 * 
 * @author kangyong
 * @date 2018年9月5日 下午10:32:36
 * @version 1.0
 */
public interface TokenService {

	/**
	 * 根据token取用户信息
	 * 
	 * @param token token值
	 * @return
	 */
	E3Result getUserByToken(String token);
	
}
