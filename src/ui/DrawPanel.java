package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import wave_drawer.MouseStroke;

public class DrawPanel extends Canvas {

	public MouseStroke currentStroke; // stores the points drawn in the current mouse stroke
	private boolean mouseDown = false;

	public DrawPanel() {
		super();
		setBackground(Color.WHITE);
		
		currentStroke = new MouseStroke();
		
		MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	mouseDown = true;
            	makePoint(e);
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
            	makePoint(e);
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
            	mouseDown = false;
            	// finish mouse stroke, add it to allMouseStrokes, and reset currentStroke
            	MouseStroke.finalizeMouseStroke(currentStroke);
            	currentStroke = new MouseStroke();
            }
        };
		
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		
		// timer to redraw the drawPanel 30 times per second if mouse is down
		Timer redrawTimer = new Timer((int) (1f/30*1000), (e) -> {
			if(this.mouseDown) {
				this.repaint();
			}
		});
		redrawTimer.start();
	}
	
	public void makePoint(MouseEvent e) {
		currentStroke.addPoint(e.getPoint());
	}
	
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;

		// gets all points from allFinalizedMouseStrokes and currentStroke
		HashMap<Integer, Integer> allPoints = MouseStroke.addPointMaps(MouseStroke.allFinalizedPoints, currentStroke.pointMap);
		
		// draws all points
		g.setColor(Color.BLUE);
		for(Integer x : allPoints.keySet()) {
			g.fillOval(x, allPoints.get(x), 5, 5);
		}
	}
	
}
