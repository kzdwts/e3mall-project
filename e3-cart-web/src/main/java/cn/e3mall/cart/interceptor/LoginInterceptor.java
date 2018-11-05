package cn.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 用户登录 自定义拦截器
 * 
 * @author kangyong
 * @date 2018年9月15日 下午5:00:28
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;

	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 前处理，执行handler之前执行此方法
		// 返回true，放行 false：拦截

		// 1、从cookie中取token
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		// 2、如果没有token，未登录状态，直接放行
		if (StringUtils.isBlank(token)) {
			// 放行
			return true;
		}
		// 3、取到token，需要调用sso系统的服务，根据token取用户信息
		E3Result e3Result = tokenService.getUserByToken(token);
		// 4、没有取到用户信息。登录过期，直接放行
		if (e3Result.getStatus() == 201) {
			// 用户信息过期，放行
			return true;
		}
		// 5、取到用户信息。登录状态
		TbUser user = (TbUser) e3Result.getData();
		// 6、把用户信息放入request中。只需要在request中判断request是否包含user信息
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// handler执行之后，返回ModelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 完成处理，返回ModelAndView之后
		// 可以在此处理异常

	}

}
