package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

/**
 * 商品搜索 控制层
 * 
 * @author kangyong
 * @date 2018年8月22日 下午11:23:13
 * @version 1.0
 */
@Controller
public class SearchController {

	@Autowired
	public SearchService searchService;

	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;

	/**
	 * 根据关键词，搜索商品
	 * 
	 * @param keyword
	 * @param page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search")
	public String searchItemList(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model)
			throws Exception {
		// 关键词乱码处理
		keyword = new String(keyword.getBytes("iso-8859-1"), "UTF-8");
		// 加载数据
		SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);

		// model数据显示
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecordCount());
		model.addAttribute("itemList", searchResult.getItemList());

		// 返回逻辑视图
		return "search";
	}

}
