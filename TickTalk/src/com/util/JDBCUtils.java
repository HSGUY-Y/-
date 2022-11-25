package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
	static Connection conn;
	public static Connection getConnection() {
		//从mysql.properties读取数据
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mysql.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String user=properties.getProperty("user");
			String password=properties.getProperty("password");
			//获得连接
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
			return  conn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return  null;
	}
	//关闭连接
	public void closeAll() {
		 if(conn!=null) {
			 try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
	}
