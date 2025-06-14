package com.backend.filter;

import com.backend.exception.AppException;
import com.backend.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*") // 全局拦截所有请求
public class GlobalExceptionFilter implements Filter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response); // 执行目标 Servlet
        } catch (AppException e) {
            // 自定义业务异常
            resp.setStatus(e.getCode());
            resp.setContentType("application/json;charset=UTF-8");

            Result<Object> result = new Result<>(e.getCode(), e.getMessage(), null);
            resp.getWriter().write(mapper.writeValueAsString(result));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.setContentType("application/json;charset=UTF-8");

            Result<Object> result = new Result<>(500, "服务器内部错误", null);
            resp.getWriter().write(mapper.writeValueAsString(result));
        }
    }
}
