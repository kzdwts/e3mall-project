package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;

/**
 * 商品类别管理 接口
 * 
 * @author kangyong
 * @date 2018年8月14日 下午2:35:48
 * @version 1.0
 */
public interface ItemCatService {

	/**
	 * 获取同一级别的类别列表
	 * 
	 * @param parentId 父节点id
	 * @return 类别列表
	 */
	List<EasyUITreeNode> getItemCatList(long parentId);

}
