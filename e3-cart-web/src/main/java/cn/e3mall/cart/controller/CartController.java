package cn.e3mall.cart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;

/**
 * 购物车处理 控制层
 * 
 * @author kangyong
 * @date 2018年9月15日 上午10:10:05
 * @version 1.0
 */
@Controller
public class CartController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private CartService cartService;

	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;

	/**
	 * 添加商品到购物车
	 * 
	 * @param itemId   商品id
	 * @param num      商品数量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 从request中取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 购物车信息写入cookie
			cartService.addCart(user.getId(), itemId, num);
			// 返回逻辑视图
			return "cartSuccess";
		}

		// 如果request中没有用户
		// 从cookie中取商品列表
		List<TbItem> cartList = getCartListFromCookie(request);
		// 判断购物车是否有该商品，
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			// 有此商品
			if (tbItem.getId() == itemId.longValue()) {
				flag = true;
				// 更新购物车该商品数量
				tbItem.setNum(tbItem.getNum() + num);
				break;
			}
		}
		// 购物车没有该商品，查询，添加
		if (!flag) {
			// 查询
			TbItem item = itemService.getItemById(itemId);
			// 设置为第一张图片
			String image = item.getImage();
			if (StringUtils.isNotBlank(image)) {
				item.setImage(image.split(",")[0]);
			}
			// 设置商品数量
			item.setNum(num);
			// 添加到购物车
			cartList.add(item);
		}

		// 购物车存入cookie,设置过期时间（秒）
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		return "cartSuccess";
	}

	/**
	 * 从购物车取商品列表
	 * 
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		// 从cookie中取商品列表信息
		String json = CookieUtils.getCookieValue(request, "cart", true);
		// 判断是否为空
		if (StringUtils.isBlank(json)) {
			return new ArrayList<>();
		}
		// 如果不为空
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}

	/**
	 * 显示购物车列表页面
	 * 
	 * @param request
	 * @param response
	 * 
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, HttpServletResponse response) {
		// 从cookie取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);

		// 判断用户是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 已登录状态,从cookie中取购物车列表，如果不为空同步到服务端购物车
			cartService.mergeCart(user.getId(), cartList);
			// 删除cookie中的购物车列表
			CookieUtils.deleteCookie(request, response, "cart");
			// 从服务端取购物车列表
			cartList = cartService.getCartList(user.getId());
		}

		// 如果未登录
		// model.addAttribute("cartList", cartList);
		request.setAttribute("cartList", cartList);
		// 显示逻辑视图
		return "cart";
	}

	/**
	 * 更新购物车商品数量
	 * 
	 * @param itemId   商品id
	 * @param num      商品数量
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 已登录状态,修改购物车数量
			cartService.updateCartNum(user.getId(), itemId, num);
			// 返回结果
			return E3Result.ok();
		}

		// 获取购物车
		List<TbItem> cartList = getCartListFromCookie(request);
		// 遍历找到当前商品，修改数量
		for (TbItem tbItem : cartList) {
			// 找到当前商品
			if (tbItem.getId() == itemId.longValue()) {
				// 更新商品数量
				tbItem.setNum(num);
				break;
			}
		}

		// 商品存入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		// 返回成功
		return E3Result.ok();
	}

	/**
	 * 删除购物车商品
	 * 
	 * @param itemId   商品id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 已登录状态,修改购物车数量
			cartService.deleteCartItem(user.getId(), itemId);
			// 重定向到列表页面
			return "redirect:/cart/cart.html";
		}

		// 获取cookie中的购物车
		List<TbItem> cartList = getCartListFromCookie(request);
		// 执行删除
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				// 删除
				cartList.remove(tbItem);
				break;
			}
		}
		// 更新购物车
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		// 重定向到列表页面
		return "redirect:/cart/cart.html";
	}

}
