package model;

import java.awt.Color;
import java.awt.Font;

public class BufferCell {

	private char content;
	private Font font;
	private Color backgroundColor;
	private Color contentColor;
	
	public BufferCell(char content) {
		this.content = content;
	}
	
	public char getContent() {
		return content;
	}
	public void setContent(char content) {
		this.content = content;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Color getContentColor() {
		return contentColor;
	}
	public void setContentColor(Color contentColor) {
		this.contentColor = contentColor;
	}
	
	
}
