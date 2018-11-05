package cn.e3mall.order.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * 订单业务 接口层
 * 
 * @author kangyong
 * @date 2018年9月17日 下午2:52:30
 * @version 1.0
 */
public interface OrderService {

	/**
	 * 创建订单
	 * 
	 * @param orderInfo 订单内容信息
	 * @return
	 */
	E3Result createOrder(OrderInfo orderInfo);

}
