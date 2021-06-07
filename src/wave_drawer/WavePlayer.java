package wave_drawer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import lib.StdAudio;

public class WavePlayer {

	/** This class manages the audio and plays back the wave */
	
	// whether or not the WavePlayer should play sound
	public static volatile boolean isPlaying = false;
	
	// for preparing wave for playing right after play button is pressed
	public static boolean readyToPlay = false;
	
	public static float frequency = 440;
	public static int volume = 50;
	
	// playback
	private static ArrayList<Integer> xValsToPlay;
	private static int xValLastIndex; // last index of x value that the player has reached (used for lerping between values)
	private static int xPeriod; // period of the wave in x distance
	private static int timesPlayed; // number of times a sample has been played
	private static int yMin, yMax; // min and max y values in all points
	
	public static void start() {
		while(true) {
			if(isPlaying) {
				if(!readyToPlay) {
					xValLastIndex = 0;
					timesPlayed = 0;
					xValsToPlay = sortPointMap(PointMap.allFinalizedPoints);
					yMin = PointMap.allFinalizedPoints.get((int) xValsToPlay.get(0));
					yMax = PointMap.allFinalizedPoints.get((int) xValsToPlay.get(0));
					findYExtremes();
					readyToPlay = true;
				}
				// speed at which the waveplayer goes through x values
				xPeriod = xValsToPlay.get(xValsToPlay.size()-1) - xValsToPlay.get(0);
				float playbackRate = xPeriod * frequency / StdAudio.SAMPLE_RATE;
				float playerXPos = xValsToPlay.get(0) + (playbackRate * timesPlayed) % xPeriod;
				
				// determine the y value of the point at playerXPos by lerping
				if(xValLastIndex >= xValsToPlay.size() - 1) {
					xValLastIndex = 0;
				}
				int xValNextIndex = xValLastIndex + 1;
				float xValLast = xValsToPlay.get(xValLastIndex);
				float yValLast = PointMap.allFinalizedPoints.get((int) xValLast);
				float xValNext = xValsToPlay.get(xValNextIndex);
				float yValNext = PointMap.allFinalizedPoints.get((int) xValNext);
				float yVal = yValLast + (yValNext - yValLast) / (xValNext - xValLast) * (playerXPos - xValLast);

				// convert y value to a sample value b/w -1 and 1, and play that sample
				float yMaxDist = (yMax - yMin)/2f; // maximum distance y can be from the yMiddle
				float yMiddle = yMin + yMaxDist; // y value in the middle b/w yMin and yMax
				float sampleValue = (yVal - yMiddle)/yMaxDist;
				StdAudio.play(sampleValue * volume/100f);
				
				// move playerXPos forward
				playerXPos += playbackRate;
				if(playerXPos > xValNext) {
					xValLastIndex++;
				}
				timesPlayed++;
			} else {
				if(readyToPlay) {
					readyToPlay = false;
				}
			}
		}
	}
	
	// takes a pointmap and sorts its points into an array
	public static ArrayList<Integer> sortPointMap(PointMap pointMap) {
		ArrayList<Integer> sortedXVals = new ArrayList<Integer>();
		
		for(Integer x : pointMap.keySet()) {
			sortedXVals.add(x);
		}
		Collections.sort(sortedXVals);

		return sortedXVals;
	}
	
	private static void findYExtremes() {
		for(Integer x : PointMap.allFinalizedPoints.keySet()) {
			int yVal = PointMap.allFinalizedPoints.get(x);
			if(yVal > yMax) {
				yMax = yVal;
			}
			if(yVal < yMin) {
				yMin = yVal;
			}
		}
	}
	
}
