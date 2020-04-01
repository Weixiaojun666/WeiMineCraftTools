package weixiaojun;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Ic2ExpReactorPlanner.ReactorPlannerFrame;
import betterfps.BetterFps;
import install.Download;

public class GUI extends JFrame {

	private static final long serialVersionUID = 4559646778288230139L;

	public GUI() {
		setTitle("WeiUpdater");
		setSize(720, 480);
		setResizable(false);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		getContentPane().setLayout(new FlowLayout());
		JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/logo.png")));

		JLabel wallpaper = new JLabel(new ImageIcon(getClass().getResource("/wallpaper.png")));
		wallpaper.setBounds(0, 0, this.getWidth(), this.getHeight());
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(wallpaper, new Integer(Integer.MIN_VALUE));

		final JLabel jl = new JLabel("Wei Technology Era Updaer   ");
		final JLabel j2 = new JLabel("当前客户端版本:");
		final JLabel j3 = new JLabel(Double.toString(Tools.version));

		jl.setFont(new java.awt.Font("Dialog", 1, 30));
		j2.setFont(new java.awt.Font("Dialog", 1, 20));
		j2.setFont(new java.awt.Font("Dialog", 1, 15));

		JButton jb1 = new JButton("启动游戏");
		JButton jb2 = new JButton("检查更新");
		JButton jb3 = new JButton("BetterFps");
		JButton jb4 = new JButton("核电模拟器");
		JButton jb5 = new JButton("关于");

		cp.add(logo);
		cp.add(jl);
		cp.add(j2);
		cp.add(j3);
		cp.add(jb1);
		cp.add(jb2);

		if (Tools.ClientjsonObject.getBoolean("BetterFps"))
			cp.add(jb3);
		if (Tools.ClientjsonObject.getBoolean("Ic2ExpReactorPlanner"))
			cp.add(jb4);
		
		cp.add(jb5);
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
		setLocation(x, y);
		setVisible(true);

		jb1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					Runtime.getRuntime().exec("java -jar " + Tools.ClientjsonObject.getString("Launcher"));
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}

			}

		});

		jb2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				jb1.setEnabled(false);
				jb2.setEnabled(false);
				Download.update();
				jb1.setEnabled(true);
				jb2.setEnabled(true);

			}

		});

		jb3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				BetterFps tester = new BetterFps();
				tester.setVisible(true);

			}

		});
		jb4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				String tmp[]=null;
				ReactorPlannerFrame.main(tmp);

			}

		});
		jb5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
			    String url = "https://github.com/Weixiaojun666/WeiMineCraftTools"; 
			    java.net.URI uri = java.net.URI.create(url);
			    java.awt.Desktop dp = java.awt.Desktop.getDesktop(); 
			    if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) { 
			    	dp.browse(uri);
			    }
			    }catch (java.io.IOException e) {  
			        e.printStackTrace(); 
			       } 


			}

		});

	}

}
