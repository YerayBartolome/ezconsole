package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

import controller.EZConsoleController;

public class EZConsoleRenderer extends JComponent {
	private static final long serialVersionUID = 2395050165190267844L;
	
	private EZConsoleController controller;
	
	public EZConsoleRenderer(EZConsoleController controller) {
		this.controller = controller;
		
		this.setPreferredSize(new Dimension(controller.getColumns() * controller.getFontWidth(), controller.getRows() * controller.getFontHeight()));

		repaint();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		Rectangle r = g.getClipBounds();
		
		int fontX = controller.getFontWidth();
		int fontY = controller.getFontHeight();

		int x1 = (int) (r.getMinX() / fontX);
		int x2 = (int) (r.getMaxX() / fontX) + 1;
		int y1 = (int) (r.getMinY() / fontX);
		int y2 = (int) (r.getMaxY() / fontX) + 1;

		int curX = controller.getCursorX();
		int curY = controller.getCursorY();
		
		int rows = controller.getRows();
		int cols = controller.getColumns();

		for (int j = Math.max(0, y1); j < Math.min(y2, rows); j++) {
			int offset = j * cols;
			int start = Math.max(x1, 0);
			int end = Math.min(x2, cols);

			while (start < end) {
				
				Color nfg = controller.getForegroundAt(offset+start);
				Color nbg = controller.getBackgroundAt(offset+start);
				Font nf = controller.getFontAt(offset+start);

				int i = start + 1;
				
				if ((j == curY) && (start == curX)) {
					if (controller.getCursorVisible() && controller.getCursorBlinkOn() && controller.getCursorInverted()) {
						Color t = nfg;
						nfg = nbg;
						nbg = t;
					}
				} else {
					while ((i < end) && (!((j == curY) && (i == curX)))
						&& (nfg == controller.getForegroundAt(offset+i))
							&& (nbg == controller.getBackgroundAt(offset+i))
							&& (nf == controller.getFontAt(offset+i))) {
						i++;
					}
				}

				g.setFont(nf);

				g.setBackground(nbg);
				g.clearRect(fontX * start, j * fontY, fontX
						* (i - start), fontY);

				g.setColor(nfg);
				for (int k=start; k<i; k++) {
					g.drawChars(controller.bufferContentsToCharArray(), offset + k, 1, 
							k* fontX, 
							j * fontY + controller.getFontYOffset());
				}
				start = i;
			
			}
		}
	}
	
}
