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
			System.out.println("ERROR");
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
