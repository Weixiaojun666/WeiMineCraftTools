package install;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URI;
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
			URL httpurl = new URL(Tools.url);
			if (!dirfile.exists()) {
				dirfile.mkdirs();
			}
			FileUtils.copyURLToFile(httpurl, dirfile);
			Tools.ServerjsonObject(dirfile);
			int choose;
			if (Tools.ServerjsonObject.getDouble("replace") > Tools.Weiversion) {
				choose = JOptionPane.showConfirmDialog(null, "�ѷ��ֵ�ǰ�����и��£�����ȷ�����ֶ����ز����� ��֮���Դ˴θ��¼������¿ͻ���", "�ѷ��ָ��£��Ƿ����?",
						JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.YES_OPTION) {
					URI uri = java.net.URI.create(Tools.ServerjsonObject.getString("reurl"));
					Desktop dp = java.awt.Desktop.getDesktop();
					if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
						dp.browse(uri);
						System.exit(0);
					}
				}
			}
			if (Tools.ServerjsonObject.getDouble("version") > Tools.version) {
				JLabel j0 = new JLabel("�ѷ����°汾:" + Tools.ServerjsonObject.getDouble("version") + "��������:"
						+ Tools.ServerjsonObject.getString("log"));
				j0.setFont(new java.awt.Font("Dialog", 1, 15));
				choose = JOptionPane.showConfirmDialog(null, j0, "�ѷ��ָ��£��Ƿ����?", JOptionPane.YES_NO_OPTION);

				if (choose == JOptionPane.YES_OPTION) {
					if (Tools.ServerjsonObject.getBoolean("warning")) {
						choose = JOptionPane.showConfirmDialog(null, "��ǰ���¿��ܻ��𺦴浵���Ƿ������", "�ѷ��ָ��£��Ƿ����?",
								JOptionPane.YES_NO_OPTION);
					}
					if (choose == JOptionPane.YES_OPTION) {
						String Downurl;
						if (Tools.edition.equals("1.7.10")) {
							Downurl = Tools.ServerjsonObject.getString("1.7.10url");
						} else {
							Downurl = Tools.ServerjsonObject.getString("1.12.2url");
						}
						Downloads(Downurl);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "��ǰ��Ϊ���°�,�������!", "��ǰ��Ϊ���°�,�������!", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "������ʧ��!", "������ʧ��!", JOptionPane.ERROR_MESSAGE);
		}
	}

	Download(String url) {
		Progress.Shows();
		new Thread() {
			File file;

			public void run() {
				InputStream inputStream = null;
				RandomAccessFile randomAccessFile = null;
				File directory = new File(".");

				try {
					HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setConnectTimeout(10 * 1000);
					file = new File(directory.getCanonicalPath() + "\\modpack.zip");
					if (file.exists()) {
						file.delete();
					}
					// file = File.createTempFile("WeiMinecraftToolsData", "." +
					// url.substring(url.lastIndexOf(".") + 1));

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

						new Install();

						JOptionPane.getRootFrame().dispose();
						JOptionPane.showMessageDialog(null, "�������!", "��ʾ", 1);
						file.delete();

					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "����ʧ��!", "���� ", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

			}

		}.start();
	}

	public static void Downloads(String url) {
		new Download(url);
	}
}
