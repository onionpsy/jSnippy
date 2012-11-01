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

import gui.MainWindow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.peer.ComponentPeer;
import java.lang.reflect.Method;

import javax.swing.JDialog;
import module.ImageToClipboard;

public class ScreenController extends JDialog {
	private static final long serialVersionUID = 8004167467175170613L;
	private Point mousePressed;
	private Point mouseReleased;
	private BufferedImage imageCaptured;
	private float alphaLevel = 0.5f;
	private SystemInfo systemInfo = new SystemInfo();
	private MainWindow mainWindow;

	public ScreenController(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void buildFrame() {
		setUndecorated(true);
		setSize(systemInfo.getScreenSize());
		setPreferredSize(getMaximumSize());
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setResizable(false);
		setMaximumSize(getMaximumSize());
		toFront();
		setTranslucidity();
	}

	public void init() {
		repaint();
		dispose();
	}

	public void addMouseDragListener() {
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (mousePressed != null && mouseReleased != null) {
					setVisible(false);
					imageCaptured = new ScreenCapture().takeRectangleScreen(
							mousePressed, mouseReleased);
					if (imageCaptured != null) {
						mainWindow.displayScreen(imageCaptured);
						new ImageToClipboard(imageCaptured);
					}
				}
				dispose();
				mainWindow.setState(Frame.NORMAL);
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				mousePressed = new Point(e.getX(), e.getY());
				mouseReleased = mousePressed;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseReleased = new Point(e.getX(), e.getY());
				repaint();
			}
		});
	}

	private void setTranslucidity() {
		systemInfo.getJavaVersion();

		if (systemInfo.isMac() == true) {
			this.setVisible(true);
			setMacTranslucidity();
		} else {
			try {
				Class<?> awtUtilitiesClass = Class
						.forName("com.sun.awt.AWTUtilities");
				Method mSetWindowOpacity = awtUtilitiesClass.getMethod(
						"setWindowOpacity", Window.class, float.class);
				mSetWindowOpacity.invoke(null, this, Float.valueOf(alphaLevel));
				// setOpacity(alphaLevel);
			} catch (Exception e) {
				System.out.println("TRANSPARENCY NOT SUPPORTED");
				System.out.println(e);
			}
		}
		setVisible(true);

	}

	private void setMacTranslucidity() {
		@SuppressWarnings("deprecation")
		ComponentPeer peer = this.getPeer();
		if (peer == null) {
			return;
		}
		Class<? extends ComponentPeer> peerClass = peer.getClass();

		try {
			Class<?> nativeClass = Class.forName("apple.awt.CmainWindow");
			if (nativeClass.isAssignableFrom(peerClass)) {
				Method setAlpha = nativeClass
						.getMethod("setAlpha", float.class);
				setAlpha.invoke(peer,
						Math.max(alphaLevel, Math.min(0.1f, 1.0f)));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
		return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2),
				Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int rectangleStroke = 1;
		g.clearRect(0, 0, systemInfo.getScreenWidth(),
				systemInfo.getScreenHeight());
		if (mousePressed != null && mouseReleased != null) {
			g2.setPaint(Color.RED);
			g2.setStroke(new BasicStroke(rectangleStroke));
			Shape selectionRectangle = makeRectangle(mousePressed.x,
					mousePressed.y, mouseReleased.x, mouseReleased.y);
			g2.draw(selectionRectangle);
		}
	}
}
