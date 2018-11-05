package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 测试solr集群
 * 
 * @author kangyong
 * @date 2018年8月25日 下午12:34:43
 * @version 1.0
 */
public class TestSolrCloud {

	@Test
	public void testAddDocument() throws Exception {
		// 创建一个solr集群的连接，应该使用cloudSolrServer
		// zkHost：集群的地址列表
		CloudSolrServer solrServer = new CloudSolrServer("192.168.10.132:2181,192.168.10.132:2182,192.168.10.132:2183");
		// 设置默认搜索域
		solrServer.setDefaultCollection("collection2");

		// 创建文档对象
		SolrInputDocument doc = new SolrInputDocument();

		// 添加内容
		doc.addField("id", "solrcloud01");
		doc.addField("item_title", "测试商品");
		doc.addField("item_price", 123);
		doc.addField("item_image", "http://t2.hddhhn.com/uploads/tu/201707/115/60.jpg");
		// 添加到索引库
		solrServer.add(doc);
		// 提交
		solrServer.commit();
	}

	@Test
	public void testQueryDocument() throws Exception {
		CloudSolrServer cloudSolrServer = new CloudSolrServer(
				"192.168.10.132:2181,192.168.10.132:2182,192.168.10.132:2183");
		cloudSolrServer.setDefaultCollection("collection2");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		// 执行查询
		QueryResponse queryResponse = cloudSolrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询数量：" + solrDocumentList.getNumFound());
		// 遍历
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
}
