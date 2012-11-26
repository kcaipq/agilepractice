package com.atinject.bowling.service;

import java.util.List;

import com.atinject.bowling.domain.Ball;
import com.atinject.bowling.domain.Player;

public class InputValidatorImpl implements InputValidator {
	
	private static PlayerGameRegisteryFactory registery = PlayerGameRegisteryFactory.getInstance();

	@Override
	public boolean validatePlayerName(Player player) {
		return registery.getGameForPlayer(player) == null;
	}

	@Override
	public boolean validateTeamName(String teamName) {
		return teamName != null && !"".equals(teamName);
	}

	@Override
	public boolean validatePinDownForFrame(Player player, final int secondThrow) {
		GameService game = registery.getGameForPlayer(player);
		int count = game.getCurrentThrowCount();
		int previousThrowScore = game.getThrowPinDown(count); // get the pins from the most recent throw.
		int frameScore = previousThrowScore != 10 ? secondThrow + previousThrowScore : secondThrow;

		return validatePinDownForThrow(frameScore);
	}
	
	@Override
	public boolean validateThrows(List<Ball> balls) {
		int size = balls.size();
		if (size > 21) return false;
		for (int j = 0; j < size; j ++) {
			Ball ball = balls.get(j);
			if (10 == ball.getPinDown()) continue;
			int fScore = 0;
			try {
				Ball ballNext = balls.get(j + 1);
				fScore = ball.getPinDown() + ballNext.getPinDown();
			} catch (Exception ex) {
				fScore = ball.getPinDown();
			}
			if (fScore > 10 || fScore < 0) {
				return false;
			}
			j = j + 1;
		}
		return true;
	}

	@Override
	public boolean validatePinDownForThrow(int pins) {
		// range 0 - 10
		return pins <= 10 && pins >= 0;
	}
	
	@Override
	public boolean validateYesOrNo(String choice) {
		return "Yes".equalsIgnoreCase(choice) || "Y".equalsIgnoreCase(choice);
	}
	
}
