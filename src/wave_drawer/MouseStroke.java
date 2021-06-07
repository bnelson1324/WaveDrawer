package wave_drawer;

import java.awt.Point;
import java.util.ArrayList;

import ui.DrawPanel;

public class MouseStroke {

	/** A MouseStroke stores all the points clicked on in 1 mouse stroke by the user */
	
	// all points in the mouse stroke
	public PointMap pointMap;
	
	public static ArrayList<MouseStroke> allFinalizedMouseStrokes = new ArrayList<MouseStroke>();
	
	public static ArrayList<MouseStroke> redoQueue = new ArrayList<MouseStroke>();
	
	public MouseStroke() {
		pointMap = new PointMap();
	}
	
	public void addPoint(Point p) {
		pointMap.put(p.x, p.y);
	}
	
	// finalizes a mouse stroke, adding each of its points to allFinalizedPoints if there is not already a point at the same x value
	public static void finalizeMouseStroke(MouseStroke ms) {
		PointMap.allFinalizedPoints.addPointMap(ms.pointMap);
		MouseStroke.allFinalizedMouseStrokes.add(ms);
	}
	
	// undos a given amount of mouseStrokes
	public static void undo(int numStrokes) {
		if(allFinalizedMouseStrokes.size() < 1 || WavePlayer.isPlaying) {
			return;
		}
		int totalStrokes = allFinalizedMouseStrokes.size();
		for(int i = 0; i < numStrokes; i++) {
			MouseStroke ms = allFinalizedMouseStrokes.get(totalStrokes - 1 - i);
			for(Integer x : ms.pointMap.keySet()) {
				PointMap.allFinalizedPoints.remove(x);
			}
			redoQueue.add(ms);
			allFinalizedMouseStrokes.remove(ms);
		}
		DrawPanel.clearAndRedraw = true;
	}
	
	public static void redo() {
		if(redoQueue.size() < 1 || WavePlayer.isPlaying) {
			return;
		}
		int i = redoQueue.size() - 1;
		finalizeMouseStroke(redoQueue.get(i));
		redoQueue.remove(i);
		DrawPanel.clearAndRedraw = true;
	}
	
}
