package main;

import java.awt.Color;

public class AppLauncher {

	public static void main(String[] args) {
		
		EZConsole console = new EZConsole(60, 20);
		
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
		
		console.println("Is this content?");
	}

}
