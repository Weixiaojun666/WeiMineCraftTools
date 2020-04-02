package weixiaojun;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import org.json.JSONObject;

public class Tools {
	public static JSONObject ClientjsonObject;
	public static JSONObject ServerjsonObject;
	public static double version;
	public static double Weiversion = 1;
	public static String edition;
	public static String url;
	public static String Launcher;
	public static Boolean domestic;
	public static Boolean BetterFps;
	public static Boolean Ic2ExpReactorPlanner;
	public static File JSONFile;

	public static void main(String[] args) {
		try {
			File directory = new File(".");

			JSONFile = new File(directory.getCanonicalPath() + "\\.minecraft\\versions\\WeiTechnologyera\\WeiMineCraftTools.json");

			if (!JSONFile.exists()) {
				JOptionPane.showMessageDialog(null, "未找到WeiMineCraftTools配置文件，无法正常获取客户端信息！",
						"未找到WeiMineCraftTools配置文件!", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			String client = null;

			client = FileUtils.readFileToString(JSONFile, "UTF-8");
			ClientjsonObject = new JSONObject(client);
			version = ClientjsonObject.getDouble("version");
			Launcher = ClientjsonObject.getString("Launcher");
			domestic = ClientjsonObject.getBoolean("domestic");
			edition = ClientjsonObject.getString("edition");
			BetterFps = ClientjsonObject.getBoolean("BetterFps");
			Ic2ExpReactorPlanner = ClientjsonObject.getBoolean("Ic2ExpReactorPlanner");
			if (domestic) {
				url = ClientjsonObject.getString("url1");
			} else {
				url = ClientjsonObject.getString("url2");
			}
			new GUI();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void ServerjsonObject(File file) {
		String server = null;
		try {
			server = FileUtils.readFileToString(file, "UTF-8");
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerjsonObject = new JSONObject(server);
	}

}
