package cn.e3mall.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

/**
 * fastdfs文件上传测试
 * 
 * @author kangyong
 * @date 2018年8月16日 下午3:50:08
 * @version 1.0
 */
public class FastDfsTest {

	/**
	 * 测试文件上传
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTestUpload() throws Exception {
		
		// 创建一个配置文件。文件名任意。内容就是tracker服务器的地址
		// 使用全局对象加载配置文件
		ClientGlobal
				.init("E:/code/doit/num03/eshop/e3-manager-web/src/main/resources/conf/client.conf");
		// 创建trackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		// 通过TrackClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		// 创建一个StorageServer的引用，可以是null
		StorageServer storageServer = null;
		// 创建一个StorageClient，参数需要trackerClient、StorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 使用StorageClient上传文件
		String[] strings = storageClient.upload_file("C:/Users/pc/Pictures/Saved Pictures/hadoop-logo.jpg", "jpg",
				null);
		for (String string : strings) {
			System.out.println(string);
		}
		
	}
	
	@Test
	public void testFastDfsUpload() throws Exception {
		FastDFSClient client = new FastDFSClient("E:/code/doit/num03/eshop/e3-manager-web/src/main/resources/conf/client.conf");
		String string = client.uploadFile("C:\\Users\\pc\\Pictures\\广告图片\\2.jpg");
		System.out.println(string);
	}

}
