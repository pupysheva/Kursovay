package ru.mirea.CartService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import ru.mirea.Tokens.PayloadToken;
import ru.mirea.Tokens.Role;
import ru.mirea.Tokens.TokenFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static ru.mirea.Tokens.Role.ADMIN;

@Component
@WebFilter(urlPatterns = "/admin/*")
public class FilterAdminCart implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String tokenHttp = ((HttpServletRequest)servletRequest).getHeader("token");
        PayloadToken payloadToken = TokenFactory.decoderToken(tokenHttp);

        if (payloadToken != null) {
            if (payloadToken.getRole().contains(ADMIN))
                filterChain.doFilter(servletRequest, servletResponse);
            else
                throw new ServletException("Доступ только для администраторов!");
        }
        else {
            throw new ServletException();
        }

    }


    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
