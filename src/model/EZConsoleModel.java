package model;

import java.awt.Color;
import java.awt.Font;

/**
 * Class used for storing console data
 * 
 * * Original Author: 
 * @author Mike
 * 
 * Modified by:
 * @author Yeray Bartolomé
 * 
 * New Features:
 * 	- This former ConsoleData acts now as the model layer
 *    of the MVC programming architecture.
 *  - A new BufferCell data structure class to encapsulate
 *    each cell properties and contents.
 */
public class EZConsoleModel {
	private int capacity = 0;
	public int rows;
	public int columns;
	public BufferCell[] bufferContents;

	public EZConsoleModel() {
		// create empty console data
	}

	private void ensureCapacity(int minCapacity) {
		if (this.capacity >= minCapacity)
			return;

		BufferCell[] newContents = new BufferCell[minCapacity];

		int size = rows * columns;
		if (size > 0) {
			System.arraycopy(this.bufferContents, size, newContents, 0, size);
		}

		this.bufferContents = newContents;
		capacity = minCapacity;
	}

	public void init(int columns, int rows) {
		ensureCapacity(rows * columns);
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * Sets a single character position
	 */
	public void setContentsAt(int column, int row, BufferCell newContent) {
		int pos = column + row * columns;
		
		this.bufferContents[pos] = newContent;
	}
	
	public void pushUp (BufferCell properties) {
		int pos; 
		// agafem les files des de la 1 (la segona pq numerem a partir de 0)
		// fins a la darrera i movem el contingut a fila-1 (pujar cap amunt)
		for (int row=1; row<rows; row++) {
			for (int col=0; col<columns; col++) {
				pos = col + row * columns;
				setContentsAt(col,row-1, this.bufferContents[pos]);
			}
		}
		// i ara només ens queda de fer neteja de la darrera fila 
		for (int col=0; col<columns; col++) {
			setContentsAt(col, rows-1, properties);
		}
	}
	
	public char[] bufferContentsToCharArray() {
		char[] contents = new char[this.capacity];
		for (int i = 0; i < this.capacity; i++) {
			contents[i] = this.bufferContents[i].getContent();
		}
		return contents;
	}

	public BufferCell getBCAt(int column, int row) {
		int offset = column + row * columns;
		return this.getBCAt(offset);
	}
	
	public BufferCell getBCAt(int offset) {
		return this.bufferContents[offset];
	}

	public Color getContentColorAt(int column, int row) {
		int offset = column + row * columns;
		return this.getContentColorAt(offset);
	}
	
	public Color getContentColorAt(int offset) {
		return this.bufferContents[offset].getContentColor();
	}

	public Color getBackgroundAt(int column, int row) {
		int offset = column + row * columns;
		return this.getBackgroundAt(offset);
	}
	public Color getBackgroundAt(int offset) {
		return this.bufferContents[offset].getBackgroundColor();
	}

	public Font getFontAt(int column, int row) {
		int offset = column + row * columns;
		return this.getFontAt(offset);
	}
	
	public Font getFontAt(int offset) {
		return this.bufferContents[offset].getFont();
	}

	public void fillArea(BufferCell bc, int column,	int row, int width, int height) {
		for (int q = Math.max(0, row); q < Math.min(row + height, rows); q++) {
			for (int p = Math.max(0, column); p < Math.min(column + width, columns); p++) {
				int offset = p + q * columns;
				this.bufferContents[offset] = bc;
			}
		}
	}
}
