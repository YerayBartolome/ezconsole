package functionalities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.concurrent.Semaphore;

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
	
	private volatile EZConsoleView view;
	private volatile EZConsoleController controller;
	
	private Semaphore mutex = new Semaphore(1);
	
	/* CONSOLE INSTANCES CREATION */
	
	//Creates the console by default.
	public EZConsole () {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, DEFAULT_TITLE);
	}
	
	//Creates the console provided its title.
	public EZConsole (String title) {
		this(DEFAULTSIZE_X, DEFAULTSIZE_Y, title);
	}
	
	//Creates the console provided its with and length.
	public EZConsole (int x, int y) {
		this(x, y, DEFAULT_TITLE);
	}
	
	//Creates the console provided all three values.
	public EZConsole (int x, int y, String title) {
		this.controller = new EZConsoleController(x, y);
		this.view = new EZConsoleView(x, y, title, controller);
		System.out.println("Launching " + title + " in a new Window...");
	}
	
	/* CONSOLE SETTINGS */
	
	//Sets the visibility of the cursor.
	public void setCursorVisible(boolean visible) {
		try {mutex.acquire();} catch (Exception ex) {}
		this.view.setCursorVisible(visible);
		mutex.release();
	}
	
	//Allows to set the cursor in a position within bounds.
	public void setCursorPosition (int w, int h) {
		try {mutex.acquire();} catch (Exception ex) {}
		this.view.setCursorPosition(w, h);
		mutex.release();
	}
	
	//Shows or hides the prompt when reading input.
	public void showPrompt(boolean show) {
		try {mutex.acquire();} catch (Exception ex) {}
		this.controller.setShowPrompt(show);
		mutex.release();
	}
	
	//Allows to set a custom prompt.
	public void setPrompt(String newPrompt) {
		try {mutex.acquire();} catch (Exception ex) {}
		this.controller.setPrompt(newPrompt);
		mutex.release();
	}
	
	//Sets the console cells background color.
	public void setBackgroundColor(Color c) {
		try {mutex.acquire();} catch (Exception ex) {}
		view.setBackgroundColor(c);
		mutex.release();
	}
	
	//Sets the cell character color.
	public void setContentsColor(Color c) {
		try {mutex.acquire();} catch (Exception ex) {}
		view.setContentsColor(c);
		mutex.release();
	}
	
	//Resets the color settings to default.
	public void resetColor () {
		try {mutex.acquire();} catch (Exception ex) {}
		view.resetColor();
		mutex.release();
	}
	
	//Returns the cell height.
	public int getRows() {
		return view.getRows();
	}
	
	//Returns the cell width.	
	public int getColumns() {
		return this.getColumns();
	}
	
	/* DISPLAYING CONTENT */
	
	//Prints the .toString() of the provided object in the console.
	public void print(Object o) {
		try {mutex.acquire();} catch (Exception ex) {}
		this.view.print(o);
		mutex.release();
	}
	
	//Prints in a specified content color without changing the overall settings.
	public void print(Object o, Color color) {
		try {mutex.acquire();} catch (Exception ex) {}
		this.view.print(o, color);
		mutex.release();
	}
	
	//Prints a empty line on the console, then a new line "\n".
	public void println() {
		this.println("");
	}
	
	//Prints the .toString() of the provided object in the console, then a new line "\n".
	public void println(Object o) {
		try {mutex.acquire();} catch (Exception ex) {}
		view.println(o);
		mutex.release();
	}
	
	//Prints in a specified content color without changing settings, then a new line "\n".
	public void println(Object o, Color c) {
		this.print(o, c);
		this.println();
	}
	
	//Prints a table provided a 2D array of objects and the horizontal length.
	public void printTable(Object[][] o, int[] cellLength) {
		try {mutex.acquire();} catch (Exception ex) {}
		view.println();
		view.printHorizontalDivisor(0, o[0].length, cellLength);
		view.println();
		for(int i = 0; i < o.length; i++) {
			view.print('│');
			for (int j = 0; j < o[0].length; j++) {
				view.print(o[i][j]);
				for(int k = 0; k < cellLength[i]-o[i][j].toString().length(); k++) view.print(' '); 
				view.print('│');
			}
			view.println();
			view.printHorizontalDivisor(1, o[0].length, cellLength);
			view.println();
		}
		view.println();
		view.printHorizontalDivisor(2, o[0].length, cellLength);
		mutex.release();
	}
	
	
	
	//Cleanses all the content prompted and returns cursor to [0,0].
	public void clear() {
		try {mutex.acquire();} catch (Exception ex) {}
		controller.clear();
		mutex.release();
	}
	
	/* INPUT READING */
	
	//Checks if there is any key tap in the input buffer.
	public boolean keyAvailable() {
		try {mutex.acquire();} catch (Exception ex) {}
		boolean ka = controller.keyAvailable();
		mutex.release();
		return ka;
	}
	
	//Resets the current key input buffer.
	public void clearBuffer() {
		try {mutex.acquire();} catch (Exception ex) {}
		this.controller.clearBuffer();
		mutex.release();
	}
	
	//Reads a single key pressed by the user (blocks execution until input).
	public KeyEvent readKey() {
		return this.readKey(false);
	}
	//Same as readKey(), but (hide == true) won't show the input character.
	public KeyEvent readKey(boolean hide) {
		try {mutex.acquire();} catch (Exception ex) {}
		KeyEvent ke = this.controller.readKey(hide);
		mutex.release();
		return ke;
	}
	
	//Reads a single character.
	public char readChar() {
		try {mutex.acquire();} catch (Exception ex) {}
		char c = controller.readChar();
		mutex.release();
		return c;
	}
	
	//Reads a string of characters until the user presses [Return].
	public String readString() {
		try {mutex.acquire();} catch (Exception ex) {}
		String str = controller.readLine();
		mutex.release();
		return str;
	}
	
	//Reads an integer value until [Return] is pressed.
	public int readInt() {
		try {mutex.acquire();} catch (Exception ex) {}
		int i = controller.readInt();
		mutex.release();
		return i;
	}
	
	//Reads a short integer value until [Return] is pressed.
	public short readShort() {
		try {mutex.acquire();} catch (Exception ex) {}
		short s = controller.readShort();
		mutex.release();
		return s;
	}
	
	//Reads a long integer value until [Return] is pressed.
	public long readLong() {
		try {mutex.acquire();} catch (Exception ex) {}
		long l = controller.readLong();
		mutex.release();
		return l;
	}
	
	//Reads a double value until [Return] is pressed.
	public double readDouble() {
		try {mutex.acquire();} catch (Exception ex) {}
		double d = this.controller.readDouble();
		mutex.release();
		return d;
	}
	
	//Reads a float value until [Return] is pressed.
	public float readFloat() {
		try {mutex.acquire();} catch (Exception ex) {}
		float f = controller.readFloat();
		mutex.release();
		return f;
	}
	
	//Reads a byte value until [Return] is pressed.
	public byte readByte() {
		try {mutex.acquire();} catch (Exception ex) {}
		byte b = controller.readByte();
		mutex.release();
		return b;
	}
}
