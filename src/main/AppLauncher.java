package main;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import functionalities.EZConsole;

public class AppLauncher {

	public static void main(String[] args) {
		
		EZConsole console = new EZConsole(200, 50, "EZConsole features DEMO");
		
		console.setCursorVisible(true);
		
        try {
			Files.lines(Paths.get("src/resources/EZConsole_ASCII_Logo.txt"), StandardCharsets.UTF_8).forEach(console::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		console.println();
		
		console.println("Welcome to the EZConsole features DEMO. This is an example project developed using EZConsole to showcase its functionalitites."
				+ "\nThis file/executable shall be replaced by your own main and/or EZConsole class usage.");
		console.println();
		console.print("EZConsole is an intuitive tool to develop Java based TUI's."
				+ "\nIt uses/it is based on the Javax Swing framework.");
		
		console.println("\n");
		
		console.println("MAIN FEATURES");
		console.println("—————————————");
		
		console.print("Is this content?");
		console.print('\n');
		console.print("Is this content?");
		console.println();
		console.println("Is this content?");
		
		console.setBackgroundColor(Color.BLUE);
		
		console.println("Is this content?");
		
		console.setContentsColor(Color.RED);
		
		console.print("Is this content?");
		
		console.println(console.readString());
	}

}
