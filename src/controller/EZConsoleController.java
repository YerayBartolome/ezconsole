package controller;

import model.BufferCell;
import model.EZConsoleModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class EZConsoleController implements HierarchyListener, KeyListener, ActionListener {
	public static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
	public static final Color DEFAULT_BACKGROUND = Color.BLACK;
	private static final Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 14);
	private static final int TAB_SPACES = 5;
	private static final int DEFAULT_FLICKERRATE = 200;
	private static final boolean DEFAULT_FLICKER_ON = true;
	private static final String DEFAULT_PROMPT = "> ";
	
	private boolean cursorVisible = false;
	private boolean cursorBlinkOn = false; 
	private boolean cursorInverted = false;
	
	private String prompt;
	private boolean showPrompt = true;
	
	private Timer flickerTimer;
	
	private int cursorX = 0;
	private int cursorY = 0;

	private int fontWidth;
	private int fontHeight;
	private int fontYOffset;

	private Font mainFont = null;
	private Font currentFont = null;
	private Color currentForeground = DEFAULT_FOREGROUND;
	private Color currentBackground = DEFAULT_BACKGROUND;
	
	private JComponent render;
	private EZConsoleModel model = new EZConsoleModel();
	
	private Queue<Character> inputBuffer;
	private Queue<KeyEvent> innerBuffer;
	
	public EZConsoleController(int columns, int rows) {
		setMainFont(DEFAULT_FONT);
		setFont(mainFont);
		setCursorFlicker(DEFAULT_FLICKER_ON);
		prompt = DEFAULT_PROMPT;
		inputBuffer = new ConcurrentLinkedQueue<Character>();
		innerBuffer = new ConcurrentLinkedQueue<KeyEvent>();
	}
	
	public void setRender(JComponent render) {
		this.render = render;
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
		
		if (column==this.cursorX && row==this.cursorY && width==1 && height==1 && this.cursorVisible==false) {
			return;
		}
		
		int fw = getFontWidth();
		int fh = getFontHeight();
		render.repaint(column * fw, row * fh, width * fw, height * fh);
	}

	public void init(int columns, int rows) {
		model.init(columns, rows);
		BufferCell bc = new BufferCell(' ', DEFAULT_BACKGROUND, DEFAULT_FOREGROUND, DEFAULT_FONT);
		Arrays.fill(this.model.bufferContents, bc);

		render.setPreferredSize(new Dimension(columns * fontWidth, rows * fontHeight));
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
	
	public Color getForegroundAt(int offset) {
		return model.getContentColorAt(offset);
	}

	public Color getForegroundAt(int column, int row) {
		return model.getContentColorAt(column, row);
	}

	public Color getBackgroundAt(int offset) {
		return model.getBackgroundAt(offset);
	}
	
	public Color getBackgroundAt(int column, int row) {
		return model.getBackgroundAt(column, row);
	}

	public Font getFontAt(int offset) {
		return model.getFontAt(offset);
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
		render.repaint();
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
		render.repaint();
			
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
	
	public void setPrompt(String str) {
		this.prompt = str;
	}
	
	public void setShowPrompt(boolean show) {
		this.showPrompt = show;
	}

	public void fillArea(BufferCell bc, int column, int row, int width, int height) {
		model.fillArea(bc, column, row, width, height);
		repaintArea(column, row, width, height);
	}

	public void setCursorVisible(boolean visible) {
		this.cursorVisible = visible;		
	}

	public char[] bufferContentsToCharArray() {
		return model.bufferContentsToCharArray();
	}

	public int getFontYOffset() {
		return this.fontYOffset;
	}

	public boolean getCursorVisible() {
		return this.cursorVisible;
	}

	public boolean getCursorBlinkOn() {
		return this.cursorBlinkOn;
	}

	public boolean getCursorInverted() {
		return this.cursorInverted;
	}
	
	private void stopBlinking() {
		if (flickerTimer != null) {
			flickerTimer.stop();
			cursorInverted = true;
		}
	}

	private void startBlinking() {
		getTimer().start();
	}

	public void setCursorFlicker(boolean blink) {
		if (blink) {
			cursorBlinkOn = true;
			startBlinking();
		} else {
			cursorBlinkOn = false;
			stopBlinking();
		}
	}

	public void setBlinkDelay(int millis) {
		getTimer().setDelay(millis);
	}

	private Timer getTimer() {
		if (flickerTimer == null) {
			flickerTimer = new Timer(DEFAULT_FLICKERRATE, new TimerAction());
			flickerTimer.setRepeats(true);
			if (cursorBlinkOn) {
				startBlinking();
			}
		}
		return flickerTimer;
	}
	
	private class TimerAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (cursorBlinkOn && render.isShowing()) {
				cursorInverted = !cursorInverted;
				repaintArea(getCursorX(), getCursorY(), 1, 1);
			} else {
				stopBlinking();
			}
		}
	}

	@Override
	public void hierarchyChanged(HierarchyEvent e) {
		if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
			if (render.isShowing()) {
				startBlinking();
			} else {
				stopBlinking();
			}
		}
	}

	public boolean keyAvailable() {
		return !innerBuffer.isEmpty();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(render, 
                "EZConsole v1.0 by Y. Bartolomé (2021-2022) \n Contains code from JConsole v1.0c by E. Sesa (2016-17) and Swing Console by M. Anderson found at https://github.com/mikera/swing-console",
                "About & Credits", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		inputBuffer.add(e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		innerBuffer.add(e);		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Do nothing
	}

	public void clearBuffer() {
		try {
			EventQueue.invokeAndWait(new Runnable () {public void run () {
		innerBuffer.clear(); 
		inputBuffer.clear();
			}});
		}
		catch(Exception e){}
	}

	public KeyEvent readKey(boolean hide) {
		if(showPrompt) write(prompt);
		KeyEvent result = innerBuffer.poll();
		while (result==null) {
			result = innerBuffer.poll();
		}
		if (!hide && writable(result.getKeyCode())) write(result.getKeyChar());
		clearBuffer();
		return result;
	}
	
	private static boolean writable (int keyCode) {
		return KeyEvent.getKeyText(keyCode).length()==1
				|| Character.isLetterOrDigit(keyCode);
	}

	public char readChar() {
		String input = this.readLine();
		if (input.length()>0) return input.charAt(0);
		else return this.readChar();
	}
	
	private char innerReadChar () {
		Character result = inputBuffer.poll();
		while (result==null) {
			result = inputBuffer.poll();
		}
		return result.charValue(); 
	}

	public String readLine() {
		if(showPrompt) write(prompt);
		inputBuffer.clear();
		String result = "";
		char c = this.innerReadChar();
		while (c!='\n') {
			if(c=='\b') {
				if (result.length()>0 && getCursorX()>0) {
					doBackspace();
					result = result.substring(0,result.length()-1);
				}
			}
			else {
				write(c);
				result = result+c;
			}
			c = this.innerReadChar();
		}
		innerBuffer.clear();
		write(c);
		return result;
	}

	public int readInt() {
		String input=null;
		try {
			input = this.readLine();
			if (input.length()==0) return readInt();
			return Integer.parseInt(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(render, 
					                      "Incorrect input: "+input+". program will terminate", 
					                       "Incorrect Input", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return 0;
	}

	public long readLong() {
		String input=null;
		try {
			input = this.readLine();
			if (input.length()==0) return readLong();
			return Long.parseLong(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(render, 
					                      "Incorrect input: "+input+". program will terminate", 
					                       "Incorrect Input", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return 0;
	}

	public short readShort() {
		String input=null;
		try {
			input = this.readLine();
			if (input.length()==0) return readShort();
			return Short.parseShort(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(render, 
					                      "Incorrect input: "+input+". program will terminate", 
					                       "Incorrect Input", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return 0;
	}

	public double readDouble() {
		String input=null;
		try {
			input = this.readLine();
			if (input.length()==0) return readDouble();
			return Double.parseDouble(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(render, 
					                      "Incorrect input: "+input+". program will terminate", 
					                       "Incorrect Input", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return 0;
	}

	public float readFloat() {
		String input=null;
		try {
			input = this.readLine();
			if (input.length()==0) return readFloat();
			return Float.parseFloat(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(render, 
					                      "Incorrect input: "+input+". program will terminate", 
					                       "Incorrect Input", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return 0;
	}

	public byte readByte() {
		String input=null;
		try {
			input = this.readLine();
			if (input.length()==0) return readByte();
			return Byte.parseByte(input);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(render, 
					                      "Incorrect input: "+input+". program will terminate", 
					                       "Incorrect Input", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return 0;
	}
	

}

