package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.content.service.ContentCategoryService;

/**
 * 内容管理 控制层
 * 
 * @author kangyong
 * @date 2018年8月18日 下午5:06:07
 * @version 1.0
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 根据父节点id查询类别列表信息
	 * 
	 * @param parentId 父类id
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
		return contentCategoryService.getContentCatList(parentId);
	}

	/**
	 * 新增类型
	 * 
	 * @param parentId 父节点id
	 * @param name     名称
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCat(Long parentId, String name) {
		return contentCategoryService.addContentCategory(parentId, name);
	}
}
