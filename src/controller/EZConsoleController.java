package controller;

import java.awt.Color;
import java.awt.Font;

import model.BufferCell;
import model.EZConsoleModel;

public class EZConsoleController {
	
	private EZConsoleModel model;
	
	public EZConsoleController (EZConsoleModel model) {
		this.model = model;
		this.model.setFont(this.model.getCurrentFont());
	}
	
	public void write(String string) {
		for (char c : string.toCharArray()) {
			this.model.writeBufferContent(new BufferCell(c));
		}
		
	}
	
	/* Getters and Setters */
	
	public EZConsoleModel getModel() {
		return this.model;
	}
	
	public void setModel(EZConsoleModel model) {
		this.model = model;
	}
	
	public void setBackgroundColor(Color c) {
		model.setCurrentBackgroundColor(c);
	}
	
	public void setContentsColor(Color c) {
		model.setCurrentContentsColor(c);
	}
	
	public void setFont(Font f) {
		model.setCurrentFont(f);
	}

}
