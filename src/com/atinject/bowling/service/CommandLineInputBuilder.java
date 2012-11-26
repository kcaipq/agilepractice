package com.atinject.bowling.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.atinject.bowling.FrameType;
import com.atinject.bowling.domain.Ball;
import com.atinject.bowling.domain.Game;
import com.atinject.bowling.domain.Player;
import com.atinject.bowling.domain.Team;

/**
 * Single threaded UI that interacts with user's input and outputs the final results.
 * 
 * @author kcai
 *
 */
public class CommandLineInputBuilder implements InputBuilder {

	private static Scanner console = new Scanner(System.in);
	private static List<Team> teams = new ArrayList<Team>();
	private static InputValidator inputValidator = new InputValidatorImpl();
	private static PlayerGameRegisteryFactory registery = PlayerGameRegisteryFactory.getInstance();
	
	
	@Override
	public Game buildInput() {
		return null;
	}

	/*
	 * @see com.atinject.bowling.service.InputBuilder#buildResultsForDisplay()
	 */
	@Override
	public void buildResultsForDisplay() {
		// Teams
		paintTeamDataInput();
		// Players
		paintPlayerDataInput();
		// Games
		paintGameDataInput();
		// Scores
		paintFinalScores();
		// close resource
		console.close();
	}
	
	/**
	 * Command line interaction for getting input from user for the teams.
	 */
	private static void paintTeamDataInput() {
		System.out.println("############## Teams Setup ############## ");
		while (true) {
			try {
				System.out.print("Please create a team for Bowling Game: ");
				String teamName = console.nextLine();
				if (!inputValidator.validateTeamName(teamName)) {
					System.out.print("Team name is empty! Please try again.");
					continue;
				}
				Team t = new Team(teamName);
				teams.add(t);
				System.out.print("Add another? - [Y]es, [N]o: ");
				String i = console.nextLine();
				if (!inputValidator.validateYesOrNo(i))
					break;
			} catch (Exception e) {
				System.out.print("Invalid input! Please try again.");
				continue;
			}
		}
		System.out.print("You have created teams: ");
		int count = 0;
		for (Team t : teams) {
			if (count != 0) System.out.print("; ");
			System.out.print("[" + t.getName() + "]");
			count ++;
		}
	}
	
	/**
	 * Command line interaction for getting input from user for the players.
	 */
	private static void paintPlayerDataInput() {
		System.out.println("");
		System.out.println("");
		System.out.println("############## Team Players #############");
		for (Team t : teams) {
			List<Player> players = new ArrayList<Player>();
			int count = 1;
			while (true) {
				try {
					System.out.print("Please add player"+ count +" to team [" + t.getName() + "]: ");
					String player = console.nextLine();
					Player p = new Player(player);
					if (!inputValidator.validatePlayerName(p)) {
						System.out.println("Player has registered! Please try again.");
						continue;
					}
					registery.registerGame(p, null); // register a game for a player in memory.
					players.add(p);
					count ++;
					System.out.print("Add another? - [Y]es, [N]o: ");
					String i = console.nextLine();
					if (!inputValidator.validateYesOrNo(i)) 
						break;
				} catch (Exception e) {
					System.out.print("Invalid input! Please try again.");
					continue;
				}
			}
			t.setPlayers(players);
			count = 0;
			System.out.print("You have created players for team ["+ t.getName() +"]: ");
			for (Player p : players) {
				if (count != 0) System.out.print("; ");
				System.out.print("[" + p.getName() + "]");
				count ++;
			}
			System.out.println("");
		}
	}
	
	/**
	 * Command line interaction for getting input from user for the games. 
	 * In here the scoring starts being tracked, each player holds a {@link GameService} in memory.
	 */
	private static void paintGameDataInput(){
		System.out.println("");
		System.out.println("");
		System.out.println("############### Game Start ##############");
		for (Team t : teams) {
			List<Player> ps = t.getPlayers();
			int throwCount = 0;
			for (int f = 1; f <= 10; f ++) {
				for (Player p : ps) {
					int firstScore = paintFrameThrows(p, f, 1, throwCount++);
					if (10 != f && 10 == firstScore) continue; // this play has got a strike, move to next player.
					int secondScore = paintFrameThrows(p, f, 2, throwCount++);
					if (10 == f && (firstScore == 10 || (firstScore + secondScore) == 10))
						paintFrameThrows(p, f, 3, throwCount++);
				}
				paintFrameScores(ps, f);
				System.out.println("");
			}
		}
	}
	
