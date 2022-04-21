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
	
	public BufferCell(char content, Color backgroundColor, Color contentColor, Font font) {
		this.content = content;
		this.innit(backgroundColor, contentColor, font);
	}
	
	public void innit(Color backgroundColor, Color contentColor, Font font) {
		this.backgroundColor = backgroundColor;
		this.contentColor = contentColor;
		this.font = font;
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
	
	public String toString() {
		return "BufferCell { char: "+this.content+", bC: "+this.backgroundColor+" cC: "+this.contentColor+" }";
	}
	
}
