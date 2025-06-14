package com.backend.service;

import com.backend.Exception.AppException;
import com.backend.entity.User;
import com.backend.util.BCryptUtil;
import com.backend.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {

    public User login(User inputUser) {
        String sql = "SELECT * FROM user WHERE username = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, inputUser.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");

                if (BCryptUtil.verify(inputUser.getPassword(), dbPassword)) {
                    // 登录成功，返回脱敏用户信息
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            null
                    );
                } else {
                    throw new AppException(401, "用户名或密码错误");
                }
            } else {
                throw new AppException(401, "用户名或密码错误");
            }

        } catch (AppException e) {
            throw e; // 继续抛出让全局处理
        } catch (Exception e) {
            throw new AppException(500, "数据库异常：" + e.getMessage());
        }
    }
}
