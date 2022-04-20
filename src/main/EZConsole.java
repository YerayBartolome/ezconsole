package main;

import java.awt.Color;

import controller.EZConsoleController;
import model.EZConsoleModel;
import view.EZConsoleView;

public class EZConsole {
	
	private static final int DEFAULTSIZE_X = 800;
	private static final int DEFAULTSIZE_Y = 400;
	private static final String DEFAULTTITLE = "EZConsole for Java17 by Yeray Bartolomé";
	
	private EZConsoleController controller;
	
	public EZConsole () {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, DEFAULTTITLE);
	}
	
	public EZConsole (String title) {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, title);
	}
	
	public EZConsole (int x, int y) {
		this(x, y, DEFAULTTITLE);
	}
	
	public EZConsole (int x, int y, String title) {
		EZConsoleModel model = new EZConsoleModel(x, y, title);
		EZConsoleView view = new EZConsoleView();
		this.controller = new EZConsoleController(model, view);
		System.out.println("Launching " + this.DEFAULTTITLE + " in a new Window...");
	}
	
	public void print(Object o) {
		controller.print(o);
	}
	
	public void println(Object o) {
		controller.println(o);
	}
	
	public void setBackgroundColor(Color c) {
		controller.setBackgroundColor(c);
	}
	
	public void setContentsColor(Color c) {
		controller.setContentsColor(c);
	}
	
}
