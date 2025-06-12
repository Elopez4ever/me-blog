package com.backend.controller;

import com.backend.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置 CORS 响应头，允许跨域请求
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS"); // 允许的 HTTP 方法
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With"); // 允许的请求头部
        resp.setHeader("Access-Control-Allow-Credentials", "true"); // 是否允许发送 cookies

        resp.setStatus(HttpServletResponse.SC_OK); // 返回 200 状态码
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        // 设置 CORS 响应头，允许跨域请求
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS"); // 允许的 HTTP 方法
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With"); // 允许的请求头部
        resp.setHeader("Access-Control-Allow-Credentials", "true"); // 是否允许发送 cookies


        // 获取请求体数据
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid request body\"}");
            return;
        }

        // 解析请求体数据
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(requestBody.toString(), User.class);

            // 打印输出解析后的数据
            System.out.println("Username: " + user.getUsername());
            System.out.println("Password: " + user.getPassword());
            // 返回响应
            resp.getWriter().write("{\"message\": \"Login successful\"}");
        } catch (Exception e) {
            resp.getWriter().write("{\"error\": \"Failed to parse request body\"}");
        }
    }
}
