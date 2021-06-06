package wave_drawer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class MouseStroke {

	/** A MouseStroke stores all the points clicked on in 1 mouse stroke by the user */
	
	// hash map storing x and y values of all points in the mouse stroke
	public HashMap<Integer, Integer> pointMap;
	
	// hash map storing x and y values of all finalized points
	public static HashMap<Integer, Integer> allFinalizedPoints = new HashMap<Integer, Integer>();
	
	public static ArrayList<MouseStroke> allFinalizedMouseStrokes = new ArrayList<MouseStroke>();
	
	public MouseStroke() {
		pointMap = new HashMap<Integer, Integer>();
	}
	
	public void addPoint(Point p) {
		pointMap.put(p.x, p.y);
	}
	
	// finalizes a mouse stroke, adding each of its points to allFinalizedPoints if there is not already a point at the same x value
	public static void finalizeMouseStroke(MouseStroke ms) {
		addPointMaps(allFinalizedPoints, ms.pointMap);
		MouseStroke.allFinalizedMouseStrokes.add(ms);
	}
	
	// adds each point in toAdd to mainMap if mainMap doesn't have a point in the same spot
	public static void addPointMaps(HashMap<Integer, Integer> mainMap, HashMap<Integer, Integer> toAdd) {
		ArrayList<Integer> xValsToRemove = new ArrayList<Integer>(); // list of x values to remove from toAdd b/c they already exist in mainMap
		for(Integer x : toAdd.keySet()) {
			if(!mainMap.containsKey(x)) {
				mainMap.put(x, toAdd.get(x));
			} else {
				xValsToRemove.add(x);
			}
		}
		for(Integer x : xValsToRemove) {
			toAdd.remove(x);
		}
	}
	
}
