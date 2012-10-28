package core;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SystemInfo {
	public float getJavaVersion() {
		String javaVersion = System.getProperty("java.version");

		int pointPosition = javaVersion.indexOf(".");
		if (pointPosition > 0) {
			if (javaVersion.length() >= pointPosition + 2) {
				String version = javaVersion.substring(0, pointPosition + 2);
				float javaVersionParsed = Float.parseFloat(version);
				return javaVersionParsed;
			}
		}
		return 0;
	}

	public boolean isMac() {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			return true;
		} else {
			return false;
		}
	}

	public int getScreenHeight() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return (int) screenSize.getHeight();
	}

	public int getScreenWidth() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return (int) screenSize.getWidth();
	}

	public Dimension getScreenSize() {
		return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
	}
}
