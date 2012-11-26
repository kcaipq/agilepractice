package com.atinject.bowling.service;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.atinject.bowling.domain.Ball;
import com.atinject.bowling.domain.Game;
import com.atinject.bowling.domain.Player;
import com.atinject.bowling.domain.Team;
import com.atinject.bowling.exception.DataValidationException;

/**
 * Reads XML input from disk and display the final results on screen.
 *
 */
public class XMLInputBuilder implements InputBuilder {

	public String INPUT_PATH = XMLInputBuilder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private static PlayerGameRegisteryFactory registery = PlayerGameRegisteryFactory.getInstance();
	private static InputValidator validator = new InputValidatorImpl();
	private static String DEFAULT_INPUT_FILE = "Game.xml";
	
	public XMLInputBuilder(String path) {
		if (path != null && !"".equals(path))
			this.INPUT_PATH = path;
		else {
			this.INPUT_PATH = INPUT_PATH.substring(0, INPUT_PATH.lastIndexOf("/")) + "/" + DEFAULT_INPUT_FILE; 
		}
	}
	
	public XMLInputBuilder() { }
	
	@Override
	public Game buildInput() {
		try {
			File file = new File(INPUT_PATH);
			JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Game game = (Game) jaxbUnmarshaller.unmarshal(file);

			return game;
		  } catch (JAXBException e) {
			 System.out.println("Invalid input xml path: " + INPUT_PATH);
			 throw new DataValidationException("Invalid XML input path.");
		  }
	}
	
	/**
	 * A bit nasty those nested loops. but I think it's OK there aren't too may data in a look though.
	 * @see com.atinject.bowling.service.InputBuilder#buildResultsForDisplay()
	 */
	@Override
	public void buildResultsForDisplay() {
		Game game = buildInput();
		List<Team> teams = game.getTeams();
		
		for (Team t: teams) {
			int total = 0;
			int topScore = 0;
			Player topScorer = null;
			for (Player p : t.getPlayers()) {
				//validate the XML input if there are invalid throws exception is thrown.
				boolean bp = validator.validatePlayerName(p);
				boolean b = validator.validateThrows(p.getBalls());
				if (!bp) {
					throw new DataValidationException("The player names are not unique in the XML data: " + p.getName());
				}
				if (!b) {
					throw new DataValidationException("The throws data in the XML is invalid: " + toString(p.getBalls()));
				}
				
				registery.registerGame(p, null);
				GameService gs = registery.getGameForPlayer(p);
				gs.addThrows(p.getBalls());
				int score = gs.getCurrentScore();
				for (int i = 1; i <=10; i ++) {
					int fScore = gs.getScoreForFrame(i);
					p.setScore(score);
					if (fScore > topScore) {
						topScore = fScore;
						topScorer = p;
					}
					System.out.println("Team: ["+ t.getName() +"] Player: [" + p.getName() + "], Frame " + i + " - SCORE: " + fScore); 
				}
				total += score;
				System.out.println("Team: ["+ t.getName() +"] Player: [" + p.getName() + "] Total Score: [" + score +"]");
				System.out.println("");
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
	
	private String toString(List<Ball> balls) {
		String cat = "";
		for (Ball b : balls) {
			cat += b.toString() + " ";
		}
		return cat;
	}
}
