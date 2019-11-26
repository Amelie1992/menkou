package com.wzlue.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XSS过滤
 * @author chenshun
 * @email wzlue.com
 * @date 2017-04-01 10:20
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		/*解决跨域问题*/
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest rep = (HttpServletRequest) request;
//		res.setHeader("Access-Control-Allow-Origin",  rep.getHeader("Origin"));
		res.setHeader("Access-Control-Allow-Origin",  "*");
		//允许跨域请求中携带cookie
		res.addHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers", "authorization,Origin,token, No-Cache, X-Requested-With, "
				+ "If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, " + "Content-Type, X-E4M-With");
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}