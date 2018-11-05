package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

/**
 * 商品实体类
 * 
 * @author kangyong
 * @date 2018年8月30日 下午11:27:10
 * @version 1.0
 */
public class Item extends TbItem {

	public Item(TbItem item) {
		this.setId(item.getId());
		this.setTitle(item.getTitle());
		this.setSellPoint(item.getSellPoint());
		this.setPrice(item.getPrice());
		this.setNum(item.getNum());
		this.setBarcode(item.getBarcode());
		this.setImage(item.getImage());
		this.setCid(item.getCid());
		this.setStatus(item.getStatus());
		this.setCreated(item.getCreated());
		this.setUpdated(item.getUpdated());
	}

	/**
	 * 获取图片数组
	 * 
	 * @return
	 */
	public String[] getImages() {
		String image2 = this.getImage();
		if (null != image2 && !"".equals(image2)) {
			String[] strings = image2.split(",");
			return strings;
		}
		return null;
	}
}
