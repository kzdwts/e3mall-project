package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.common.pojo.SearchItem;

/**
 * 搜索商品 持久层接口
 * 
 * @author kangyong
 * @date 2018年8月21日 下午11:08:40
 * @version 1.0
 */
public interface ItemMapper {

	/**
	 * 查询商品列表
	 * 
	 * @return
	 */
	List<SearchItem> getItemList();

	/**
	 * 根据商品id，查询商品详情
	 * 
	 * @param itemId
	 * @return
	 */
	SearchItem getItemById(long itemId);

}
