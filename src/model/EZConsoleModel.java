package model;

import java.awt.Color;
import java.awt.Font;

import view.EZConsoleView;

public class EZConsoleModel {

	private int shellSizeX;
	private int shellSizeY;
	
	private int bufferSizeX;
	private int bufferSizeY;
	
	private int cursorX = -1;
	private int cursorY = 0;
	
	private String shellTitle;
	
	private BufferCell[][] bufferGrid;
	private BufferCell lastBufferCell;
	
	private Color currentContentsColor = Color.WHITE;
	private Color currentBackgroundColor = Color.BLACK;
	
	private Font currentFont = new Font("Monospace", Font.PLAIN, 14);
	
	public Color getCurrentContentsColor() {
		return currentContentsColor;
	}

	public void setCurrentContentsColor(Color currentContentsColor) {
		this.currentContentsColor = currentContentsColor;
	}

	public Color getCurrentBackgroundColor() {
		return currentBackgroundColor;
	}

	public void setCurrentBackgroundColor(Color currentBackgroundColor) {
		this.currentBackgroundColor = currentBackgroundColor;
	}
	
	public EZConsoleModel (int x, int y, String title) {
		
		this.shellSizeX = x;
		this.shellSizeY = y;
		
		this.bufferSizeX = x;
		this.bufferSizeY = y;
		
		this.bufferGrid = new BufferCell[bufferSizeX][bufferSizeY];
		
		for(int i = 0; i < bufferSizeX; i++) {
			for(int j = 0; j < bufferSizeY; j++) {
				lastBufferCell = new BufferCell(' ');
				this.setBufferCell(lastBufferCell);
				this.addBufferContents(lastBufferCell);
			}
		}
		
		this.setShellTitle(title);
	}
	
	public int getShellSizeX() {
		return shellSizeX;
	}
	public void setShellSizeX(int shellSizeX) {
		this.shellSizeX = shellSizeX;
	}
	public int getShellSizeY() {
		return shellSizeY;
	}
	public void setShellSizeY(int shellSizeY) {
		this.shellSizeY = shellSizeY;
	}
	public int getBufferSizeX() {
		return bufferSizeX;
	}
	public void setBufferSizeX(int bufferSizeX) {
		this.bufferSizeX = bufferSizeX;
	}
	public int getBufferSizeY() {
		return bufferSizeY;
	}
	public void setBufferSizeY(int bufferSizeY) {
		this.bufferSizeY = bufferSizeY;
	}

	public String getShellTitle() {
		return shellTitle;
	}

	public void setShellTitle(String shellTitle) {
		this.shellTitle = shellTitle;
	}

	public Font getCurrentFont() {
		return currentFont;
	}

	public void setCurrentFont(Font currentFont) {
		this.currentFont = currentFont;
	}

	public int getCursorX() {
		return cursorX;
	}

	public void setCursorX(int cursorX) {
		this.cursorX = cursorX;
	}

	public int getCursorY() {
		return cursorY;
	}

	public void setCursorY(int cursorY) {
		this.cursorY = cursorY;
	}
	
	public BufferCell[][] getBufferContents() {
		return this.bufferGrid;
	}
	
	public void addBufferContents(/*Object o, EZConsoleView v*/ BufferCell bc) {
		/*char[] newContents = o.toString().toCharArray();
		for(int i = 0; i < newContents.length; i++) {
			BufferCell bc = this.bufferGrid[cursorX][cursorY];
			this.setBufferCell(bc, newContents[i]);
			v.paintViewContent(bc, cursorX, cursorY);
			cursorX++;
			if(cursorX >= bufferSizeX) {
				cursorX = 0;
				cursorY++;
			}
		}*/
		cursorX++;
		if (cursorX >= this.bufferSizeX || bc.getContent() == '\n') {
			cursorX = 0;
			cursorY++;
		}
		this.setBufferCell(bc);
		this.bufferGrid[cursorX][cursorY] = bc;
		this.lastBufferCell = bc;
	}
	
	private void setBufferCell(BufferCell bufferCell/*, char newContent*/) {
		//bufferCell.setContent(newContent);
		bufferCell.setContentColor(this.currentContentsColor);
		bufferCell.setBackgroundColor(this.currentBackgroundColor);
		bufferCell.setFont(this.currentFont);
	}
	
	public BufferCell getLastBufferCell() {
		return this.lastBufferCell;
	}
}
