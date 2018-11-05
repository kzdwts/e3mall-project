package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * 商品业务处理 控制层
 * 
 * @author kangyong
 * @date 2018年9月1日 下午4:10:20
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 跳转到商品详情页面
	 * 
	 * @param itemId 商品id
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable long itemId, Model model) {
		// 执行查询
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		// 查询商品描述
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
		// 存入逻辑视图数据中
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		// 返回逻辑视图
		return "item";
	}

}
