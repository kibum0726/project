package project;

import java.sql.*;
import javax.swing.*;
import java.awt.*;

/*
 * 데이터 베이스 생성 용 
 */
public class DbMain extends JFrame {
	Connection con;
	Statement stmt;
	int i = 0;
	String url = "jdbc:mysql://localhost/?characterEncoding=UTF-8&serverTimezone=UTC";
	String user = "root";
	String passwd = "12345";
	String menu[] = { "insert into menu values('affogato',6300);", "insert into menu values('americano',3600);",
			"insert into menu values('caffeLatte',4100);", "insert into menu values('caffeMocha',4600);",
			"insert into menu values('cappuccino',4100);", "insert into menu values('espresso',4600);",
			"insert into menu values('macchiato',5100);" };

	public DbMain() {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			con = DriverManager.getConnection(url, user, passwd);
			System.out.println("DB연결 성공");
			stmt = con.createStatement();
			System.out.println("Statement객체 생성 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void createDb() throws SQLException {
		try {
			stmt.executeUpdate("create database infor;");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	void createTable() {
		try {
			stmt.execute("use infor;");
			stmt.executeUpdate(
					"create table custmoer(id varchar(20) primary key,pwd varchar(20),phone varchar(20),membership boolean);");
			stmt.executeUpdate("create table menu(menuName varchar(30) primary key,price integer);");
			System.out.println("테이블 생성 성공");
		} catch (Exception e) {
			System.out.println("테이블 있음");
		}

	}

	void insertStartValue() {

		try {
			for (String menu : menu)
				stmt.executeUpdate(menu);
			System.out.println("초기 값 입력 성공");
		} catch (Exception e) {
			System.out.println("이미 있거나 실패");
		}

	}

	public static void main(String[] args) throws SQLException {
		DbMain dbMain = new DbMain();
		dbMain.createDb();
		dbMain.createTable();
		dbMain.insertStartValue();
		new LoginPage();

	}
}
