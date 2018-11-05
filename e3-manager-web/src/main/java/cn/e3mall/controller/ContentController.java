package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 内容管理控制层
 * 
 * @author kangyong
 * @date 2018年8月18日 下午7:55:59
 * @version 1.0
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	/**
	 * 查询内容列表
	 * 
	 * @param categoryId 类别id
	 * @param page       当前页码
	 * @param rows       每页数量
	 * @return
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentQueryList(long categoryId, int page, int rows) {
		return contentService.getContentList(categoryId, page, rows);
	}

	/**
	 * 新增内容
	 * 
	 * @param content 内容信息
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent content) {
		return contentService.addContent(content);
	}

}
