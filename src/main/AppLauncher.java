package main;

import java.awt.Color;

import functionalities.EZConsole;

public class AppLauncher {

	public static void main(String[] args) {
		
		EZConsole console = new EZConsole(60, 25, "EZConsole features DEMO");
		
		console.setCursorVisible(true);
		
		console.print("Is this content?");
		console.print(' ');
		console.print("Is this content?");
		console.print('\t');		
		console.print("Is this content?");
		console.print('\n');
		console.print("Is this content?");
		console.println();
		console.println("Is this content?");
		
		console.setBackgroundColor(Color.BLUE);
		
		console.println("Is this content?");
		
		console.setContentsColor(Color.RED);
		
		console.print("Is this content?");
	}

}
