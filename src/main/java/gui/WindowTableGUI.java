package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import TableAdapter.UserAdapter;
import domain.Registered;

public class WindowTableGUI extends JFrame{
	private JTable table;
	private UserAdapter ua;
	private Registered u;
	public WindowTableGUI(Registered user) {
		super(user.getUsername());
		setBounds(10,10,1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.u=user;

		ua= new UserAdapter(u);

		table = new JTable(ua);
		table.setBounds(85, 49, 243, 121);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(600,280));
		JPanel panel = new JPanel();
		panel.add(scrollPane);
		add(panel,BorderLayout.CENTER);
	}
}
