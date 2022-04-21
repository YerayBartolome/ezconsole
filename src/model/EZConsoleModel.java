package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JComponent;

import view.EZConsoleView;

public class EZConsoleModel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	/* Console settings params */
	
	//Shell dimensions
	private int shellSizeX;
	private int shellSizeY;
	
	//Content 2D buffer dimensions
	private int bufferSizeX;
	private int bufferSizeY;
	
	//Content visual settings
	private String shellTitle;
	private Color currentContentsColor = Color.WHITE;
	private Color currentBackgroundColor = Color.BLACK;
	private Font currentFont = new Font("Monospace", Font.PLAIN, 14);
	
	private int fontSizeX;
	private int fontSizeY;
	private int fontYOffset;
	
	private int tabXOffset = 5;
	
	/* Console content manipulation */
	
	//Actual stored data
	private BufferCell[][] bufferContents;
	
	//Data writing position
	private int cursorX = 0;
	private int cursorY = 0;
	
	/* Model constructor */
	public EZConsoleModel (int x, int y, String title) {
		
		//initialization of settings
		this.shellSizeX = x;
		this.shellSizeY = y;
		
		this.bufferSizeX = x;
		this.bufferSizeY = y;
		
		this.bufferContents = new BufferCell[x][y];
		
		for(int i = 0; i < bufferSizeX; i++) {
			for(int j = 0; j < bufferSizeY; j++) {
				BufferCell bc = new BufferCell(' ');
				this.innitBufferCell(bc);
				this.writeBufferContentAt(i, j, bc);
			}
		}
		
		this.setPreferredSize(new Dimension(this.bufferSizeX * this.fontSizeX, this.bufferSizeY * this.fontSizeY));
	}
	
	
	/* Model manipulation functions */
	
	//Writes a single BufferCell
	public void writeBufferContent(BufferCell bc) {
		this.writeBufferContentAt(this.cursorX, this.cursorY, bc);
		this.updateCursor(bc);
	}
	
	//Writes a specific BufferCell position
	public void writeBufferContentAt(int x, int y, BufferCell bc) {
		this.innitBufferCell(bc);
		this.bufferContents[x][y] = bc;
	}
	
	private void updateCursor(BufferCell bc) {
		char c = bc.getContent();
		switch (c) {
		case '\n':
			this.cursorX = 0;
			this.cursorY++;
			break;
		case '\t':
			this.cursorX+=this.tabXOffset;
			break;
		default:
			this.cursorX++;
			break;
		}
		if (this.cursorX >= this.bufferSizeX) {
			this.cursorX = 0;
			this.cursorY++;
		}
		
		if (this.cursorY >= this.bufferSizeY) {
			this.pushBufferUP();
		}
		this.repaint();
	}
	
	private void pushBufferUP() {
		for (int i = 0; i < this.bufferSizeX; i++) {
			for (int j = 1; j < this.bufferSizeY; j++) {
				this.writeBufferContentAt(i, j-1, this.bufferContents[i][j]);
			}
		}
		
		for (int i = 0; i < this.bufferSizeX; i++) {
			BufferCell bc = new BufferCell(' ');
			this.innitBufferCell(bc);
			this.writeBufferContentAt(i, this.getBufferSizeY()-1, bc);
		}
	}


	private void innitBufferCell(BufferCell bufferCell) {
		bufferCell.setContentColor(this.currentContentsColor);
		bufferCell.setBackgroundColor(this.currentBackgroundColor);
		bufferCell.setFont(this.currentFont);
	}
	
	/* Getters and Setters */
	
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

		FontRenderContext frc = new FontRenderContext(currentFont.getTransform(), false, false);
		Rectangle2D contentBounds = currentFont.getStringBounds("X", frc);
		fontSizeX = (int) contentBounds.getWidth();
		fontSizeY = (int) contentBounds.getHeight();
		fontYOffset = -(int) contentBounds.getMinY();
		
		this.setPreferredSize(new Dimension(this.bufferSizeX * fontSizeX, this.bufferSizeY * fontSizeY));

		repaint();
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
		return this.bufferContents;
	}

	public int getFontWidth() {
		return fontSizeX;
	}

	public void setFontWidth(int fontWidth) {
		this.fontSizeX = fontWidth;
	}

	public int getFontHeight() {
		return fontSizeY;
	}

	public void setFontHeight(int fontHeight) {
		this.fontSizeY = fontHeight;
	}

	public int getFontYOffset() {
		return fontYOffset;
	}

	public void setFontYOffset(int fontYOffset) {
		this.fontYOffset = fontYOffset;
	}
	
}
