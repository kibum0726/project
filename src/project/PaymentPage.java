package project;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

public class PaymentPage extends JFrame implements ActionListener, ItemListener {
	JPanel jpanel[] = new JPanel[4];
	JLabel jLabel = new JLabel();
	JRadioButton size[] = new JRadioButton[3];
	LinkedList<String> menuList = new LinkedList<String>();
	Font title = new Font("맑은 고딕", Font.BOLD, 20);
	Font font = new Font("맑은 고딕", Font.BOLD, 12);
	String menu[];
	Integer[] order = new Integer[99];
	int menuPrice, addPrice, totalPrice;
	int menuIndex = 0;
	int orderCount = 0;
	DbMain dbMain = new DbMain();
	JComboBox<String> comboBox;
	JComboBox<Integer> orderBox;
	JPanel radioPanel = new JPanel();
	JLabel imgl = new JLabel();
	ImageIcon getimg, images;
	JButton jButton[] = new JButton[2];
	JOptionPane popup = new JOptionPane();
	String getid;
	WindowAdapter adapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	};

	public PaymentPage(String id) {
		// TODO Auto-generated constructor stub
		super("메뉴 페이지");
		getid = id;
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		for (int i = 0; i < jpanel.length; i++) {
			jpanel[i] = new JPanel();
		}

		jLabel.setText("MENU"); // 메뉴
		jLabel.setFont(title);
		jpanel[0].add(jLabel);

		try {
			getCoffe();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jpanel[1].setLayout(new GridLayout(1, 2));
		comboBox = new JComboBox<String>(menu); // 어떤 음료를 선택 할 것인지 고를 선텍창
		comboBox.addActionListener(this);
		comboBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		ButtonGroup group = new ButtonGroup(); // 사이즈 선택
		size[0] = new JRadioButton("S");
		size[1] = new JRadioButton("M");
		size[2] = new JRadioButton("L");
		for (int i = 0; i < size.length; i++) {
			size[i].addItemListener(this);
		}
		size[0].setSelected(true);
		for (int i = 0; i < size.length; i++)
			group.add(size[i]);

		jpanel[1].add(comboBox);
		for (int i = 0; i < order.length; i++)
			order[i] = i + 1;

		orderBox = new JComboBox<Integer>(order);
		orderBox.setSelectedIndex(0);
		orderBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		orderBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Component c = (Component) e.getSource();
				orderCount = ((JComboBox) c).getSelectedIndex();
				System.out.println("orderCount : " + orderCount);

			}
		});
		jpanel[1].add(orderBox);

		radioPanel.add(size[0]);
		radioPanel.add(size[1]);
		radioPanel.add(size[2]);

		jpanel[1].add(radioPanel);
		jpanel[2].add(imgl);
		jpanel[2].setBorder(BorderFactory.createEmptyBorder(-10, -10, -10, -10));

		jButton[0] = new JButton("결제");
		jButton[1] = new JButton("초기화");
		jButton[0].addActionListener(this);
		jButton[1].addActionListener(this);
		for (int i = 0; i < jButton.length; i++)
			jpanel[3].add(jButton[i]);

		jpanel[2].setPreferredSize(new Dimension(getWidth(), 300));

		for (int i = 0; i < jpanel.length; i++) {
			add(jpanel[i]);
			jpanel[i].setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // margin
		}

		setSize(400, 450);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// db에 있는 메뉴 이름을 가져오는 메소드
	private void getCoffe() throws SQLException {
		int i = 0;
		dbMain.stmt.execute("use infor");
		String query = "select menuName from menu";
		ResultSet rs;
		rs = dbMain.stmt.executeQuery(query);
		menuList.add(" 메뉴 ");
		while (rs.next()) {
			menuList.add(rs.getString("menuName"));
			i++;
		}
		menu = new String[i];
		for (int j = 0; j < i; j++) {
			menu[j] = menuList.get(j);
			System.out.print(menu[j] + ", ");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource(); // 콤보박스 알아내기
		if (c instanceof JComboBox) {
			if (c == comboBox) {
				// 콤보 박스에서 고른 항목과 관련된 이미지를 출력
				menuIndex = ((JComboBox) c).getSelectedIndex();
				System.out.println(menu[menuIndex]);
				images = new ImageIcon("images/" + menu[menuIndex] + ".jpg");
				Image img = images.getImage().getScaledInstance(jpanel[2].getWidth() - 100, jpanel[2].getHeight() - 50,
						Image.SCALE_SMOOTH);
				getimg = new ImageIcon(img);
				imgl.setIcon(getimg);

			}

		}
		if (c instanceof JButton) {
			if (c == jButton[0]) {
				try {
					dbMain.stmt.execute("use infor;");
					String query = "select price from menu where menuName = '"; // 메뉴 이름으로 가격 찾기
					String query2 = "select membership from custmoer where id = '" + getid + "'";
					query += menu[menuIndex] + "'";
					ResultSet rs, rs2;
					rs = dbMain.stmt.executeQuery(query);

					if (rs.next()) {
						menuPrice = rs.getInt("price");
						System.out.println("커피 금액 : " + (menuPrice + addPrice));
						System.out.println("선택된 개수 : " + order[orderCount]);
						totalPrice = menuPrice + addPrice;

					}
					rs.close();
					rs2 = dbMain.stmt.executeQuery(query2);
					if (rs2.next()) {
						if (rs2.getBoolean("membership")) {
							totalPrice = (int) (totalPrice * 0.95);
							if (totalPrice % 100 != 0) {
								if (totalPrice % 100 >= 50)
									totalPrice = (100 - (totalPrice % 100)) + totalPrice; // 10의 자리 수 제거
								else
									totalPrice = totalPrice - (totalPrice % 100); // 10의 자리 수 제거
							}
						}

					}

					popup.showMessageDialog(null, getid + " 님의 결제 금액은 " + (totalPrice * order[orderCount]) + "원 입니다.",
							"결제 안내", JOptionPane.CLOSED_OPTION);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			if (c == jButton[1]) {
				comboBox.setSelectedIndex(0);
				orderBox.setSelectedIndex(0);
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (size[0].isSelected()) {
			addPrice = 0;
		}
		if (size[1].isSelected()) {
			addPrice = 500;
		}
		if (size[2].isSelected()) {
			addPrice = 1000;
		}
	}

}
