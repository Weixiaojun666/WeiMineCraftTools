package install;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import weixiaojun.Progress;
import weixiaojun.Tools;

public class Download {
	public static void update() {
		try {
			File dirfile = File.createTempFile("WeiMineCraftToolUpdater", ".json");
			URL httpurl = new URL(Tools.ClientjsonObject.getString("url"));
			if (!dirfile.exists()) {
				dirfile.mkdirs();
			}
			FileUtils.copyURLToFile(httpurl, dirfile);
			Tools.ServerjsonObject(dirfile);

			if (Tools.ServerjsonObject.getDouble("version") > Tools.version) {
				JLabel j0 = new JLabel("已发现新版本:" + Tools.ServerjsonObject.getDouble("version") + "更新内容:"
						+ Tools.ServerjsonObject.getString("log"));
				j0.setFont(new java.awt.Font("Dialog", 1, 15));
				int choose = JOptionPane.showConfirmDialog(null, j0, "已发现更新！是否更新?", JOptionPane.YES_NO_OPTION);

				if (choose == JOptionPane.YES_OPTION) {
					Downloads(Tools.ServerjsonObject.getString("url"));
				}
			} else {
				JOptionPane.showMessageDialog(null, "当前已为最新版,无需更新!", "当前已为最新版,无需更新!", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "检查更新失败!", "检查更新失败!", JOptionPane.ERROR_MESSAGE);
		}
	}

	Download(String url) {
		Progress.Shows();
		new Thread() {
			File file;

			public void run() {
				InputStream inputStream = null;
				RandomAccessFile randomAccessFile = null;

				try {
					HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setConnectTimeout(10 * 1000);
					file = File.createTempFile("WeiMinecraftToolsData", "." + url.substring(url.lastIndexOf(".") + 1));

					int responseCode = urlConnection.getResponseCode();
					if (responseCode >= 200 && responseCode < 300) {
						inputStream = urlConnection.getInputStream();
						int len = 0;
						byte[] data = new byte[4096];
						int progres = 0;
						int maxProgres = urlConnection.getContentLength();
						randomAccessFile = new RandomAccessFile(file, "rwd");
						randomAccessFile.setLength(maxProgres);
						int unit = maxProgres / 100;
						int unitProgress = 0;
						while (-1 != (len = inputStream.read(data))) {
							randomAccessFile.write(data, 0, len);
							progres += len;
							int temp = progres / unit;
							if (temp >= 1 && temp > unitProgress) {
								unitProgress = temp;
								Progress.SetValue(unitProgress);
							}
						}
						inputStream.close();

						Progress.Sexit();

						File directory = new File(".");
						System.out.println(file);
						System.out.println(directory.getCanonicalPath());

						JOptionPane.getRootFrame().dispose();
						JOptionPane.showMessageDialog(null, "更新完成!", "提示", 1);
						file.delete();

					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "更新失败!", "错误 ", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

			}

		}.start();
	}

	public static void Downloads(String url) {
		new Download(url);
	}
}
