package cn.e3mall.sso.service;
/**
 * 用户注册 业务处理接口层
 * @author kangyong
 * @date 2018年9月4日 下午10:10:57
 * @version 1.0
 */

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbUser;

public interface RegisterService {

	/**
	 * 检查数据
	 * 
	 * @param param 数据
	 * @param type  类型
	 * @return
	 */
	E3Result checkData(String param, int type);

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	E3Result register(TbUser user);
	
}
