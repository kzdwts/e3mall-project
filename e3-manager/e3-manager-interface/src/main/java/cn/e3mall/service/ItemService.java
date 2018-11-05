package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

/**
 * 商品管理 业务 接口
 * 
 * @author kangyong
 * @date 2018年8月12日 下午12:08:07
 * @version 1.0
 */
public interface ItemService {

	/**
	 * 根据商品id，查询时商品信息
	 * 
	 * @param itemId 商品id
	 * @return 商品信息
	 */
	TbItem getItemById(long itemId);

	/**
	 * 商品列表
	 * 
	 * @param page 当前页码
	 * @param row  每页数量
	 * @return 商品集合
	 */
	EasyUIDataGridResult getItemList(int page, int rows);

	/**
	 * 新增商品
	 * 
	 * @param item 商品信息
	 * @param desc 商品描述
	 * @return 执行结果
	 */
	E3Result addItem(TbItem item, String desc);

	/**
	 * 根据商品id，查询商品描述
	 * 
	 * @param itemId
	 * @return
	 */
	TbItemDesc getItemDescById(long itemId);

}
