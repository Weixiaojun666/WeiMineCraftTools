package weixiaojun;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class Setting extends JFrame {

	private static final long serialVersionUID = 1L;

	public Setting() {
		setTitle("Setting");
		setSize(450, 240);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container cp = getContentPane();
		getContentPane().setLayout(new FlowLayout());
		this.setLayout(new GridLayout(6, 3, 10, 10));
		final JLabel j0 = new JLabel("设置");
		final JLabel j1 = new JLabel("当前程序版本" + Tools.Weiversion);
		final JLabel j2 = new JLabel("当前游戏版本" + Tools.version + "(" + Tools.edition + ")");
		final JLabel j3 = new JLabel("下载线路 ");
		final JLabel j4 = new JLabel("获取版本");
		final JLabel j6 = new JLabel("BetterFps");
		final JLabel j7 = new JLabel("核电模拟器");
		ButtonGroup g1 = new ButtonGroup();
		ButtonGroup g2 = new ButtonGroup();
		ButtonGroup g3 = new ButtonGroup();
		ButtonGroup g4 = new ButtonGroup();
		final JRadioButton s1;
		final JRadioButton s2;
		final JRadioButton s3;
		final JRadioButton s4;
		final JRadioButton s5;
		final JRadioButton s6;
		final JRadioButton s7;
		final JRadioButton s8;
		JButton jb1 = new JButton("保存并返回");
		final JLabel j8 = new JLabel("");
		JButton jb2 = new JButton("关于");

		if (Tools.domestic) {
			s1 = new JRadioButton("国内", true);
			s2 = new JRadioButton("海外", false);
		} else {
			s1 = new JRadioButton("国内", false);
			s2 = new JRadioButton("海外", true);
		}
		g1.add(s1);
		g1.add(s2);

		if (Tools.edition.equals("1.7.10")) {
			s3 = new JRadioButton("1.7.10", true);
			s4 = new JRadioButton("1.12.2", false);
		} else {
			s3 = new JRadioButton("1.7.10", false);
			s4 = new JRadioButton("1.12.2", true);
		}

		g2.add(s3);
		g2.add(s4);

		if (Tools.BetterFps) {
			s5 = new JRadioButton("显示", true);
			s6 = new JRadioButton("隐藏", false);
		} else {
			s5 = new JRadioButton("显示", false);
			s6 = new JRadioButton("隐藏", true);
		}

		g3.add(s5);
		g3.add(s6);

		if (Tools.Ic2ExpReactorPlanner) {
			s7 = new JRadioButton("显示", true);
			s8 = new JRadioButton("隐藏", false);
		} else {
			s7 = new JRadioButton("显示", false);
			s8 = new JRadioButton("隐藏", true);
		}

		g4.add(s7);
		g4.add(s8);

		cp.add(j0);
		cp.add(j1);
		cp.add(j2);
		cp.add(j3);
		cp.add(s1);
		cp.add(s2);
		cp.add(j4);
		cp.add(s3);
		cp.add(s4);
		cp.add(j6);
		cp.add(s5);
		cp.add(s6);
		cp.add(j7);
		cp.add(s7);
		cp.add(s8);
		cp.add(jb1);
		cp.add(j8);
		cp.add(jb2);

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
		setLocation(x, y);
		setVisible(true);

		jb1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					JSONObject jsonObject = new JSONObject(FileUtils.readFileToString(Tools.JSONFile, "UTF-8"));
					if (!Tools.domestic.equals(s1.isSelected())) {
						jsonObject.put("domestic", s1.isSelected());
					}

					if (!Tools.BetterFps.equals(s5.isSelected())) {
						jsonObject.put("BetterFps", s5.isSelected());
					}

					if (!Tools.Ic2ExpReactorPlanner.equals(s7.isSelected())) {
						jsonObject.put("Ic2ExpReactorPlanner", s7.isSelected());
					}

					String tmp;
					if (s3.isSelected()) {
						tmp = "1.7.10";
					} else {
						tmp = "1.12.2";
					}
					if (!Tools.edition.equals(tmp)) {
						jsonObject.put("edition", tmp);
					}
					if (jsonObject != null) {
						FileUtils.writeStringToFile(Tools.JSONFile, jsonObject.toString(), "UTF-8");
					}

				} catch (IOException e) {

					e.printStackTrace();
				}

				Tools.main(null);
				dispose();

			}

		});
		jb2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					URI uri = java.net.URI.create("https://github.com/Weixiaojun666/WeiMineCraftTools");
					Desktop dp = java.awt.Desktop.getDesktop();
					if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
						dp.browse(uri);
					}
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}

			}

		});

	}

}
