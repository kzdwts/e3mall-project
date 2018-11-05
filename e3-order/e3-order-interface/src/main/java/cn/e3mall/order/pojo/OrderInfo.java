package cn.e3mall.order.pojo;

import java.util.List;

import cn.e3mall.pojo.TbOrder;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

/**
 * 订单信息 实体类
 * 
 * @author kangyong
 * @date 2018年9月17日 下午2:43:46
 * @version 1.0
 */
public class OrderInfo extends TbOrder {

	/**
	 * 订单项集合
	 */
	private List<TbOrderItem> orderItems;

	/**
	 * 物流信息
	 */
	private TbOrderShipping orderShipping;

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

}
