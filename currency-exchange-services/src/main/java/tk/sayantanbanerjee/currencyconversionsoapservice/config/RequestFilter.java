package tk.sayantanbanerjee.currencyconversionsoapservice.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) res;
		String header = request.getHeader("Authorization");
		System.out.println("header is "+header);
//		if (header == null || !header.startsWith("Bearer")) {
//			chain.doFilter(request, res);
//			return;
//		}
//
//		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		chain.doFilter(request, response);
	}
}
