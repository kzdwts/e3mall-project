package cn.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;

/**
 * 索引库维护 业务实现层
 * 
 * @author kangyong
 * @date 2018年8月22日 上午8:32:49
 * @version 1.0
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private SolrServer solrServer;

	@Override
	public E3Result importAllItems() {

		try {
			// 查询数据
			List<SearchItem> itemList = itemMapper.getItemList();
			// 遍历数据
			for (SearchItem searchItem : itemList) {
				// 创建搜索文档对象
				SolrInputDocument doc = new SolrInputDocument();
				// 写入字段
				doc.addField("id", searchItem.getId());
				doc.addField("item_title", searchItem.getTitle());
				doc.addField("item_sell_point", searchItem.getSell_point());
				doc.addField("item_price", searchItem.getPrice());
				doc.addField("item_image", searchItem.getImage());
				doc.addField("item_category_name", searchItem.getCategory_name());
				// 添加到索引库
				solrServer.add(doc);
			}
			// 提交
			solrServer.commit();
			// 返回成功信息
			return E3Result.ok();

		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "导入数据到索引库出现异常");
		}
	}

}
