package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索结果实体类
 * 
 * @author kangyong
 * @date 2018年8月22日 下午10:34:07
 * @version 1.0
 */
public class SearchResult implements Serializable {

	private long recordCount;
	private int totalPages;
	private List<SearchItem> itemList;

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<SearchItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}

}
