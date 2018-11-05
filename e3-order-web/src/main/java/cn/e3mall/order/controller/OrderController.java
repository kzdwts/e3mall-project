package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

/**
 * 订单管理 控制层
 * 
 * @author kangyong
 * @date 2018年9月16日 下午9:35:34
 * @version 1.0
 */
@Controller
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	/**
	 * 展示订单页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		// 取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		// 根据用户id查询购物车信息
		// if (user != null) {
		List<TbItem> cartList = cartService.getCartList(user.getId()); // cartService.getCartList(5);
		request.setAttribute("cartList", cartList);
		// }

		// TODO 取收货地址列表，取支付方式列表

		// 返回页面
		return "order-cart";
	}
	
	/**
	 * 创建订单
	 * @param orderInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/create")
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
		// 取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		// 添加用户信息到orderInfo中
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		
		// 调用服务生成订单
		E3Result result = orderService.createOrder(orderInfo);
		// 结果
		if (result.getStatus() == 200) {
			request.setAttribute("orderId", result.getData());
		}
		request.setAttribute("payment", orderInfo.getPayment());
		
		// 清空购物车
		cartService.clearCartItem(user.getId());
		return "success";
	}

}
