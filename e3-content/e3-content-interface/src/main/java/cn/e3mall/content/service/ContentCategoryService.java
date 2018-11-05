package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUITreeNode;

/**
 * 广告内容分类 接口层
 * 
 * @author kangyong
 * @date 2018年8月18日 下午4:43:30
 * @version 1.0
 */
public interface ContentCategoryService {

	/**
	 * 广告内容分类列表
	 * 
	 * @param parentId 父节点id
	 * @return
	 */
	List<EasyUITreeNode> getContentCatList(long parentId);

	/**
	 * 新增类别
	 * 
	 * @param parentId 父节点id
	 * @param name     名称
	 * @return
	 */
	E3Result addContentCategory(long parentId, String name);

}
