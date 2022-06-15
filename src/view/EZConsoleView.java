package view;
import controller.EZConsoleController;
import java.awt.Color;

import javax.swing.*;

public class EZConsoleView extends JFrame {
	private static final long serialVersionUID = 8759638563543914280L;
	
	private EZConsoleController console;
	private EZConsoleRenderer render;
	private int columns, rows;
	
	public EZConsoleView (int columns, int rows, String title, EZConsoleController controller) {
		
	    this.setTitle(title);
	    
		this.columns = columns;
		this.rows = rows;
		
		console = controller;
		render = new EZConsoleRenderer(console);
		
		console.setRender(render);
		console.init(columns, rows);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(controller);
		this.getContentPane().add(render);
		
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	public void print(Object o) {
		console.write(o.toString());
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
	
}
