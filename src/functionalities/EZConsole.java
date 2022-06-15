package functionalities;

import java.awt.Color;
import java.awt.event.KeyEvent;

import controller.EZConsoleController;
import view.EZConsoleView;

public class EZConsole {
	/*
	 * This class provides the functionalities of the
	 * EZConsole to be called from other developers code.
	 * To be used, a new EZConsole instance must be created
	 * for each independent windowed console needed.
	 */
	
	private static final int DEFAULTSIZE_X = 120;
	private static final int DEFAULTSIZE_Y = 30;
	private static final String DEFAULT_TITLE = "EZConsole for Java17 by Y. Bartolomé";
	
	private EZConsoleView view;
	private EZConsoleController controller;
	
	/* CONSOLE INSTANCES CREATION */
	
	//Creates the console by default
	public EZConsole () {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, DEFAULT_TITLE);
	}
	
	//Creates the console provided its title
	public EZConsole (String title) {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, title);
	}
	
	//Creates the console provided its with and length
	public EZConsole (int x, int y) {
		this(x, y, DEFAULT_TITLE);
	}
	
	//Creates the console provided all three values
	public EZConsole (int x, int y, String title) {
		this.controller = new EZConsoleController(x, y);
		this.view = new EZConsoleView(x, y, title, controller);
		System.out.println("Launching " + title + " in a new Window...");
	}
	
	/* CONSOLE SETTINGS */
	
	//Sets the visibility of the cursor
	public void setCursorVisible(boolean visible) {
		this.view.setCursorVisible(visible);
	}
	
	//Allows to set the cursor in a position within bounds
	public void setCursorPosition (int w, int h) {
		this.view.setCursorPosition(w, h);
	}
	
	//Shows or hides the prompt when reading input-
	public void showPrompt(boolean show) {
		this.controller.setShowPrompt(show);
	}
	
	//Allows to set a custom prompt.
	public void setPrompt(String newPrompt) {
		this.controller.setPrompt(newPrompt);
	}
	
	//Sets the cell background color
	public void setBackgroundColor(Color c) {
		view.setBackgroundColor(c);
	}
	
	//Sets the cell character color
	public void setContentsColor(Color c) {
		view.setContentsColor(c);
	}
	
	//Resets the color settings to default
	public void resetColor () {
		view.resetColor();
	}
	
	//Returns the cell height
	public int getRows() {
		return view.getRows();
	}
	
	//Returns the cell width	
	public int getColumns() {
		return this.getColumns();
	}
	
	/* PROMPTING */
	
	//Prints the .toString() of the provided object in the console.
	public void print(Object o) {
		this.view.print(o);
	}
	
	//Prints a empty line on the console, then a new line "\n".
	public void println() {
		this.println("");
	}
	
	//Prints the .toString() of the provided object in the console, then a new line "\n".
	public void println(Object o) {
		view.println(o);
	}
	
	//Cleanses all the content prompted and returns cursor to [0,0].
	public void clear() {
		controller.clear();
	}
	
	/* INPUT READING */
	
	//Checks if there is any key tap in the input buffer.
	public boolean keyAvailable() {
		return controller.keyAvailable();
	}
	
	//Resets the current key input buffer.
	public void clearBuffer() {
		this.controller.clearBuffer();
	}
	
	//Reads a single key pressed by the user (blocks execution until input).
	public KeyEvent readKey() {
		return this.controller.readKey(false);
	}
	//Same as readKey(), but (hide == true) won't show the input character.
	public KeyEvent readKey(boolean hide) {
		return this.controller.readKey(hide);
	}
	
	//Reads a single character.
	public char readChar() {
		return this.controller.readChar();
	}
	
	//Reads a string of characters until the user presses [Return].
	public String readString() {
		return this.controller.readLine();
	}
	
	//Reads an integer value until [Return] is pressed.
	public int readInt() {
		return this.controller.readInt();
	}
	
	//Reads a short integer value until [Return] is pressed.
	public long readShort() {
		return this.controller.readShort();
	}
	
	//Reads a long integer value until [Return] is pressed.
	public long readLong() {
		return this.controller.readLong();
	}
	
	//Reads a double value until [Return] is pressed.
	public double readDouble() {
		return this.controller.readDouble();
	}
	
	//Reads a float value until [Return] is pressed.
	public double readFloat() {
		return this.controller.readFloat();
	}
	
	//Reads a byte value until [Return] is pressed.
	public byte readByte() {
		return this.controller.readByte();
	}
}
