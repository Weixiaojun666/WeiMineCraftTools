package install;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import weixiaojun.Tools;

public class Install {
	public Install() {
		File directory = new File(".");
		try {
			File tmp = new File(directory.getCanonicalPath() + "\\.minecraft\\versions.old");
			if (tmp.exists())
				FileUtils.deleteDirectory(tmp);
			File oldpath = new File(directory.getCanonicalPath() + "\\.minecraft\\versions");
			oldpath.renameTo(tmp);

			Runtime.getRuntime().exec("java -jar " + Tools.Launcher);
			System.exit(0);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
