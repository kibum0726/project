package project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

public class Admin extends JFrame {
	String head[] = { "아이디", "비밀번호", "전화번호", "멤버쉽 가입 여부" };
	Vector<Object> data = new Vector<Object>();
	JTable jTable;
	JScrollPane scrollPane = new JScrollPane();
	int count = 0;
	DbMain dbMain = new DbMain();
	List<Object> list = new ArrayList<Object>();
	JLabel title = new JLabel("사용자 목록");
	JPanel jPanel[] = new JPanel[2];
	JButton set;
	int cflag = -1;
	WindowAdapter adapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	};

	public Admin() {

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		for (int i = 0; i < jPanel.length; i++) {
			jPanel[i] = new JPanel();
		}
		try {
			dbMain.stmt.execute("use infor");
			String query = "select * from custmoer;";
			ResultSet rs;
			rs = dbMain.stmt.executeQuery(query);

			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String phone = rs.getString("phone");
				boolean ms = rs.getBoolean("membership");
				data.add(id);
				data.add(pwd);
				data.add(phone);
				data.add(ms);
				System.out.println(data);
				count++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<Object> iterator = data.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().toString());
		}
		System.out.println(count);
		Object contents[][] = new Object[count][4];
		int counted = 0;
		for (int i = 1; i <= count; i++) {
			for (int j = 1; j <= 4; j++) {
				contents[i - 1][j - 1] = data.get(counted++);
			}
		}

		setIconImage(getIconImage());
		jTable = new JTable(contents, head);
		scrollPane = new JScrollPane(jTable);
 
		scrollPane.setPreferredSize(new Dimension(400, 200));
		jPanel[0].add(title);
		jPanel[1].add(scrollPane);
		jPanel[1].setPreferredSize(new Dimension(400, 300));
		add(jPanel[0]);
		add(jPanel[1]);
		setSize(500, 400);
		setVisible(true);
		setLocationRelativeTo(null);//화면 정중앙
	}

}
