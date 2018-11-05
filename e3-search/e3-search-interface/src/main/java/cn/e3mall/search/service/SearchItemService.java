package cn.e3mall.search.service;

import cn.e3mall.common.pojo.E3Result;

/**
 * 索引库维护 接口层
 * 
 * @author kangyong
 * @date 2018年8月22日 上午8:31:25
 * @version 1.0
 */
public interface SearchItemService {

	/**
	 * 导入数据到索引库
	 * 
	 * @return
	 */
	E3Result importAllItems();

}
