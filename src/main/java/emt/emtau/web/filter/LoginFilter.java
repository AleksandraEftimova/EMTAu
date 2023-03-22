package webp.testau.web.filter;

import org.springframework.context.annotation.Profile;
import webp.testau.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
@Profile("servlet")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    //tuka go pravime celoto filtriranje
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //dali postoi istanca t.e. sesija od user i da mu dozvolime da koristi info od requestot
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //dali ima objekt vo attributot user
        User user = (User) request.getSession().getAttribute("user");

        String path = request.getServletPath();
        //dali userot e null, proverka i na patekata za da ne dobieme endless loop
        //i register go dodavame
        if (!"/login".equals(path) && !"/register".equals(path) && !"/main.css".equals(path) && user==null) {
            response.sendRedirect("/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
