package com.wipro.ta.samplebank.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "authenticationFilter")
public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (!isPublicUri(request.getRequestURI()) && !hasValidSession(request)) {
			request.setAttribute("login", new Login());
			request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private boolean hasValidSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute("username") != null;
	}

	private boolean isPublicUri(String uri) {
		List<String> privatePaths = new ArrayList<>();
		privatePaths.add("/admin/");
		for(String privatePath : privatePaths) {
			if(uri.contains(privatePath)) {
				return false;
			}
		}
		return true;
	}
}