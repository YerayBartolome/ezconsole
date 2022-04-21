package main;

import java.awt.Color;
import view.EZConsoleView;

public class EZConsole {
	
	private static final int DEFAULTSIZE_X = 120;
	private static final int DEFAULTSIZE_Y = 30;
	private static final String DEFAULT_TITLE = "EZConsole for Java17 by Yeray Bartolomé";
	
	private EZConsoleView view;
	
	public EZConsole () {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, DEFAULT_TITLE);
	}
	
	public EZConsole (String title) {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, title);
	}
	
	public EZConsole (int x, int y) {
		this(x, y, DEFAULT_TITLE);
	}
	
	public EZConsole (int x, int y, String title) {
		this.view = new EZConsoleView(x, y, title);
		System.out.println("Launching " + title + " in a new Window...");
	}
	
	public void print(Object o) {
		view.print(o);
	}
	
	public void println() {
		this.println(' ');
	}
	
	public void println(Object o) {
		view.println(o);
	}
	
	public void setBackgroundColor(Color c) {
		view.setBackgroundColor(c);
	}
	
	public void setContentsColor(Color c) {
		view.setContentsColor(c);
	}
	
}
