package com.backend.controller;

import com.backend.dto.UserDTO;
import com.backend.entity.Result;
import com.backend.entity.User;
import com.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取请求体 JSON
        StringBuilder requestBody = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        // 读取请求体内容
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(
                    objectMapper.writeValueAsString(
                            new Result<>(HttpServletResponse.SC_BAD_REQUEST, "请求体解析失败", null))
            );
            return;
        }

        // 解析 JSON
        User inputUser;
        try {
            inputUser = objectMapper.readValue(requestBody.toString(), User.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(
                    objectMapper.writeValueAsString(
                            new Result<>(HttpServletResponse.SC_BAD_REQUEST, "请求体格式错误", null))
            );
            return;
        }

        // 登陆验证逻辑
        User user = userService.login(inputUser);
        if (user != null) {
            // 登录成功，返回用户信息
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(
                    objectMapper.writeValueAsString(
                            new Result<>(HttpServletResponse.SC_OK, "登录成功", new UserDTO(user)))
            );
        } else {
            // 登录失败，返回错误信息
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(
                    objectMapper.writeValueAsString(
                            new Result<>(HttpServletResponse.SC_UNAUTHORIZED, "用户名或密码错误", null))
            );
        }
    }
}
