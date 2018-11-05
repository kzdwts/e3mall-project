package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * 广告类型 管理 业务实现层
 * 
 * @author kangyong
 * @date 2018年8月18日 下午4:50:30
 * @version 1.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		// 封装查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

		// 返回结果集
		List<EasyUITreeNode> resultList = new ArrayList<>();
		// 封装成树形结构
		for (TbContentCategory contentCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCat.getId());
			node.setText(contentCat.getName());
			node.setState(contentCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		// 返回结果
		return resultList;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		// 封装pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setStatus(1);// 状态。可选值:1(正常),2(删除)
		contentCategory.setSortOrder(1);// 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		contentCategory.setIsParent(false); // 该类目是否为父类目，1为true，0为false
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 执行插入
		contentCategoryMapper.insert(contentCategory);

		// 查询父节点是否为叶子节点
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentCat.getIsParent()) {
			// 更新父节点状态
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}

		// 返回新增类别的id
		return E3Result.ok(contentCategory);
	}

}
