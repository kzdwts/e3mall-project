package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

/**
 * 图片上传 控制层
 * 
 * @author kangyong
 * @date 2018年8月17日 上午10:06:53
 * @version 1.0
 */
@Controller
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	/**
	 * 上传图片
	 * 
	 * @param uploadFile
	 * @return 上传结果
	 */
	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try {
			// 读取配置信息
			FastDFSClient dfsClient = new FastDFSClient("classpath:conf/client.conf");
			// 取文件扩展名
			String filename = uploadFile.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".") + 1);
			// 将图片上传到服务器
			String url = dfsClient.uploadFile(uploadFile.getBytes(), extName);
			// 补充完整图片路径
			url = IMAGE_SERVER_URL + url;

			// 封装结果
			Map map = new HashMap();
			map.put("error", 0);
			map.put("url", url);
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			Map map = new HashMap();
			map.put("error", 1);
			map.put("message", "图片上传失败");
			return JsonUtils.objectToJson(map);
		}
	}

}
