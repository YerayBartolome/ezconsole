package view;
import controller.EZConsoleController;
import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.*;

public class EZConsoleView extends JFrame {
	private static final long serialVersionUID = 8759638563543914280L;
	
	private EZConsoleController console;
	private EZConsoleRenderer render;
	private int columns, rows;
	
	private JMenuBar menuBar;
	private JMenuItem item0, divisor0, divisor1, divisor2, item1;
	
	public EZConsoleView (int columns, int rows, String title, EZConsoleController controller) {
		
	    this.setTitle(title);
	    
		this.columns = columns;
		this.rows = rows;
		
		console = controller;
		render = new EZConsoleRenderer(console);
		
		console.setRender(render);
		console.init(columns, rows);
		
		menuBar = new JMenuBar();
		
		item0 = new JMenuItem("Properties");
		divisor0 = new JMenuItem();
		divisor1 = new JMenuItem();
		divisor2 = new JMenuItem();
		item1 = new JMenuItem("About & Credits");

		item0.addActionListener(controller);
		item1.addActionListener(controller);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(controller);
		this.getContentPane().add(render);
		
		item0.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		item1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		menuBar.add(item0);
		menuBar.add(divisor0);
		menuBar.add(divisor1);
		menuBar.add(divisor2);
		menuBar.add(item1);
		this.setJMenuBar(menuBar);
		
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	public void print(Object o) {
		console.write(o.toString());
	}
	
	public void print(Object o, Color color) {
		Color fgc = console.getForeground();
		console.setForeground(color);
		print(o);
		console.setForeground(fgc);
	}
	
	public void println(Object o) {
		console.write(o.toString());
		console.write("\n");
	}
	
	public void println () {
		console.write("\n");
	}
	
	public void setCursorPosition (int w, int h) {
		if (w<0 || w>=this.columns || h<0 || h>=this.rows) {
			JOptionPane.showMessageDialog(this, 
                    "Cursor position out of bounds: ["+w+","+h+"]. program will terminate", 
                    "Incorrect Cursor position", JOptionPane.ERROR_MESSAGE);
			try {
				throw new IndexOutOfBoundsException("Cursor position out of bounds: ["+w+","+h+"]");
			}
			catch(IndexOutOfBoundsException ex) {
				ex.printStackTrace();
			}
			System.exit(0);
		}
		
		console.setCursorPos(w, h);
	}
	
	public void setContentsColor(Color c) {
		console.setForeground(c);
	}
	
	public void setBackgroundColor(Color c) {
		console.setBackground(c);
	}
	
	public void resetColor () {
		console.setForeground(EZConsoleController.DEFAULT_FOREGROUND);
		console.setBackground(EZConsoleController.DEFAULT_BACKGROUND);
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getColumns() {
		return this.columns;
	}

	public void setCursorVisible(boolean visible) {
		this.console.setCursorVisible(visible);
		
	}
	public void printHorizontalDivisor(int mode, int cols, int[] cellLength) {
		char c0 = '┌', c1 = '┬', c2 = '┐', c3 = '├', c4 = '┼', c5 = '┤', c6 = '└', c7 = '┴', c8='┘';
		switch(mode) {
		case 0: printHorizontalDivisor(c0, c1, c2, cols, cellLength); break;
		case 1: printHorizontalDivisor(c3, c4, c5, cols, cellLength); break;
		case 2: printHorizontalDivisor(c6, c7, c8, cols, cellLength); break;
		}
	}
	
	private void printHorizontalDivisor(char first, char middle, char last, int cols, int[] cellLength) {
		print(first);
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < cellLength[0]; j++) {
				print("─");
			}
			if (i != cols-1) print(middle);
		}
		print(last);
	}
}
