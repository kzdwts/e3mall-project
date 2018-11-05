package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;

/**
 * 商品查询 数据访问层
 * 
 * @author kangyong
 * @date 2018年8月22日 下午10:39:06
 * @version 1.0
 */
@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;

	/**
	 * 根据查询条件查询索引库
	 * 
	 * @param query 查询条件
	 * @return
	 * @throws SolrServerException
	 */
	public SearchResult search(SolrQuery query) throws SolrServerException {
		// 根据query查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		// 取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// 取查询结果总记录数
		long numFound = solrDocumentList.getNumFound();
		// 取商品列表，需要高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		List<SearchItem> itemList = new ArrayList();
		for (SolrDocument solrDocument : solrDocumentList) {
			// 创建SearchItem
			SearchItem item = new SearchItem();
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			// 取高亮显示
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			// 添加到集合
			itemList.add(item);
		}
		// 返回结果
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecordCount(numFound);

		return result;
	}

}
