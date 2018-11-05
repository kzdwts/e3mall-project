package cn.e3mall.content.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.content.service.ContentCategoryService;

/**
 * 商品类别管理测试类
 * 
 * @author kangyong
 * @date 2018年8月18日 下午6:46:09
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:spring/applicationContext-dao.xml",
		"classpath:spring/applicationContext-trans.xml",
		"classpath:spring/applicationContext-service.xml"})
public class ContentCategoryServiceImplTest {

	@Autowired
	private ContentCategoryService contentCategoryService;

	public void testGetContentCatList() {
	}

	@Test
	public void testAddContentCategory() {
		long parentId = 30;
		String name = "测试新增类别";
		E3Result result = contentCategoryService.addContentCategory(parentId, name);
		System.out.println(result.getStatus() + result.getData().toString());
	}

}
