package view;

import javax.swing.JComponent;
import javax.swing.JFrame;

import controller.EZConsoleController;
import model.BufferCell;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Queue;

public class EZConsoleView extends JFrame {
	private static final long serialVersionUID = 1837882079154564210L;
	
	private EZConsoleController controller;
	
	public EZConsoleView(EZConsoleController controller) {
		this.controller = controller;
		this.setTitle(this.controller.getModel().getShellTitle());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(this.controller.getModel());
		
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
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
	
	public void print(Object o) {
		controller.write(o.toString());
	}
	
	public void println(Object o) {
		this.print(o);
		this.print('\n');
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        paintViewContents(g);
    }

	public void paintViewContents(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Rectangle r = g.getClipBounds();
		
		int fontX = this.controller.getModel().getFontWidth();
		int fontY = this.controller.getModel().getFontHeight();
		
		int x1 = (int) (r.getMinX() / fontX);
		int x2 = (int) (r.getMaxX() / fontX) + 1;
		int y1 = (int) (r.getMinY() / fontY);		
		int y2 = (int) (r.getMaxY() / fontY) + 1;  

		int curX = controller.getModel().getCursorX();
		int curY = controller.getModel().getCursorY();

		int rows = controller.getModel().getShellSizeX();
		int cols = controller.getModel().getShellSizeY();
		
		for (int j = Math.max(0, y1); j < Math.min(y2, rows); j++) {
			int offset = j * cols;
			int start = Math.max(x1, 0);
			int end = Math.min(x2, cols);

			while (start < end) {
				
				BufferCell bc = this.controller.getModel().getBufferContents()[offset][start];
				Color nfg = bc.getContentColor();
				Color nbg = bc.getBackgroundColor();
				Font nf = bc.getFont();

				// index of ending position
				int i = start + 1;

				if ((j == curY) && (start == curX)) { // processar posició del cursor
					if (cursorVisible && cursorBlinkOn && cursorInverted) {
						// swap foreground and background colours
						
						// El cursor fet com a inversió "background-foreground"
						//System.out.println("Generant cursor per inversió");
						Color t = nfg;
						nfg = nbg;
						nbg = t;
					}
				} else {
					// detect run
					
					// "moure i mentre no hi hagi canvis"
					while ((i < end) && (!((j == curY) && (i == curX)))
							&& (nfg == data.foreground[offset + i])
							&& (nbg == data.background[offset + i])
							&& (nf == data.font[offset + i])) {
						i++;
					}
				}

				// set font
				g2d.setFont(nf);

				// draw background
				g2d.setBackground(nbg);
				g2d.clearRect(fontX * start, j * fontY, fontX
						* (i - start), fontY);

				// draw chars up to this point
				g2d.setColor(nfg);
				for (int k=start; k<i; k++) { 
					g2d.drawChars(this.getBufferRow(0), offset + k, 1, 
							k* fontX, 
							j * fontY + controller.getModel().getFontYOffset());
				}
				start = i;
			
			}
			
		}
	}
	
	private char[] getBufferRow(int row) {
		BufferCell[] bcRow = this.controller.getModel().getBufferContents()[row];
		char[] charArray = new char[bcRow.length]; 
		for (int i = 0; i < charArray.length; i++) {
			charArray[i] = bcRow[i].getContent();
		}
		return charArray;
	}
	
}
