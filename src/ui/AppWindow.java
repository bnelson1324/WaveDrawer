package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class AppWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfFrequency;
	private JTextField tfVolume;

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
		
		JButton btnClear = new JButton("Clear");
		pnlToolbar.add(btnClear);
		
		JButton btnUndo = new JButton("Undo");
		pnlToolbar.add(btnUndo);
		
		JPanel pnlPlay = new JPanel();
		contentPane.add(pnlPlay, BorderLayout.SOUTH);
		
		tfFrequency = new JTextField();
		tfFrequency.setText("440");
		tfFrequency.setToolTipText("frequency at which the wave is to be played");
		tfFrequency.setColumns(4);
		
		JLabel lblFrequency = new JLabel("Frequency");
		pnlPlay.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlPlay.add(lblFrequency);
		pnlPlay.add(tfFrequency);
		
		JButton btnPlay = new JButton("Play");
		pnlPlay.add(btnPlay);
		
		JLabel lblVolume = new JLabel("Volume");
		pnlPlay.add(lblVolume);
		
		tfVolume = new JTextField();
		tfVolume.setToolTipText("volume (out of 100)");
		tfVolume.setText("50");
		tfVolume.setColumns(2);
		pnlPlay.add(tfVolume);
	}
	
}
