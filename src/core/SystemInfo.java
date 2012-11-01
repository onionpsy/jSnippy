/*
 * Copyright 2012 psych0pat
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
