package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import core.ScreenController;

import module.ImageToClipboard;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7188769394080889544L;
	private ScreenController screenController;
	private JPanel buttonPanel, picPanel;
	private JButton buttonSaveAs, buttonTakeScreen, buttonCopy;
	private JFileChooser fileChooser;
	private JSeparator panelSeparator;
	private Dimension defaultSize = new Dimension(350, 0); // pack()
	private BufferedImage screenShoot;
	private Integer border = 10;
	private final ClassLoader loader = MainWindow.class.getClassLoader();

	public MainWindow() {
		screenController = new ScreenController(this);
		build();
		GuiLook.setGuiLook(this);
	}

	private void build() {
		setTitle("jSnippy");
		setSize(defaultSize.width, 100);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		/*
		 * Set window Icon
		 */
		try {
			setIconImage(ImageIO.read(loader.getResource("icon_small.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new FlowLayout(0, 5, 1));
		buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		add(buttonPanel);

		panelSeparator = new JSeparator();
		panelSeparator.setBackground(Color.DARK_GRAY);
		panelSeparator.setForeground(Color.GRAY);
		panelSeparator.setPreferredSize(new Dimension(getWidth(), 3));
		add(panelSeparator);

		picPanel = new JPanel();
		picPanel.setBackground(Color.WHITE);
		picPanel.setPreferredSize(new Dimension(getWidth(), 50));
		add(picPanel);

		screenController.addMouseDragListener();

		/*
		 * Take screen button
		 */
		buttonTakeScreen = new JButton();
		buttonTakeScreen
				.setIcon(new ImageIcon(loader.getResource("camera.png")));
		buttonTakeScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setState(Frame.ICONIFIED);
				screenController.buildFrame();
				screenController.setVisible(true);
			}
		});
		buttonPanel.add(buttonTakeScreen);

		/*
		 * Copy button
		 */
		buttonCopy = new JButton();
		buttonCopy.setIcon(new ImageIcon(loader.getResource("copy.png")));
		buttonCopy.setToolTipText("Copy image to your Clipboard");
		buttonCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (screenShoot != null) {
					new ImageToClipboard(screenShoot);
					JOptionPane.showMessageDialog(null,
							"Image copied to your clipbord", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"You have no Screenshoot", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		buttonPanel.add(buttonCopy);

		/*
		 * Save As button
		 */
		buttonSaveAs = new JButton();
		buttonSaveAs.setIcon(new ImageIcon(loader.getResource("save.png")));
		buttonSaveAs.setToolTipText("Save your screenshoot");
		buttonSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (screenShoot != null) {

					fileChooser = new JFileChooser(".");

					int returnVal = fileChooser.showSaveDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						String path = file.getPath();
						path = (path.endsWith(".png")) ? path : path
								.concat(".png");
						File file2 = new File(path);
						try {
							ImageIO.write(screenShoot, "PNG", file2);
						} catch (IOException e1) {
							System.out.println(e1.toString());
						}
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Screenshoot not taken", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonPanel.add(buttonSaveAs);

		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		/*
		 * About button
		 */
		/*
		 * buttonAbout = new JButton(); buttonAbout.setIcon(new
		 * ImageIcon(getClass().getResource( resPath + "information.png")));
		 * buttonAbout.setToolTipText("Information about this program");
		 * buttonAbout.setPreferredSize(new Dimension(20, 20));
		 * buttonAbout.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) {
		 * System.out.println("about"); } }); buttonPanel.add(buttonAbout);
		 */
		update(getGraphics());
	}

	public void displayScreen(BufferedImage screen) {
		this.screenShoot = screen;

		picPanel.removeAll();
		int width = 0;
		int height = 0;
		Boolean change = false;

		if (screen.getWidth() > defaultSize.width) {
			width = screen.getWidth() + border;
			change = true;
		} else {
			width = defaultSize.width;
		}

		if (screen.getHeight() > defaultSize.height - buttonPanel.getHeight()) {
			height = screen.getHeight() + border * 3 + buttonPanel.getHeight()
					+ panelSeparator.getHeight();
			change = true;
		} else {
			height = defaultSize.height;
		}

		if (change == true) {
			setMinimumSize(new Dimension(width, height));
		}

		JLabel picLabel = new JLabel(new ImageIcon(screen));
		picPanel.add(picLabel);

		panelSeparator.setMaximumSize(new Dimension(getWidth(), 0));
		buttonPanel.setMaximumSize(new Dimension(getWidth(), 2));
		pack();

	}
}
