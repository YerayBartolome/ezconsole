package main;

import java.awt.Color;

public class AppLauncher {

	public static void main(String[] args) {
		
		EZConsole console = new EZConsole();
		
		for (int i = 0; i < 10; i++) {
			console.print("Is this content");
			if(i%5 == 0) console.println(" ");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		console.print("Is this content");
		

	}

}
