package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.search.service.SearchItemService;

/**
 * 导入商品数据到索引库 控制层
 * 
 * @author kangyong
 * @date 2018年8月22日 上午9:03:53
 * @version 1.0
 */
@Controller
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;

	/**
	 * 一键导入
	 * 
	 * @return
	 */
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList() {
		return searchItemService.importAllItems();
	}
}
