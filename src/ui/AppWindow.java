package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import wave_drawer.MouseStroke;
import wave_drawer.WavePlayer;

public class AppWindow extends JFrame {

	private JPanel contentPane;
	private JButton btnPlay, btnClear, btnUndo, btnRedo;
	private JTextField tfFrequency, tfVolume;
	
	public AppWindow() {
		setTitle("Wave Drawer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlMain = new JPanel();
		contentPane.add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlDraw = new JPanel();
		pnlDraw.setForeground(Color.DARK_GRAY);
		pnlMain.add(pnlDraw, BorderLayout.CENTER);
		pnlDraw.setLayout(new BorderLayout(0, 0));
		pnlDraw.add(new DrawPanel());
		pnlDraw.setBorder(new LineBorder(Color.BLACK, 3));
		
		JPanel pnlToolbar = new JPanel();
		pnlMain.add(pnlToolbar, BorderLayout.SOUTH);
		
		btnClear = new JButton("Clear");
		pnlToolbar.add(btnClear);
		
		btnUndo = new JButton("Undo");
		pnlToolbar.add(btnUndo);
		
		btnRedo = new JButton("Redo");
		pnlToolbar.add(btnRedo);
		
		JPanel pnlPlay = new JPanel();
		contentPane.add(pnlPlay, BorderLayout.SOUTH);
		
		tfFrequency = new JTextField();
		tfFrequency.setText("440");
		tfFrequency.setToolTipText("frequency at which the wave is to be played");
		tfFrequency.setColumns(6);
		
		JLabel lblFrequency = new JLabel("Frequency");
		pnlPlay.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlPlay.add(lblFrequency);
		pnlPlay.add(tfFrequency);
		
		btnPlay = new JButton("Play");
		pnlPlay.add(btnPlay);
		
		JLabel lblVolume = new JLabel("Volume");
		pnlPlay.add(lblVolume);
		
		tfVolume = new JTextField();
		tfVolume.setToolTipText("volume (out of 100)");
		tfVolume.setText("50");
		tfVolume.setColumns(3);
		pnlPlay.add(tfVolume);
		
		addListeners();
	}
	
	private void addListeners() {
		btnPlay.addActionListener((e) -> {
			WavePlayer.isPlaying = !WavePlayer.isPlaying;
		});
		btnClear.addActionListener((e) -> {
			if(!WavePlayer.isPlaying) {
				MouseStroke.undo(MouseStroke.allFinalizedMouseStrokes.size());
			}
		});
		btnUndo.addActionListener((e) -> {
			if(!WavePlayer.isPlaying) {
				MouseStroke.undo(1);
			}
		});
		btnRedo.addActionListener((e) -> {
			if(!WavePlayer.isPlaying) {
				MouseStroke.redo();
			}
		});
		
		tfFrequency.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						WavePlayer.frequency = clamp(Float.parseFloat(tfFrequency.getText()), 0, 999999);;
					} catch(NumberFormatException ex){
					}
					tfFrequency.setText(String.valueOf(WavePlayer.frequency));
				}
			}
		});
		
		tfVolume.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						WavePlayer.volume = (int) clamp(Integer.parseInt(tfVolume.getText()), 0, 100);
					} catch(NumberFormatException ex){
					}
					tfVolume.setText(String.valueOf(WavePlayer.volume));
				}
			}
		});
	}

	
	private float clamp(float val, float min, float max) {
		if(val > max) {
			return max;
		}
		if(val < min) {
			return min;
		}
		return val;
	}
	
}
