package install;

import java.io.File;
import java.io.IOException;

import weixiaojun.Tools;

public class Install {
	public Install() {
		File directory = new File(".");
		try {
			File fileold = new File(directory.getCanonicalPath() + "\\.minecraft\\version.old\\V" + Tools.version);

			if (!fileold.exists() && !fileold.isDirectory()) {
				fileold.mkdirs();
			} else {
				Boolean mark = false;
				File fileolds;
				for (int i = 1; mark; i++) {
					fileolds = new File(fileold + "-" + i);
					if (!fileolds.exists() && !fileolds.isDirectory()) {
						fileold = fileolds;
						fileold.mkdirs();
						mark = true;
					}
				}
			}
			File Tmp = new File(directory.getCanonicalPath() + "\\hmcl.json");
			if (Tmp.exists()) {
				moveFile(Tmp, new File(fileold + "\\hmcl.jsom"));
			}
			Tmp = new File(directory.getCanonicalPath() + "\\.minecraft\\version");
			//moveDirectory(Tmp, new File(fileold + "\\version"));
			//moveFile(Data, new File(directory.getCanonicalPath() + "//modpack.zip"));

			try {
				Runtime.getRuntime().exec("java -jar " + Tools.ClientjsonObject.getString("Launcher"));
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moveFile(File srcFile, File destDir) {

		if (!srcFile.exists() || !srcFile.isFile())

			if (!destDir.exists())
				destDir.mkdirs();

		srcFile.renameTo(new File(destDir + File.separator + srcFile.getName()));
	}

	public void moveDirectory(File srcDir, File destDir) {

		if (!srcDir.exists() || !srcDir.isDirectory())

			if (!destDir.exists())
				destDir.mkdirs();

		File[] sourceFiles = srcDir.listFiles();
		for (File sourceFile : sourceFiles) {
			if (sourceFile.isFile())
				moveFile(sourceFile, destDir);
			else if (sourceFile.isDirectory())
				moveDirectory(sourceFile, new File(destDir + File.separator + sourceFile.getName()));
			else
				;
		}
		srcDir.delete();
	}
}
