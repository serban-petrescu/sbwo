package spet.sbwo.api;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CsrfTokenFilter extends BaseService implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			String header = httpRequest.getHeader(X_CSRF_TOKEN_HEADER);
			String token = (String) httpRequest.getSession().getAttribute(X_CSRF_TOKEN_HEADER);
			if ("POST".equals(httpRequest.getMethod()) && (header == null || token == null || !header.equals(token))) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
			} else {
				if (X_CSRF_TOKEN_HEADER_FETCH.equals(header)){
					token = getOrCreateCsrfToken(httpRequest.getSession());
					httpResponse.setHeader(X_CSRF_TOKEN_HEADER, token);
				}
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}