	/**
	 * This method is responsible for gathering score input, score validation and add sore to service for processing.
	 * 
	 * @param p the {@link Player}
	 * @param frame  the current frame number
	 * @param ball the ball number in a frame. i.e. 1, 2 or 3
	 * @param throwCount a unique identifier for {@link Ball}
	 * @return the valid scoring for a throw.
	 */
	private static int paintFrameThrows(Player p, int frame, int ball, int throwCount) {
		GameService gameService = registery.getGameForPlayer(p);
		while (true) {
			System.out.print("Player: [" + p.getName() + "], Frame " + frame + " - Ball "+ ball +": ");
			try {
				String input = console.nextLine();
				int i = Integer.parseInt(input);
				if (!inputValidator.validatePinDownForThrow(i)) {
					System.out.println("Invalid ball pins! Please try again within range 1 - 10 inclusive.");
					continue;
				}
				if (ball != 1 && 10 != frame && !inputValidator.validatePinDownForFrame(p, i)) {
					System.out.println("Invalid ball pins! The sum with previous throw is greater than 10.");
					continue;
				}
				throwCount++;
				gameService.addThrow(new Ball(throwCount, i));
				return i;
			} catch (Exception e) {
				System.out.println("Invalid ball pins! Only intergers are accepted.");
				continue;
			}
		}
	}
	
	/**
	 * Use This method to interact the command for displaying scoring information for each frame.
	 * @param ps a list of {@link Player}
	 * @param frame the current frame number.
	 */
	private static void paintFrameScores(List<Player> ps, int frame) {
		for (Player p : ps) {
			GameService gameService = registery.getGameForPlayer(p);
			int thisFrameScore = gameService.getScoreForFrame(frame);
			FrameType thisType = gameService.getFrameTypeForFrame(frame);
			FrameType previousType = gameService.getFrameTypeForFrame(frame - 1);
			
			if (previousType == FrameType.SPARE || previousType == FrameType.STRIKE) {
				int previousScore = gameService.getScoreForFrame(frame - 1);
				System.out.println("Player: [" + p.getName() + "], Frame " + (frame - 1) + " - SCORE: " + previousScore); 
			}
			
			System.out.print("Player: [" + p.getName() + "], Frame " + frame + " - SCORE: "); 
			if (frame != 10 && thisType == FrameType.STRIKE) System.out.print("|X|");
			else if (frame != 10 &&  thisType == FrameType.SPARE) System.out.print("|/|");
			else System.out.print(thisFrameScore);
			System.out.println("");
		}
	}
	
	/**
	 * Paint the final game results to the command line.
	 */
	private static void paintFinalScores() {
		System.out.println("");
		System.out.println("");
		System.out.println("############### Game Results ##############");
		for (Team t : teams) {
			int total = 0;
			int topScore = 0;
			Player topScorer = null;
			for (Player p : t.getPlayers()) {
				int score = registery.getGameForPlayer(p).getCurrentScore();
				p.setScore(score);
				if (score > topScore) {
					topScore = score;
					topScorer = p;
				}
				System.out.print("Player [" + p.getName() + "]'s total score after 10 frames is : ");
				
				total += score;
				System.out.println(score);
			}
			String topScorerTxt = "";
			for (Player pl : t.getPlayers()) {
				if (pl.getScore() == topScorer.getScore()) {
					topScorerTxt += "[" + pl.getName() + "] ";
				}
			}
			System.out.print("Player " + topScorerTxt + " has the highest score in team ["+ t.getName() +"]: " + topScorer.getScore());
			System.out.println("");
			System.out.print("Team [" + t.getName() + "]'s total players' score after 10 frames is : ");
			System.out.println(total);
		}
	}
}
