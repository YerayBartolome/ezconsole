package main;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import functionalities.EZConsole;

public class AppLauncher {

	public static void main(String[] args) {
		
		EZConsole console = new EZConsole(150, 50, "EZConsole features DEMO");
		
		console.setCursorVisible(true);

		mainMenu(console);
		
		
	}
	
	static void mainMenu(EZConsole console) {
		TimePause pause = new TimePause(1000);
		console.setContentsColor(Color.GREEN);
		TitlePrompter title = new TitlePrompter(0, console);
		title.start();
		pause.pause();
		console.resetColor();
		TimePrompter time = new TimePrompter(1, console);
		time.start();
		console.println();
		console.println("Welcome to the EZConsole features DEMO. This is an example project developed using EZConsole to showcase its functionalitites.");
		pause.pause();
		console.println("This file/executable shall be replaced by your own main and/or EZConsole class usage.");
		pause.pause();
		console.println();
		console.print("EZConsole is an intuitive tool to develop Java based TUI's.");
		pause.pause();
		console.println("It uses/it is based on the Javax Swing framework.");
		pause.pause();
		console.println("\n");
		console.println("MAIN FEATURES", Color.GREEN);
		console.println("—————————————");
		console.print("Is this content?");
		console.print('\n');
		console.print("Is this content?");
		console.println();
		console.println("YES IT IS!");
		toggleView(console);
	}
	
	static void toggleView(EZConsole console) {
		console.println();
		console.println("Where do you want to go?");
		console.println();
		console.println("1. Main Menu");
		console.println("2. Console Creation");
		console.println("3. Console Settings");
		console.println("4. Content Prompting");
		console.println("5. Input Reading");
		console.println();
		console.println("What do you want to see? Type the index of your desired destination.");
		int index = -1;
		do {
			index = Character.getNumericValue(console.readChar());
			if(index < 1 || index > 5) console.println("Invalid index, try again:", Color.RED);
		} while (index < 1 || index > 5);
		
		console.clear();
		
		switch(index) {
		case 1: mainMenu(console); break;
		case 2: consoleCreation(console); break;
		case 3: consoleSettings(console); break;
		case 4: consolePrompting(console); break;
		case 5: consoleInputReading(console); break;
		}
	}
	
	static void consoleCreation(EZConsole console) {
		
	}
	
	static void consoleSettings(EZConsole console) {
		
	}
	
	static void consolePrompting(EZConsole console) {
		
	}

	static void consoleInputReading(EZConsole console) {
		
	}
}

class TimePause {
	private long millis = 0;
	
	public TimePause(long millis) {
		this.millis = millis;
	}
	
	public void pause() {
		try {
			Thread.sleep(this.millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class TitlePrompter extends Thread {
	private EZConsole console;
	private int id;
	
	public TitlePrompter(int id, EZConsole console) {
		this.id = id;
		this.console = console;
	}
	
	public void run() {
		try {
			Files.lines(Paths.get("src/resources/EZConsole_ASCII_Logo.txt"), StandardCharsets.UTF_8).forEach(console::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class TimePrompter extends Thread {
	private EZConsole console;
	private int id;
	
	public boolean keepRunning = true;
	
	public TimePrompter(int id, EZConsole console) {
		this.id = id;
		this.console = console;
	}
	
	public void run() {
		TimePause tp = new TimePause(10000);
		while(keepRunning) {
			tp.pause();
			console.println("\nThread "+ id +" [TimePrompter]: " + java.time.LocalTime.now() + "\n");
		}
	}
}
