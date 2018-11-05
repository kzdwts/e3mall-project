package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 首页展示 控制层
 * 
 * @author kangyong
 * @date 2018年8月17日 下午4:38:11
 * @version 1.0
 */
@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;

	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;

	/**
	 * 展示首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		// 查询首页轮播广告
		List<TbContent> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
		// 把结果传递给页面
		model.addAttribute("ad1List", ad1List);
		return "index";
	}

}
