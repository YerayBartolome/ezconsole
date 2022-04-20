package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.BufferCell;
import model.EZConsoleModel;
import view.EZConsoleView;

public class EZConsoleController implements ActionListener {
	
	private EZConsoleModel model;
	private EZConsoleView view;
	
	public EZConsoleController (EZConsoleModel model, EZConsoleView view) {
		this.model = model;
		this.view = view;
		this.updateViewParams();
		this.setShellVisible(true);
		this.model.setCursorX(-1);
		this.model.setCursorY(0);
		this.print(' ');
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateViewParams() {
		view.setShellSize(model.getShellSizeX(), model.getShellSizeY());
		view.setBufferSize(model.getBufferSizeX(), model.getBufferSizeY());
		view.setShellTitle(model.getShellTitle());
	}
	
	public void setShellVisible(boolean visible) {
		view.setShellVisible(visible);
	}
	
	public void print(Object o) {
		String s = o.toString();
		view.updateViewCursor(model.getCursorX(), model.getCursorY());
		for (char c : s.toCharArray()) {
			BufferCell bc = new BufferCell(c);
			model.addBufferContents(bc);		
		}
		view.updateViewContent(model.getBufferContents(), model.getBufferSizeX(), model.getBufferSizeY());
		view.repaint();	
	}
	
	public void println(Object o) {
		this.print(o);
		this.print('\n');
	}

	public void setBackgroundColor(Color c) {
		model.setCurrentBackgroundColor(c);
	}
	
	public void setContentsColor(Color c) {
		model.setCurrentContentsColor(c);
	}

}
