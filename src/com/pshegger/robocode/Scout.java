package com.pshegger.robocode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;

public class Scout extends Robot {
	private HashMap<String, RobotInfo> robots;
	
	public Scout() {
		robots = new HashMap<>();
	}
	
	private void gotoXY(double x, double y) {
		double dx = x - getX();
		double dy = y - getY();
		
		double turnDegrees = (Math.toDegrees(Math.atan2(dx, dy)) - getHeading()) % 360;
		
		turnRight(turnDegrees);
		ahead(Math.sqrt(dx*dx+dy*dy));
	}
	
	@Override
	public void run() {
		setColors(Color.BLACK, Color.GREEN, Color.DARK_GRAY, Color.CYAN, Color.RED);
		gotoXY(getBattleFieldWidth()/2, getBattleFieldHeight()/2);
		while (true) {
			turnRadarRight(Double.POSITIVE_INFINITY);
		}
	}

	@Override
	public void onPaint(Graphics2D g) {
		for (String key : robots.keySet()) {
			RobotInfo robot = robots.get(key);
			g.setColor(new Color(0xff, 0x00, 0x00, 0x80));
			g.fillRect(Math.round((float)robot.getPositionX())-20, Math.round((float)robot.getPositionY())-20, 40, 40);
		}
	}

	@Override
	public void onRobotDeath(RobotDeathEvent event) {
		robots.remove(event.getName());
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if (robots.containsKey(event.getName()))
			robots.remove(event.getName());
		robots.put(event.getName(), new RobotInfo(event));
	}
	
	private class RobotInfo {
		private double positionX, positionY;
		private double heading;
		private String name;
		
		public double getPositionX() {
			return positionX;
		}
		
		public double getPositionY() {
			return positionY;
		}
		
		public RobotInfo(ScannedRobotEvent info) {
			double absBearing = getHeading() + info.getBearing();
			positionX = getX() + info.getDistance() * Math.sin(Math.toRadians(absBearing));
			positionY = getY() + info.getDistance() * Math.cos(Math.toRadians(absBearing));
			name = info.getName();
			heading = info.getHeading();
		}

		@Override
		public String toString() {
			return String.format("Enemy (%s) @ (%.2f,%.2f), heading: %.2f°", name, positionX, positionY, heading);
		}
	}
}
