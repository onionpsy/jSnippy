package main;

import gui.MainWindow;

import javax.swing.SwingUtilities;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainWindow mainWindow = new MainWindow();
				mainWindow.setVisible(true);
			}
		});
	}
}
