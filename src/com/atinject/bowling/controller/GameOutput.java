package com.atinject.bowling.controller;

import com.atinject.bowling.exception.DataValidationException;
import com.atinject.bowling.service.CommandLineInputBuilder;
import com.atinject.bowling.service.InputBuilder;
import com.atinject.bowling.service.XMLInputBuilder;

/**
 * This is the main class the UI starts from here.
 * 
 * @author kcai
 *
 */
public class GameOutput {

	/**
	 * 0) no argument is given - run the command line input screen.
	 * 1) one argument is given - if it equals to "XML" then the XML input builder will be triggered and the screen will run using the
   	 *    default XML (./Game.xml), which sits in the same folder as the jar and this bash.
	 * 2) two arguments are given - 1. XML or something else; 2. a valid path to the XML input.
	 */
	public static void main(String args[]) {
		int size = args == null ? 0 : args.length;
		InputBuilder builder = null;
		
		switch (size) {
			case 0: 
				builder = new CommandLineInputBuilder(); break;
			case 1: 
				builder = resolveBuilder(args[0], "");
				break;
			case 2: 
				builder = resolveBuilder(args[0], args[1]);
				break;
			default: builder = resolveBuilder("Command", "");
				break;
		}
		try {
			builder.buildResultsForDisplay();
		} catch (DataValidationException de) {
			System.out.println("Error: " + de.getMessage());
		}
	}
	
	private static InputBuilder resolveBuilder(final String type, final String path) {
		InputBuilder builder = null;
		if ("XML".equalsIgnoreCase(type)) {
			builder = new XMLInputBuilder(path);
		} else {
			builder = new CommandLineInputBuilder();
		}
		return builder;
	}
}