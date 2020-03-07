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

	public static void main(String[] args) {

		File file = new File("WeiTools.json");
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "未找到配置文件 WeiUpdater.json!", "未找到配置文件 WeiUpdater.json!",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		String client = null;
		try {
			client = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientjsonObject = new JSONObject(client);
		version = ClientjsonObject.getDouble("version");
		new GUI();
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
