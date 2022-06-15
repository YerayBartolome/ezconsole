package main;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
		toggleView(console);
	}
	
	static void toggleView(EZConsole console) {
		console.println();
		console.println("TABLE OF CONTENTS", Color.CYAN);
		console.println("—————————————————", Color.CYAN);
		console.println("Where do you want to go?");
		console.println();
		console.println("1. Main Menu");
		console.println("2. Console Creation");
		console.println("3. Console Settings");
		console.println("4. Content Displaying");
		console.println("5. Input Reading");
		console.println("6. Exit to Desktop");
		console.println();
		console.println("What do you want to see? Type the index of your desired destination.");
		int index = -1;
		do {
			index = Character.getNumericValue(console.readChar());
			if(index < 1 || index > 6) console.println("Invalid index, try again:", Color.RED);
		} while (index < 1 || index > 6);
	
		console.clear();
		
		switch(index) {
		case 1: mainMenu(console); break;
		case 2: consoleCreation(console); break;
		case 3: consoleSettings(console); break;
		case 4: consoleDisplay(console); break;
		case 5: consoleInputReading(console); break;
		case 6: System.exit(0);
		}
	}
	
	static void consoleCreation(EZConsole console) {
		console.println("CONSOLE CREATION", Color.GREEN);
		console.println("————————————————", Color.GREEN);
		console.println();
		console.println("To use the EZConsole, you must create a new instance for each new console you need.");
		console.println();
		console.print("EZConsole()");
		console.print(" ———► ");
		console.println("Creates the console with default settings.");
		console.print("\nEZConsole(String title)");
		console.print(" ———► ");
		console.println("Creates the console provided its title by String.");
		console.print("\nEZConsole(int x, int y)");
		console.print(" ———► ");
		console.println("Creates the console provided its with and length.");
		console.print("\nEZConsole(int x, int y, String title)");
		console.print(" ———► ");
		console.println("Creates the console provided its with, length and title.");
		toggleView(console);
	}
	
	static void consoleSettings(EZConsole console) {
		console.println("CONSOLE SETTINGS", Color.GREEN);
		console.println("————————————————", Color.GREEN);
		console.println();
		Object[][] fs = getFeatures(4, 13);
		String[] methods = {"void setCursorVisible(boolean visible)", "void setCursorPosition (int w, int h)", "void showPrompt(boolean show)", "void setPrompt(String newPrompt)", "setBackgroundColor(Color c)", "void setContentsColor(Color c)", "void resetColor ()", "int getRows()", "int getColumns()"};
		for(int i = 0; i < methods.length; i++) {
			console.print(methods[i]);
			console.print(" ———► ");
			console.println(fs[i][1]);			
			console.println();
		}
		toggleView(console);
	}
	
	static void consoleDisplay(EZConsole console) {
		console.println("CONSOLE DISPLAYING", Color.GREEN);
		console.println("——————————————————", Color.GREEN);
		console.println();
		Object[][] fs = getFeatures(13, 20);
		String[] methods = {"void print(Object o)", "void print(Object o, Color color)", "void println()", "void println(Object o)", "void println(Object o, Color c)", "void printTable(Object[][] o, int[] cellLength)", "void clear()"};
		for(int i = 0; i < methods.length; i++) {
			console.print(methods[i]);
			console.print(" ———► ");
			console.println(fs[i][1]);			
			console.println();
		}
		toggleView(console);
	}

	static void consoleInputReading(EZConsole console) {
		console.println("CONSOLE INPUT READING", Color.GREEN);
		console.println("—————————————————————", Color.GREEN);
		console.println();
		Object[][] fs = getFeatures(20, 32);
		String[] methods = {"boolean keyAvailable()", "void clearBuffer()", "KeyEvent readKey()", "KeyEvent readKey(boolean hide)", "char readChar()", "String readString()", "int readInt()", "short readShort()", "long readLong()", "double readDouble()", "float readFloat()", "byte readByte()"};
		for(int i = 0; i < methods.length; i++) {
			console.print(methods[i]);
			console.print(" ———► ");
			console.println(fs[i][1]);			
			console.println();
		}
		toggleView(console);
	}
	
	static Object[][] getFeatures(int first, int last){
		Object[][] features = null;
		Object[] explanations;
		try {
            Class classobj = EZConsole.class;
            Method[] methods = classobj.getMethods();
            features = new String[last-first][2];
            explanations = Files.lines(Paths.get("src/resources/EZConsole_Features.txt"), StandardCharsets.UTF_8).toArray();
            for (int i = first; i < last; i++) {
               features[i-first][0] = methods[i].toString();
               features[i-first][1] = explanations[i];
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return features;
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
