package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * EasyUI 数据结果集
 * 
 * @author kangyong
 * @date 2018年8月14日 上午11:06:38
 * @version 1.0
 */
public class EasyUIDataGridResult implements Serializable {

	private long total;

	private List rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
