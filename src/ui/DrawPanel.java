package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import wave_drawer.MouseStroke;
import wave_drawer.PointMap;
import wave_drawer.WavePlayer;

public class DrawPanel extends Canvas {

	public PointMap pointsToDraw; // queue of points to draw
	public MouseStroke currentStroke; // stores the points drawn in the current mouse stroke
	
	public static boolean clearAndRedraw = false; // whether the paint function should clear and redraw all points next time it is called
	public static boolean connectPointsWithLines = false; // whether the paint function should connect all points with lines next time it is called

	public DrawPanel() {
		super();
		setBackground(Color.WHITE);
		
		pointsToDraw = new PointMap();
		currentStroke = new MouseStroke();
		
		MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	queuePointForDrawing(e);
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
            	queuePointForDrawing(e);
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
            	// finish mouse stroke, add it to allMouseStrokes, reset currentStroke, and repaint
            	MouseStroke.finalizeMouseStroke(currentStroke);
            	currentStroke = new MouseStroke();
            	repaint();
            }
        };
		
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		
		// timer to paint the drawPanel 60 times per second
		Timer redrawTimer = new Timer((int) (1f/60*1000), (e) -> {
			this.repaint();
		});
		redrawTimer.start();
	}
	
	public void queuePointForDrawing(MouseEvent e) {
		// only queues points for drawing if not currently playing the wave
		if(WavePlayer.getIsPlaying()) {
			return;
		}
		
		Point p = e.getPoint();
		pointsToDraw.put(p.x, p.y);
		MouseStroke.redoQueue.clear();
	}
	
	/* drawing */
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		
		// draws each point in pointsToDraw if there is not already a point at that x value
		PointMap pointsToDrawClone = (PointMap) pointsToDraw.clone();
		pointsToDraw.clear();
		
		g.setColor(Color.BLUE);
		drawPointsInPointMap(pointsToDrawClone, g, true);
		
		if(clearAndRedraw) {
			g.clearRect(0, 0, getWidth(), getHeight());
			drawPointsInPointMap(PointMap.allFinalizedPoints, g, false);
			drawPointsInPointMap(pointsToDrawClone, g, false);
			clearAndRedraw = false;
		}
		
		if(connectPointsWithLines) {
			PointMap allExistingPoints = (PointMap) PointMap.allFinalizedPoints.clone();
			allExistingPoints.addPointMap(pointsToDrawClone);
			ArrayList<Integer> sortedXVals = WavePlayer.sortPointMap(allExistingPoints);
			for(int i = 0; i < sortedXVals.size() - 1; i++) {
				Integer thisX = sortedXVals.get(i);
				Point thisPoint = new Point(thisX, allExistingPoints.get(thisX));
				Integer nextX = sortedXVals.get(i + 1);
				Point nextPoint = new Point(nextX, allExistingPoints.get(nextX));
				g.drawLine(thisPoint.x, thisPoint.y, nextPoint.x, nextPoint.y);
			}
			connectPointsWithLines = false;
		}
	}
	
	private void drawPointsInPointMap(PointMap pm, Graphics g, boolean partOfCurrentStroke) {
		for(Integer x : pm.keySet()) {
			if(!(PointMap.allFinalizedPoints.containsKey(x) || currentStroke.pointMap.containsKey(x)) || !partOfCurrentStroke) {
				g.fillOval(x, pm.get(x), 5, 5);
				if(partOfCurrentStroke) {
					currentStroke.addPoint(new Point(x, pm.get(x)));
				}
			}
		}
	}
	
	/* misc */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
}
