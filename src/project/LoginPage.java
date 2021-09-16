package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.ByteOrder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener {
	JLabel idLabel, passwordLabel;
	JLabel imgl = new JLabel();

	ImageIcon images, resize, login;
	JTextField idField; // 아이디 입력 받을 곳
	TextField passwordField; // 비밀번호 입력 받을 곳
	String iid;
	JButton jButton[] = new JButton[3];
	JPanel panel[] = new JPanel[4];
	JPanel asd = new JPanel();
	boolean idflag = true;
	String id, pwd, getid;
	DbMain dbMain = new DbMain();
	Font title = new Font("맑은 고딕", Font.BOLD, 20);
	Font font = new Font("맑은 고딕", Font.BOLD, 12);
	JOptionPane popup = new JOptionPane();
	public LoginPage() {
		super("로그인 페이지");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
		}

		panel[1].setLayout(new GridLayout(1, 2)); // password panel
		panel[2].setLayout(new GridLayout(1, 3)); // button

		images = new ImageIcon("images/coffeIcon.png");
		Image img = images.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		resize = new ImageIcon(img);
		imgl.setIcon(resize);
		JLabel label = new JLabel("로그인 화면", JLabel.CENTER);
		label.setFont(title);
		panel[0].add(imgl);
		panel[0].add(label);
		// 아이디 입력 판넬
		idLabel = new JLabel("아 이 디");
		idLabel.setFont(font);
		idField = new JTextField(20);
		panel[1].add(idLabel);
		panel[1].add(idField);

		// 비밀번호 입력 판넬
		passwordLabel = new JLabel("비밀번호");
		passwordLabel.setFont(font);
		passwordField = new TextField(20);
		passwordField.setEchoChar('*');
		panel[2].add(passwordLabel);
		panel[2].add(passwordField);

		// 버튼 모음

		images = new ImageIcon("images/login.png");
		Image resize = images.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		ImageIcon login = new ImageIcon(resize);

		jButton[0] = new JButton("로그인", login);
		jButton[1] = new JButton("회원탈퇴");
		jButton[2] = new JButton("회원가입");
		for (JButton button : jButton)
			panel[3].add(button);

		for (JPanel jpanel : panel) {
			add(jpanel);
			jpanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		}
		getid = idField.getText();
		for (int i = 0; i < jButton.length; i++) {
			jButton[i].addActionListener(this); // 로그인
		}

		setSize(300, 240);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		if (c instanceof JButton) {
			if (c == jButton[0]) {
				if (idField.getText().equals("admin") && passwordField.getText().equals("admin")) {
					// 관리자 계정이면
					new Admin();
				} else {
					try {
						// 학번과 이름의 텍스프 필드에 값을 바탕으로
						dbMain.stmt.execute("use infor;");
						id = idField.getText();
						pwd = passwordField.getText();

						String query = "select * from custmoer where id = '" + id + "' and pwd = '" + pwd + "'";

						System.out.println(query);
						ResultSet rs;

						rs = dbMain.stmt.executeQuery(query);
						System.out.println(rs);
						if (rs.next()) { // 결과 값이 있다면
							System.out.println("로그인 성공");

							new PaymentPage(id);
						} else {
							System.out.println("feild");
							popup.showMessageDialog(null, "아이디와 비밀번호를 다시 확인해주세요");							
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println("에러");
						e1.printStackTrace();

					}

				}
			}
			if (c == jButton[1]) { // 회원 탈퇴
				new ResignPage();
			}
			if (c == jButton[2]) {

				new SigninPage();

			}
		}
	}

	String get() {
		System.out.println(id);
		return id;
	}

}
