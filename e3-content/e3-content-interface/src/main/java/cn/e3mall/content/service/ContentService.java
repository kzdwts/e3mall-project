package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

	/**
	 * 根据类别id查询内容信息
	 * 
	 * @param categoryId 类别id
	 * @param page       当前页码
	 * @param rows       每页数量
	 * @return
	 */
	EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

	/**
	 * 新增记录
	 * 
	 * @param content 内容信息
	 * @return
	 */
	E3Result addContent(TbContent content);

	/**
	 * 根据内容分类id查询内容列表
	 * 
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentListByCid(long cid);

}
