package com.atinject.bowling.service;

import java.util.List;

import com.atinject.bowling.domain.Ball;
import com.atinject.bowling.domain.Player;

/**
 * Simple validator for inputs.
 * @author kcai
 *
 */
public interface InputValidator {
	
	boolean validatePlayerName(final Player player);
	
	boolean validateTeamName(final String teamName);
	
	/**
	 * This validates the second throw for a frame.
	 * @param player {@link Player} used to get the pins down for previous throw.
	 * @param currentPins the pins down for a second throw in a frame
	 * @return true if the input passes.
	 */
	boolean validatePinDownForFrame(Player player, final int currentPins);
	
	/**
	 * validate a list of throws up to 21 items as a whole in a list. If a pair
	 * is not valid then the whole list turns out to be invalid too.
	 * 
	 * @param balls a list of balls, can be maxed up to 21 items.
	 * @return true if the input passes.
	 */
	boolean validateThrows(List<Ball> balls);
	
	/**
	 * Validate a single throw pin range.
	 * @param pins the number of pins got hit down.
	 * @return true if the input passes.
	 */
	boolean validatePinDownForThrow(int pins);
	
	/**
	 * Only "Y", "y", "YES", "yes", "YeS", "yEs", "yeS", "YEs" can be accepted.
	 * Other else input will be treated as a "NO"
	 * 
	 * @param choice the inut string
	 * @return true if the input passes.
	 */
	boolean validateYesOrNo(String choice);

}
