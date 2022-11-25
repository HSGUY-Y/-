package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
	static Connection conn;
	public static Connection getConnection() {
		//��mysql.properties��ȡ����
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mysql.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String user=properties.getProperty("user");
			String password=properties.getProperty("password");
			//�������
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
			return  conn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return  null;
	}
	//�ر�����
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
