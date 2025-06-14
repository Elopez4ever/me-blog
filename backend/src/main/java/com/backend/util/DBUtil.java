package com.backend.util;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class DBUtil {
    private static final HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig("/db.properties");
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            System.out.println("初始化数据库连接池失败: " + e.getMessage());
            throw new RuntimeException("初始化数据库连接池失败", e);
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            System.out.println("获取数据库连接失败: " + e.getMessage());
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }
}
