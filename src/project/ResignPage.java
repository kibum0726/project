package project;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class ResignPage extends JFrame implements ActionListener {
	DbMain dbMain = new DbMain();
	TextField textFields[] = new TextField[3];
	JPanel jPanel[] = new JPanel[5];
	JLabel jLabel[] = new JLabel[3];
	JLabel text;
	JOptionPane popup = new JOptionPane();
	JButton button;
	String delId;
	Font title = new Font("���� ���",Font.BOLD,20);
	Font font = new Font("���� ���",Font.BOLD,12);
	WindowAdapter adapter = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	};

	public ResignPage() {

		super("ȸ��Ż�� ������");
		// TODO Auto-generated constructor stub
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		for (int i = 0; i < jPanel.length; i++) {
			jPanel[i] = new JPanel();
		}
	
		JLabel headLabel = new JLabel("���� ������ �ٽ� Ȯ���մϴ�");
		headLabel.setFont(title);
		jPanel[0].add(headLabel);
		
		jPanel[1] = new JPanel(new GridLayout(1, 2));// ���̵�
		jPanel[2] = new JPanel(new GridLayout(1, 2));// ���
		jPanel[3] = new JPanel(new GridLayout(1, 2));// ���Ȯ��

		jLabel[0] = new JLabel("���̵�");
		textFields[0] = new TextField(20);

		jLabel[1] = new JLabel("��й�ȣ");
		textFields[1] = new TextField(20);
		textFields[1].setEchoChar('*');
		jLabel[2] = new JLabel("��й�ȣ Ȯ��");
		textFields[2] = new TextField(20);
		textFields[2].setEchoChar('*');
		for (int i = 1; i <=3; i++) {
			jPanel[i].add(jLabel[i-1]);
			jPanel[i].add(textFields[i-1]);
		}

		button = new JButton("Ȯ��");
		button.addActionListener(this);
		jPanel[4].add(button);
		
		for(JLabel fLabel : jLabel) {
			fLabel.setFont(font);
		}
		for (JPanel panel : jPanel) {
			panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
			add(panel);
			
		}
		setSize(300, 250);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String an[] = { "��", "�ƴϿ�" };

		if (c == button) {
			try {
				dbMain.stmt.execute("use infor;");

				String query1 = "select * from custmoer where id = '" + textFields[0].getText() + "' and pwd = '"
						+ textFields[1].getText() + "';";
				System.out.println(query1);
				ResultSet rs = dbMain.stmt.executeQuery(query1);
				if (rs.next()) {
					if (textFields[1].getText().equals(textFields[2].getText())) { // ��� ���� ���ٸ�

						int result = popup.showConfirmDialog(null, "������ ȸ�� Ż�� �Ͻðڽ��ϱ�?", "Ȯ��",
								JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {

							dbMain.stmt.execute("use infor;");
							delId = textFields[0].getText();
							String query2 = "DELETE FROM custmoer WHERE id = '" + delId + "';";
							dbMain.stmt.executeUpdate(query2);
							popup.showMessageDialog(null, "ȸ�� Ż�� �Ǿ����ϴ�");

						} // end if
						else {
							// �ƴϿ並 ������ ��
						}
					} // end if
					else {
						popup.showMessageDialog(null, "���̵�� ��й�ȣ Ȯ���� �ٽ� ���ּ���");
					}
				}
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
		}
	}
}
