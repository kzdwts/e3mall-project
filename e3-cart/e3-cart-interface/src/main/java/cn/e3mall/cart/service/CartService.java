package cn.e3mall.cart.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;

/**
 * 购物车 业务处理接口层
 * 
 * @author kangyong
 * @date 2018年9月15日 下午5:32:43
 * @version 1.0
 */
public interface CartService {

	/**
	 * 购物车添加商品
	 * 
	 * @param userId 用户id
	 * @param itemId 商品id
	 * @return
	 */
	E3Result addCart(long userId, long itemId, int num);

	/**
	 * 合并购物车列表
	 * 
	 * @param userId   用户id
	 * @param itemList cookie中购物车商品列表
	 * @return
	 */
	E3Result mergeCart(long userId, List<TbItem> itemList);

	/**
	 * 从服务端取出购物车列表
	 * 
	 * @param userId 用户id
	 * @return
	 */
	List<TbItem> getCartList(long userId);

	/**
	 * 更新购物车商品数量
	 * 
	 * @param userId 用户id
	 * @param itemId 商品id
	 * @param num    数量
	 * @return
	 */
	E3Result updateCartNum(long userId, long itemId, int num);

	/**
	 * 删除购物项
	 * 
	 * @param userId 用户id
	 * @param itemId 商品id
	 * @return
	 */
	E3Result deleteCartItem(long userId, long itemId);
	
	/**
	 * 删除购物车
	 * @param userId 用户id
	 * @return
	 */
	E3Result clearCartItem(long userId);

}
