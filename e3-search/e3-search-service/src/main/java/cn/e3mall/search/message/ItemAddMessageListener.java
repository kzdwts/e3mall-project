package cn.e3mall.search.message;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;

/**
 * 监听商品新增的消息，查询商品信息，将商品信息导入索引库
 * 
 * @author kangyong
 * @date 2018年8月30日 上午11:51:03
 * @version 1.0
 */
public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {
		// 从消息中取商品id
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			// 等待事务提交
			Thread.sleep(1000);

			// 查询商品信息
			SearchItem item = itemMapper.getItemById(itemId);
			// 创建一个文档对象
			SolrInputDocument doc = new SolrInputDocument();
			// 向文档对象中添加域
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_sell_point", item.getSell_point());
			doc.addField("item_image", item.getImage());
			doc.addField("item_category_name", item.getCategory_name());
			// 把文档对象添加到索引库
			solrServer.add(doc);
			// 提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
