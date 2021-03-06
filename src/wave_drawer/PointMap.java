package wave_drawer;

import java.util.ArrayList;
import java.util.HashMap;

public class PointMap extends HashMap<Integer, Integer> {

	// all finalized points
	public static PointMap allFinalizedPoints = new PointMap();

	/** This class stores a series of x and y values representing points */ 
	
	// adds each point in toAdd to this pointMap if this pointMap doesn't have a point in the same spot
		public void addPointMap(HashMap<Integer, Integer> toAdd) {
			ArrayList<Integer> xValsToRemove = new ArrayList<Integer>(); // list of x values to remove from toAdd b/c they already exist in mainMap
			for(Integer x : toAdd.keySet()) {
				if(!this.containsKey(x)) {
					this.put(x, toAdd.get(x));
				} else {
					xValsToRemove.add(x);
				}
			}
			for(Integer x : xValsToRemove) {
				toAdd.remove(x);
			}
		}
	
}
