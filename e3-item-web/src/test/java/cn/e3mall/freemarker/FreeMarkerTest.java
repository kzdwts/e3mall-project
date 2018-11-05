package cn.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 测试生成freemarker静态页面
 * 
 * @author kangyong
 * @date 2018年9月1日 下午7:04:40
 * @version 1.0
 */
public class FreeMarkerTest {

	@Test
	public void testFreeMarker() throws Exception {
		// 1、创建一个模板文件
		// 2、创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 3、设置模板文件的保存目录
		configuration.setDirectoryForTemplateLoading(
				new File("E:\\code\\doit\\num03\\eshop\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		// 4、模板文件的编码格式，utf-8
		configuration.setDefaultEncoding("utf-8");
		// 5、加载一个模板文件，生成模板
		Template template = configuration.getTemplate("hello.ftl");
		// 6、创建一个数据集，可以是pojo也可以是map对象。推荐使用map，灵活
		Map data = new HashMap();
		data.put("hello", "hello freemarker !");
		// 7、创建一个Writer对象，指定输出文件的路径及文件名
		Writer out = new FileWriter(new File("C:\\Users\\pc\\Desktop\\hello.txt"));
		// 8、生成静态页面
		template.process(data, out);
		// 9、关闭流
		out.close();
	}

	@Test
	public void testFreeMarkerProHtml() throws Exception {
		// 1、创建一个模板文件
		// 2、创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 3、设置模板属性：模板文件保存的目录，编码格式
		configuration.setDirectoryForTemplateLoading(
				new File("E:\\code\\doit\\num03\\eshop\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		configuration.setDefaultEncoding("utf-8");
		// 4、加载模板
		Template template = configuration.getTemplate("student.ftl");
		// 5、创建数据集
		Map data = new HashMap();
		Student student = new Student(1, "储小姣", 22, "安庆市潜山县");
		data.put("student", student);
		// 新增列表数据
		List<Student> stuList = new ArrayList();
		stuList.add(new Student(1, "储小姣", 21, "安徽天柱山"));
		stuList.add(new Student(2, "王丽丽", 21, "晚会阜阳"));
		stuList.add(new Student(3, "钱梦婷", 21, "安徽蚌埠"));
		stuList.add(new Student(4, "鲍凤", 21, "安徽天柱山"));
		stuList.add(new Student(5, "张三", 21, "安徽天柱山"));
		stuList.add(new Student(6, "李四", 21, "安徽天柱山"));
		data.put("stuList", stuList);
		// 日期
		data.put("date", new Date());
		// null值
		data.put("val", null);
		data.put("hello", "hello freemarker");
		// 6、创建一个Writer对象，指定输出文件的名称
		Writer out = new FileWriter(new File("C:\\Users\\pc\\Desktop\\student.html"));
		// 7、 生成静态页面
		template.process(data, out);
		// 8、关闭流
		out.close();
	}

}
