//package com.craig.greggames.config;
//
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//
//import com.craig.greggames.util.WebUtils;
//
//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class SimpleCORSFilter implements Filter{
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		HttpServletRequest request = (HttpServletRequest) req;
//	    HttpServletResponse response = (HttpServletResponse) res;
//	  
//	    System.out.println(WebUtils.getHeadersInfo(request).toString());
//	    response.setHeader("Access-Control-Allow-Origin", System.getProperty("CROSS_ORIGIN"));
//	    response.setHeader("X-Access-Control-Allow-Origin", System.getProperty("CROSS_ORIGIN"));
//	    response.setHeader("Access-Control-Allow-Credentials", "true");
//	    response.setHeader("X-Access-Control-Allow-Credentials", "true");
//	    response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
//	    response.setHeader("X-Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
//	    response.setHeader("Access-Control-Max-Age", "3600");
//	    response.setHeader("X-Access-Control-Max-Age", "3600");
//	    response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,content-type,application/json");
//	    response.setHeader("X-Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,content-type,application/json");
//	    
//	    chain.doFilter(req, res);
//		
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	
//
//	
//
//}
