package example;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class WikiExample extends Robot {
	private final String ROBOT_NAME = "WikiExample";
	private final int ROBOT_VERSION = 2;
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		double distance = event.getDistance();
		if (distance < 300)
			fire(2);
		else if (distance < 100)
			fire(3);
		else
			fire(1);
	}

	@Override
	public void run() {
		System.out.printf("%s v%d", ROBOT_NAME, ROBOT_VERSION);
		while (true) {
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunLeft(360);
		}
	}
	
}
