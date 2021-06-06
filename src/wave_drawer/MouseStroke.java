package wave_drawer;

import java.awt.Point;
import java.util.ArrayList;

public class MouseStroke {

	/** A MouseStroke stores all the points clicked on in 1 mouse stroke by the user */
	
	public ArrayList<Point> pointArray;
	
	public static ArrayList<MouseStroke> allFinalizedMouseStrokes = new ArrayList<MouseStroke>();
	
	public MouseStroke() {
		pointArray = new ArrayList<Point>();
	}
	
	public void addPoint(Point p) {
		pointArray.add(p);
	}
	
	public static ArrayList<Point> getAllFinalizedPointsDrawn() {
		ArrayList<Point> allPoints = new ArrayList<Point>();
		for(MouseStroke ms : allFinalizedMouseStrokes) {
			for(Point p : ms.pointArray) {
				allPoints.add(p);
			}
		}
		return allPoints;
	}
	
}
