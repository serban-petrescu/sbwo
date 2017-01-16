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

public class AuthConditionalFilter extends BaseService implements Filter {
	private String notAuthPath;
	private String authPath;

	public AuthConditionalFilter(String notAuthPath, String authPath) {
		this.notAuthPath = notAuthPath;
		this.authPath = authPath;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if (httpRequest.getSession(false) != null) {
				httpResponse.sendRedirect(authPath);
			} else {
				httpResponse.sendRedirect(notAuthPath);
			}
		}
	}

	@Override
	public void destroy() {
	}

}
