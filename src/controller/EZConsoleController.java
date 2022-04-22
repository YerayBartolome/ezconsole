package controller;

import model.BufferCell;
import model.EZConsoleModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.JComponent;

public class EZConsoleController extends JComponent {
	private static final long serialVersionUID = 8290082453035451253L;
	
	public static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
	public static final Color DEFAULT_BACKGROUND = Color.BLACK;
	private static final Font DEFAULT_FONT = new Font("Monospace", Font.PLAIN, 20);
	private static final int TAB_SPACES = 5;
 
	private EZConsoleModel model = new EZConsoleModel();

	private int fontWidth;
	private int fontHeight;
	private int fontYOffset;

	private int cursorX = 0;
	private int cursorY = 0;
	private Font mainFont = null;
	private Font currentFont = null;
	private Color currentForeground = DEFAULT_FOREGROUND;
	private Color currentBackground = DEFAULT_BACKGROUND;
	
	public EZConsoleController(int columns, int rows) {
		setMainFont(DEFAULT_FONT);
		setFont(mainFont);
		init(columns, rows);
	}

	public void setMainFont(Font font) {
		mainFont = font;

		FontRenderContext fontRenderContext = new FontRenderContext(
				mainFont.getTransform(), false, false);
		Rectangle2D charBounds = mainFont.getStringBounds("X",
				fontRenderContext);
		fontWidth = (int) charBounds.getWidth();
		fontHeight = (int) charBounds.getHeight();
		fontYOffset = -(int) charBounds.getMinY();
		
		setPreferredSize(new Dimension(model.columns * fontWidth, model.rows * fontHeight));

		repaint();
	}

	public void setRows(int rows) {
		resize(this.model.columns, rows);
	}

	public void setFont(Font f) {
		currentFont = f;
	}

	public int getRows() {
		return model.rows;
	}

	public void setColumns(int columns) {
		resize(columns, this.model.rows);
	}

	public int getColumns() {
		return model.columns;
	}

	public int getFontWidth() {
		return fontWidth;
	}

	public int getFontHeight() {
		return fontHeight;
	}

	public void repaintArea(int column, int row, int width, int height) {
		
		if (column==this.cursorX && row==this.cursorY && width==1 && height==1) {
			return;
		}
		
		int fw = getFontWidth();
		int fh = getFontHeight();
		repaint(column * fw, row * fh, width * fw, height * fh);
	}

	protected void init(int columns, int rows) {
		model.init(columns, rows);
		BufferCell bc = new BufferCell(' ', DEFAULT_BACKGROUND, DEFAULT_FOREGROUND, DEFAULT_FONT);
		Arrays.fill(this.model.bufferContents, bc);

		setPreferredSize(new Dimension(columns * fontWidth, rows * fontHeight));
	}

