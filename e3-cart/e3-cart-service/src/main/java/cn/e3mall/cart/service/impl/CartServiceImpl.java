package cn.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;

/**
 * 购物车 业务处理实现层
 * 
 * @author kangyong
 * @date 2018年9月15日 下午5:33:34
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;

	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		// 向redis中添加购物车。
		// 数据类型是hash key：用户id Field：商品id Value：商品信息
		// 判断商品是否存在
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
		// 如果存在数量相加
		if (hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
			// 将json转换成实体类
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			// 设置数量
			tbItem.setNum(tbItem.getNum() + num);
			// 写入redis
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
			return E3Result.ok();
		}
		// 如果不存在，根据商品id取商品信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 设置数量和图片
		item.setNum(num);
		if (StringUtils.isNotBlank(item.getImage())) {
			item.setImage(item.getImage().split(",")[0]);
		}
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));

		// 返回购物车列表
		return E3Result.ok();
	}

	@Override
	public E3Result mergeCart(long userId, List<TbItem> itemList) {
		// 遍历列表
		// 把列表添加到购物车
		// 判断购物车中是否有此商品
		// 如果有，数量相加
		// 如果没有，添加新的商品到服务端购物车
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return E3Result.ok();
	}

	@Override
	public List<TbItem> getCartList(long userId) {
		// 从服务端取出当前用户的购物车
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<TbItem> itemList = new ArrayList<>();
		for (String str : jsonList) {
			// 转成商品对象
			TbItem item = JsonUtils.jsonToPojo(str, TbItem.class);
			// 添加到购物车列表
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		// 取出当前商品项
		String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
		if (StringUtils.isNotBlank(json)) {
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			// 更新购物项数量
			item.setNum(num);
			// 写入redis
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
		}
		return E3Result.ok();
	}

	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		// 直接删除
		jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
		return E3Result.ok();
	}

	@Override
	public E3Result clearCartItem(long userId) {
		jedisClient.del(REDIS_CART_PRE + ":" + userId);
		return E3Result.ok();
	}

}
