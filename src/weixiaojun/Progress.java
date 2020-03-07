package weixiaojun;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class Progress extends JFrame {
	private static final long serialVersionUID = -6162090268754717529L;
	private static JProgressBar processBar;

	Progress() {
		setTitle("正在下载");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 250, 100);

		JPanel contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		processBar = new JProgressBar();

		processBar.setStringPainted(true);

		processBar.setBackground(Color.GREEN);

		processBar.setValue(0);

		JLabel jl = new JLabel("正在下载更新");

		jl.setFont(new java.awt.Font("Dialog", 1, 25));

		contentPane.add(jl);

		contentPane.add(processBar);

		setUndecorated(true);

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
		setLocation(x, y);

		setVisible(true);

	}

	public static void SetValue(int Value) {
		processBar.setValue(Value);
	}

	public static void Shows() {
		new Progress();
	}

	public void Exit() {
		dispose();
	}

	public static void Sexit() {
		new Progress().Exit();
	}

}
