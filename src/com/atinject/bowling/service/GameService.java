package com.atinject.bowling.service;

import java.util.List;

import com.atinject.bowling.FrameType;
import com.atinject.bowling.domain.Ball;

/**
 * This is the handler for a Bowling game for one player. Player MUST register his/her own instance with {@link PlayerGameRegisteryFactory}
 * Each player will have an unique handler for each game at a time
 * The implementation is not intended to be implemented in a thread-safe manner, please use it with extra care.
 * 
 * @author kcai
 */
public interface GameService {

	/**
	 * Adding a throw to the containing array. The maximum number of elements it can take is 21.
	 * 9 * 2 + 3 = 21
	 * @param b {@link Ball}
	 */
	void addThrow(Ball b);
	
	/**
	 * Aggregate method that takes a list of throws. Element will be added to the containing array calling {@link #addThrow(Ball)}.
	 * @param balls A list of {@link Ball}
	 */
	void addThrows(List<Ball> balls);

	/**
	 * Use this method to calculate the most recent score up to the current throw.
	 * @return the current score inclusive current throw.
	 */
	int getCurrentScore();

	/**
	 * Use this method to calculate the current score when current frame is finished.
	 * @param i the frame number
	 * @return the current score up to current frame inclusive.
	 */
	int getScoreForFrame(int i);
	
	/**
	 * This method works out the {@link FrameType} for a given frame number.
	 * @param f the frame number.
	 * @return {@link FrameType}
	 */
	FrameType getFrameTypeForFrame(int f);
	
	/**
	 * Gets the current number of throws have taken for a player in a particular game.
	 * The maximum of throws for a game is 21.
	 * @return the current throw number.
	 */
	int getCurrentThrowCount();
	
	/**
	 * Gets the pins hit for a particular throw.
	 * If the parameter "throwCount" is out of range this method will return 0;
	 * @param throwCount the throw number
	 * @return the pints hit down in that throw.
	 */
	int getThrowPinDown(final int throwCount);

}
