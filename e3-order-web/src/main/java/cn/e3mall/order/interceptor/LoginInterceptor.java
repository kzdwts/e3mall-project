package cn.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 用户登录拦截
 * 
 * @author kangyong
 * @date 2018年9月16日 下午10:07:21
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CartService cartService;

	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;

	@Value("${SSO_URL}")
	private String SSO_URL;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从cookie中取token
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			// 如果没有token，用户未登录，跳转到用户登录页面，登录完成后跳转回当前请求页面
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			// 拦截
			return false;
		}
		// 如果有token，调用sso的服务，查询用户信息
		E3Result result = tokenService.getUserByToken(token);
		// 如果没有用户信息表示用户登录已过期，要求重新登录
		if (result.getStatus() != 200) {
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			// 拦截
			return false;
		}
		// 如果有用户信息，将用户信息写入request域中
		TbUser user = (TbUser) result.getData();
		request.setAttribute("user", user);
		// 判断cookie中是否有购物车数据，如果有就合并到服务端redis中
		String jsonCartList = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isNotBlank(jsonCartList)) {
			// 同步到服务器redis
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
		}
		// 放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
