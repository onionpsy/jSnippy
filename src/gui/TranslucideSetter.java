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

package gui;

import java.awt.Window;
import java.awt.peer.ComponentPeer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TranslucideSetter {
	public void setTranslucide() {
		try {
			Class<?> awtUtilitiesClass = Class
					.forName("com.sun.awt.AWTUtilities");
			Method method = awtUtilitiesClass.getMethod("setWindowOpacity",
					Window.class, float.class);
			method.invoke(null, this, 0.5f);
		} catch (Exception exc) {
			System.out.println("ERROR Transparency not yet supported");
			exc.printStackTrace();
		}

	}

	public static void setWindowAlpha(Window w, float alpha) {
		@SuppressWarnings("deprecation")
		ComponentPeer peer = w.getPeer();
		if (peer == null) {
			return;
		}
		Class<? extends ComponentPeer> peerClass = peer.getClass();

		try {
			Class<?> nativeClass = Class.forName("apple.awt.CWindow");
			if (nativeClass.isAssignableFrom(peerClass)) {
				Method setAlpha = nativeClass
						.getMethod("setAlpha", float.class);
				setAlpha.invoke(peer, Math.max(0.0f, Math.min(alpha, 1.0f)));
			}
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
}
