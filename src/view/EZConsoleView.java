package view;

import javax.swing.JComponent;
import javax.swing.JFrame;

import model.BufferCell;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class EZConsoleView extends JFrame {
	private static final long serialVersionUID = 1837882079154564210L;
	
	public EZConsoleView() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setShellSize(int x, int y) {
		this.setSize(x, y);
	}
	
	public void setBufferSize(int x, int y) {
		this.setBounds(0, 0, x, y);
	}
	
	public void setShellTitle(String title) {
		this.setTitle(title);
	}
	
	public void setShellVisible(boolean visible) {
		this.setVisible(visible);
	}
												
	public void paint(Graphics g) {
        super.paint(g);
        paintViewContent(g);
    }
	
	private int charXSize = 50;
	private int charYSize = 50;
	private int bufferSizeX;
	private int bufferSizeY;

	public void paintViewContent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for (int i = 0; i < bufferSizeX; i++) {
			for (int j = 0; j < bufferSizeY; j++) {
				
				this.viewPositionX++;
				if (this.viewPositionX >= this.bufferSizeX || this.nextToPaint[i][j].getContent() == '\n') {
					this.viewPositionX = 0;
					this.viewPositionY++;
				}
				
				g2d.setColor(this.nextToPaint[i][j].getBackgroundColor());
				g2d.drawRect(viewPositionX, viewPositionY, 14, 50);
				g2d.fillRect(viewPositionX, viewPositionY, 14, 50);

				char[] c = new char[1];
				c[0] = this.nextToPaint[i][j].getContent();
				g2d.setColor(this.nextToPaint[i][j].getContentColor());
				g2d.setFont(this.nextToPaint[i][j].getFont());
				g2d.drawChars(c, 0, 1, this.viewPositionX, this.viewPositionY);
				
			}
		}
	}
	
	private BufferCell[][] nextToPaint;
	private int viewPositionX, viewPositionY;
	
	public void updateViewCursor(int posX, int posY) {
		this.viewPositionX = posX;
		this.viewPositionY = posY;		
	}
	
	public void updateViewContent(BufferCell[][] bufferContent, int sizeX, int sizeY) {
		this.nextToPaint = bufferContent;
		this.bufferSizeX = sizeX;
		this.bufferSizeY = sizeY;
	}

}
