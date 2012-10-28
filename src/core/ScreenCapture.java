package core;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class ScreenCapture {
	Robot robot;
	private Point finalPoint;

	public ScreenCapture() {
	}

	public BufferedImage takeRectangleScreen(Point point1, Point point2) {

		finalPoint = new Point(0, 0);
		if (point1.x > point2.x) {
			finalPoint.x = point2.x;
		} else {
			finalPoint.x = point1.x;
		}
		if (point1.y > point2.y) {
			finalPoint.y = point2.y;
		} else {
			finalPoint.y = point1.y;
		}
		try {
			robot = new Robot();
			Rectangle captureArea = new Rectangle(finalPoint.x, finalPoint.y,
					Math.abs(point2.x - point1.x),
					Math.abs(point2.y - point1.y));

			BufferedImage bufferedImage = robot
					.createScreenCapture(captureArea);

			return bufferedImage;

		} catch (Exception e) {
			System.out.println("ERROR CAPTURE");
			System.out.println(e.toString());
			return null;
		}
		// return null;
	}
}
