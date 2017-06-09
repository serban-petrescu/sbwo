package spet.sbwo.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalAddressFilter extends BaseFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LocalAddressFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        try {
            InetAddress address = InetAddress.getByName(request.getRemoteAddr());
            if (!address.isLoopbackAddress() && !address.isAnyLocalAddress()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                chain.doFilter(request, response);
            }
        } catch (UnknownHostException ex) {
            LOG.error("Forcing forbidden response because of error.", ex);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
