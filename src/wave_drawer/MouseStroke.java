package wave_drawer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class MouseStroke {

	/** A MouseStroke stores all the points clicked on in 1 mouse stroke by the user */
	
	// all points in the mouse stroke
	public PointMap pointMap;
	
	public static ArrayList<MouseStroke> allFinalizedMouseStrokes = new ArrayList<MouseStroke>();
	
	public MouseStroke() {
		pointMap = new PointMap();
	}
	
	public void addPoint(Point p) {
		pointMap.put(p.x, p.y);
	}
	
	// finalizes a mouse stroke, adding each of its points to allFinalizedPoints if there is not already a point at the same x value
	public static void finalizeMouseStroke(MouseStroke ms) {
		PointMap.allFinalizedPoints.addPointMaps(ms.pointMap);
		MouseStroke.allFinalizedMouseStrokes.add(ms);
	}
	
	
	
}
