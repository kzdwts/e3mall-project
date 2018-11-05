package cn.e3mall.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

/**
 * 分页测试
 * 
 * @author kangyong
 * @date 2018年8月14日 上午10:35:48
 * @version 1.0
 */
public class PageHelperTest {

	@Test
	public void testPageHelper() {
		// 加载spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-dao.xml");
		// 从容器中获取mapper代理对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		// 设置分页
		PageHelper.startPage(1, 10);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		System.out.println("list.size()=" + list.size());
		// 获取总数量
		PageInfo<TbItem> pageInfo = new PageInfo(list);
		System.out.println("pageInfo.getSize()=" + pageInfo.getSize());
		System.out.println("pageInfo.getTotal()=" + pageInfo.getTotal());

	}

}
