package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * 搜索服务 接口层
 * 
 * @author kangyong
 * @date 2018年8月22日 下午10:58:59
 * @version 1.0
 */
public interface SearchService {

	/**
	 * 商品搜所
	 * 
	 * @param keyword 关键词
	 * @param page    当前页码
	 * @param rows    每页数量
	 * @return
	 * @throws SolrServerException
	 */
	SearchResult search(String keyword, int page, int rows) throws Exception;

}
