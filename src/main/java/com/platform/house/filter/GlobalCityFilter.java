package com.platform.house.filter;


import com.platform.house.controller.HouseController;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-08-04 11:03
 */
// @WebFilter(filterName = "globalFilter", value = {"/house/*", "/resold/*"})
public class GlobalCityFilter implements Filter {

    private static final Logger logger = Logger.getLogger(HouseController.class);

    private static final String defaultCity = "xian";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.info("全局城市过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String queryCity;
        Object city = httpServletRequest.getAttribute("city");

        if (city == null) {
            queryCity = defaultCity;
        } else {
            queryCity = city.toString();
        }

        RequestParameterWrapper wrapper = new RequestParameterWrapper(httpServletRequest);
        wrapper.addParameter("city", queryCity);

        filterChain.doFilter(wrapper, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("全局城市过滤器销毁");
    }
}
