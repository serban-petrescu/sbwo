package spet.sbwo.api;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class LocalAddressFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (response instanceof HttpServletResponse) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			try {
				InetAddress address = InetAddress.getByName(request.getRemoteAddr());
				if (!address.isLoopbackAddress() && !address.isAnyLocalAddress()) {
					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
				} else {
					chain.doFilter(request, response);
				}
			} catch (UnknownHostException e1) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}
