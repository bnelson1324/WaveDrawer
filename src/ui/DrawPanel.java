package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import wave_drawer.MouseStroke;
import wave_drawer.PointMap;
import wave_drawer.WavePlayer;

public class DrawPanel extends Canvas {

	public PointMap pointsToDraw; // queue of points to draw
	public MouseStroke currentStroke; // stores the points drawn in the current mouse stroke
	private boolean mouseDown = false;

	public DrawPanel() {
		super();
		setBackground(Color.WHITE);
		
		pointsToDraw = new PointMap();
		currentStroke = new MouseStroke();
		
		MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	mouseDown = true;
            	queuePointForDrawing(e);
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
            	queuePointForDrawing(e);
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
            	mouseDown = false;
            	// finish mouse stroke, add it to allMouseStrokes, reset currentStroke, and repaint
            	MouseStroke.finalizeMouseStroke(currentStroke);
            	currentStroke = new MouseStroke();
            	repaint();
            }
        };
		
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		
		// timer to paint the drawPanel 60 times per second if mouse is down
		Timer redrawTimer = new Timer((int) (1f/60*1000), (e) -> {
			if(this.mouseDown) {
				this.repaint();
			}
		});
		redrawTimer.start();
	}
	
	public void queuePointForDrawing(MouseEvent e) {
		// only queues points for drawing if not currently playing the wave
		if(WavePlayer.isPlaying) {
			return;
		}
		
		Point p = e.getPoint();
		pointsToDraw.put(p.x, p.y);
	}
	
	/* drawing */
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		
		// draws each point in pointsToDraw if there is not already a point at that x value
		PointMap pointsToDrawClone = (PointMap) pointsToDraw.clone();
		pointsToDraw.clear();
		
		g.setColor(Color.BLUE);
		for(Integer x : pointsToDrawClone.keySet()) {
			if(!(PointMap.allFinalizedPoints.containsKey(x) || currentStroke.pointMap.containsKey(x))) {
				g.fillOval(x, pointsToDrawClone.get(x), 5, 5);
				currentStroke.addPoint(new Point(x, pointsToDrawClone.get(x)));
			}
		}	
	}
	
	/* misc */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
}
