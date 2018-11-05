package cn.e3mall.solrj;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * solrJ测试
 * 
 * @author kangyong
 * @date 2018年8月21日 下午11:20:00
 * @version 1.0
 */
public class TestSolrJ {

	/**
	 * 新增
	 * 
	 * @throws Exception
	 */
	@Test
	public void addDocument() throws Exception {
		// 创建一个solrServer，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.10.132:8080/solr/collection1");
		// 创建一个文档对象SolrInputDocuments
		SolrInputDocument doc = new SolrInputDocument();
		// 向文档对象中添加域，文档对象必须包含一个id域，所有的域名称必须在schema.xml中定义
		doc.addField("id", "00001");
		doc.addField("item_title", "王丽丽");
		doc.addField("item_price", 10009);

		// 把文档写入索引库
		solrServer.add(doc);
		// 提交
		solrServer.commit();
	}

	/**
	 * 删除
	 * 
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void deleteDocument() throws SolrServerException, IOException {
		SolrServer solrServer = new HttpSolrServer("http://192.168.10.132:8080/solr/collection1");
		solrServer.deleteById("00001");
		solrServer.commit();
	}

	/**
	 * 查询
	 * 
	 * @throws SolrServerException
	 */
	@Test
	public void queryIndex() throws SolrServerException {
		// 创建一个solrserver
		SolrServer solrServer = new HttpSolrServer("http://192.168.10.132:8080/solr/collection1");
		// 创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		query.set("q", "*:*");
		// 执行查询，QueryResponse
		QueryResponse queryResponse = solrServer.query(query);
		// 取文档列表，取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		// 遍历文档列表，取域
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}

	/**
	 * 加条件查询
	 * 
	 * @throws SolrServerException
	 */
	@Test
	public void query() throws SolrServerException {
		SolrServer solrServer = new HttpSolrServer("http://192.168.10.132:8080/solr/collection1");
		// 创建搜索对象
		SolrQuery query = new SolrQuery();
		query.setQuery("手机");
		query.setStart(0);
		query.setRows(20);
		query.set("df", "item_title");
		query.setHighlight(true);
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");

		// 执行查询
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询数量：" + solrDocumentList.getNumFound());
		// 遍历
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			// 取高亮
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}

}
