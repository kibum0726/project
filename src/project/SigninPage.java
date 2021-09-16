package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.Border;

public class SigninPage extends JFrame implements ActionListener, MouseListener {
	JPanel panel[] = new JPanel[6];
	JLabel jLabel[] = new JLabel[5];
	JTextField jTextField[] = new JTextField[4];
	JButton jButton[] = new JButton[2];
	DbMain dbMain = new DbMain();
	int mebershipFlag = 0;
	JRadioButton select[] = new JRadioButton[2];
	Font title = new Font("맑은 고딕", Font.BOLD, 20);
	Font font = new Font("맑은 고딕", Font.BOLD, 12);
	JOptionPane popup = new JOptionPane();
	@Override
	public void actionPerformed(ActionEvent e) {
		Component component = (Component) e.getSource();
		if (component instanceof JButton) {
			if (component == jButton[0]) {
				String id = jTextField[0].getText();
				String pwd = jTextField[1].getText();
				String phone = jTextField[2].getText();
				// id 중복이면 팝업 띄우기
				try {
					dbMain.stmt.execute("use infor;");

					String query = "select id from custmoer where id = '" + id + "'";
					ResultSet rs;
					rs = dbMain.stmt.executeQuery(query);
					if (rs.next()) {
						System.out.println("이미 있는 아이디 입니다");
					} else {
						try {
							dbMain.stmt.execute("use infor;");
							if (mebershipFlag == 0) {
								dbMain.stmt.executeUpdate(
										"insert into custmoer values(" + id + "," + pwd + "," + phone + ",false);");
							} else {
								dbMain.stmt.executeUpdate(
										"insert into custmoer values(" + id + "," + pwd + "," + phone + ",true);");
							}

							System.out.println("성공");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							System.out.println("실패");
						}
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}

			if (component == jButton[1]) {
				jTextField[0].setText("");
				jTextField[1].setText("");
				jTextField[2].setText("");

			}
		}
	}

	WindowAdapter adapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	};

	public SigninPage() {

		super("회원가입 페이지");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		panel[0] = new JPanel(new GridLayout(1, 2));// 아이디
		panel[1] = new JPanel(new GridLayout(1, 2));// 비번
		panel[2] = new JPanel(new GridLayout(1, 2));// 전번
		panel[3] = new JPanel(new GridLayout(1, 2)); // 버튼
		panel[4] = new JPanel();
		panel[5] = new JPanel();
		JPanel head = new JPanel();
		JLabel label = new JLabel("회원 가입");
		label.setFont(title);
		head.add(label);

		jLabel[0] = new JLabel("아이디");
		jTextField[0] = new JTextField(20);

		jLabel[1] = new JLabel("비밀번호");
		jTextField[1] = new JPasswordField(20);

		jLabel[2] = new JLabel("전화번호");
		jTextField[2] = new JTextField(20);

		jLabel[3] = new JLabel("멤버쉽 가입 여부");
		ButtonGroup group = new ButtonGroup(); // 사이즈 선택

		select[0] = new JRadioButton("Y");
		select[1] = new JRadioButton("N");
		for (int i = 0; i < select.length; i++) {
			select[i].addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (select[0].isSelected())
						mebershipFlag = 1;
					else if (select[1].isSelected())
						mebershipFlag = 0;
				}
			});
		}
		select[1].setSelected(true);

		for (int i = 0; i < select.length; i++)
			group.add(select[i]);
		panel[3].add(jLabel[3]);
		JPanel groupPanel = new JPanel();
		groupPanel.add(select[0]);
		groupPanel.add(select[1]);

		panel[3].add(groupPanel);

		jLabel[4] = new JLabel("멤버쉽이란? <click");
		jLabel[4].addMouseListener(this);
		panel[4].add(jLabel[4]);

		jButton[0] = new JButton("확인");
		jButton[1] = new JButton("리셋");
		jButton[0].addActionListener(this);
		jButton[1].addActionListener(this);

		for (int i = 0; i < 3; i++) {
			panel[i].add(jLabel[i]);
			panel[i].add(jTextField[i]);
			jLabel[i].setFont(font);
		}

		panel[5].add(jButton[0]);
		panel[5].add(jButton[1]);
		panel[5].setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		add(head);
		for (JPanel panel : panel) {
			add(panel);
			panel.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2));
		}
		setSize(300, 300);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		popup.showMessageDialog(null, "멤버쉽은 금액을 5% 할인합니다\n 10원 단위는 반올림 됩니다.");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	 
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	 
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
