package com.springapp.mvc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yyy on 2016/5/13.
 */
public class JspFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hresp = (HttpServletResponse) response;
        String name = hreq.getRequestURL().substring(
                hreq.getRequestURL().lastIndexOf("/") + 1,
                hreq.getRequestURL().lastIndexOf("."));
        String path = hreq.getRequestURL().toString();

        if (path.substring(path.lastIndexOf(".")).equals(".htm")  && (null == hreq.getParameter("type") || hreq.getParameter("type").equals(""))) {
            hresp.sendRedirect(hreq.getContextPath()+"/conversion?name="+hreq.getRequestURL());
            return ;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
