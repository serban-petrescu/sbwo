package spet.sbwo.api.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static spet.sbwo.api.service.base.BaseService.*;

public class CsrfTokenFilter extends BaseFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        String header = request.getHeader(X_CSRF_TOKEN_HEADER);
        String token = (String) request.getSession().getAttribute(X_CSRF_TOKEN_HEADER);
        if ("POST".equals(request.getMethod()) && (header == null || token == null || !header.equals(token))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            if (X_CSRF_TOKEN_HEADER_FETCH.equals(header)) {
                token = getOrCreateCsrfToken(request.getSession());
                response.setHeader(X_CSRF_TOKEN_HEADER, token);
            }
            chain.doFilter(request, response);
        }
    }

}
