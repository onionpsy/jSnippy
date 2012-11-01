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
	}
}
