package spet.sbwo.api.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthConditionalFilter extends BaseFilter {
    private final String notAuthPath;
    private final String authPath;

    public AuthConditionalFilter(String notAuthPath, String authPath) {
        this.notAuthPath = notAuthPath;
        this.authPath = authPath;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (request.getSession(false) != null) {
            response.sendRedirect(authPath);
        } else {
            response.sendRedirect(notAuthPath);
        }
    }

}