	public void resize(int columns, int rows) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		clearArea(0, 0, model.columns, model.rows);
	}

	public void resetCursor() {
		repaintArea(cursorX, cursorY, 0, 0);
		cursorX = 0;
		cursorY = 0;
		repaintArea(cursorX, cursorY, 0, 0);
	}

	public void clearScreen() {
		clear();
		resetCursor();
	}

	private void clearArea(int column, int row, int width, int height) {
		model.fillArea(new BufferCell(' ', currentForeground, currentBackground, currentFont),
				column, row, width, height);
		repaintArea(0, 0, width, height);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		Rectangle r = g.getClipBounds();

		int x1 = (int) (r.getMinX() / fontWidth);
		int x2 = (int) (r.getMaxX() / fontWidth) + 1;
		int y1 = (int) (r.getMinY() / fontWidth);
		int y2 = (int) (r.getMaxY() / fontWidth) + 1;

		int curX = getCursorX();
		int curY = getCursorY();

		for (int j = Math.max(0, y1); j < Math.min(y2, model.rows); j++) {
			int offset = j * model.columns;
			int start = Math.max(x1, 0);
			int end = Math.min(x2, model.columns);

			while (start < end) {
				
				Color nfg = model.getContentColorAt(offset+start);
				Color nbg = model.getBackgroundAt(offset+start);
				Font nf = model.getFontAt(offset+start);

				int i = start + 1;
				
				while ((i < end) && (!((j == curY) && (i == curX)))
						&& (nfg == model.getContentColorAt(offset+i))
						&& (nbg == model.getBackgroundAt(offset+i))
						&& (nf == model.getFontAt(offset+i))) {
					i++;
				}

				g.setFont(nf);

				g.setBackground(nbg);
				g.clearRect(fontWidth * start, j * fontHeight, fontWidth
						* (i - start), fontHeight);

				g.setColor(nfg);
				for (int k=start; k<i; k++) {
					g.drawChars(model.bufferContentsToCharArray(), offset + k, 1, 
							k* fontWidth, 
							j * fontHeight + fontYOffset);
				}
				start = i;
			
			}
		}
	}

	public void setCursorPos(int column, int row) {
		if ((column < 0) || (column >= model.columns))
			throw new Error("Invalid X cursor position: " + column);
		if ((row < 0) || (row >= model.rows))
			throw new Error("Invalid Y cursor position: " + row);
		cursorX = column;
		cursorY = row;
	}

	public int getCursorX() {
		return cursorX;
	}

	public int getCursorY() {
		return cursorY;
	}

	public void setForeground(Color c) {
		currentForeground = c;
	}

	public void setBackground(Color c) {
		currentBackground = c;
	}

	public Color getForeground() {
		return currentForeground;
	}

	public Color getBackground() {
		return currentBackground;
	}

	public char getCharAt(int column, int row) {
		return model.getBCAt(column, row).getContent();
	}

	public Color getForegroundAt(int column, int row) {
		return model.getContentColorAt(column, row);
	}

	public Color getBackgroundAt(int column, int row) {
		return model.getBackgroundAt(column, row);
	}

	public Font getFontAt(int column, int row) {
		return model.getFontAt(column, row);
	}

	public void captureStdOut() {
		PrintStream ps = new PrintStream(System.out) {
			public void println(String x) {
				writeln(x);
			}
		};

		System.setOut(ps);
	}

	public void write (char c) {
		if (c=='\t') {
			for (int i=1; i<=TAB_SPACES; i++) {
				innerWrite(new BufferCell(' '));
			}
		}
		else innerWrite(new BufferCell(c));
	}
	
	private void innerWrite(BufferCell bc) {
		bc.innit(this.currentBackground, this.currentForeground, this.currentFont);
		model.setContentsAt(cursorX, cursorY, bc);
		moveCursor(bc);
	}
	
	public void doBackspace () {
		model.setContentsAt(cursorX-1, cursorY, new BufferCell(' ', currentForeground,
				this.DEFAULT_BACKGROUND, currentFont));
		cursorX--;
		this.repaint();
	}
	
	private void moveCursor(BufferCell bc) {
		switch (bc.getContent()) {
		case '\n':
			cursorY++;
			cursorX = 0;
			break;
		default:
			cursorX++;
			if (cursorX >= model.columns) {
				cursorX = 0;
				cursorY++;
			}
			break;
		}
		
		if (cursorY==model.rows) {
			BufferCell properties = new BufferCell(' ', this.DEFAULT_BACKGROUND, this.DEFAULT_FOREGROUND, this.DEFAULT_FONT);
			model.pushUp(properties);
			cursorY--;
		}
		this.repaint();
			
	}

	public void writeln(String line) {
		write(line);
		write('\n');
	}

	public void write(String string, Color foreGround, Color backGround) {
		Color foreTemp = currentForeground;
		Color backTemp = currentBackground;
		setForeground(foreGround);
		setBackground(backGround);
		write(string);
		setForeground(foreTemp);
		setBackground(backTemp);
	}

	public void write(String string) {
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			write(c);
		}
	}

	public void fillArea(BufferCell bc, int column, int row, int width, int height) {
		model.fillArea(bc, column, row, width, height);
		repaintArea(column, row, width, height);
	}

}

