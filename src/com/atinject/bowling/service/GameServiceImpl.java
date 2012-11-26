package com.atinject.bowling.service;

import java.util.List;

import com.atinject.bowling.FrameType;
import com.atinject.bowling.domain.Ball;
import com.atinject.bowling.exception.DataValidationException;

/**
 * Not intended to be thread safe, please use it with care.
 */
public class GameServiceImpl implements GameService {

	private int currentThrow;
	private Ball[] balls = new Ball[21];
	private FrameType[] fTypes = new FrameType[10];
	private FrameTypeResolver fResolver;
	
	public GameServiceImpl(final FrameTypeResolver fResolver) {
		this.fResolver = fResolver;
	}
	
	public GameServiceImpl() {}

	@Override
	public void addThrows(List<Ball> balls) throws DataValidationException {
		if (balls != null) {
			this.balls = new Ball[21]; 
			for (Ball b : balls)
				addThrow(b);
		}
	}

	@Override
	public void addThrow(Ball b) throws DataValidationException {
		if (b != null) {
			try {
				this.balls[currentThrow++] = b;
			} catch (ArrayIndexOutOfBoundsException ae) {
				// ignore the error.
			}
		}
	}
	
	@Override
	public int getCurrentScore() {
		return getScoreForFrame(10);
	}
	
	@Override
	public FrameType getFrameTypeForFrame(int f) {
		try {
			return fTypes[f - 1];
		} catch (ArrayIndexOutOfBoundsException ae) {
			return FrameType.EMPTY;
		}
	}
	
	@Override
	public int getScoreForFrame(int f) {
		if (f > 10) f = 0; // out of range frame will return 0 score;
		int currentScore = 0;
		int currentBall = 0;
		Ball current = null;

		for (int i = 0; i < f; i ++) {
			int firstCurrent = currentBall;
			Ball first = balls[currentBall++];
			Ball second = balls[firstCurrent + 1];
			FrameType t = fResolver.resolveFrameType(first, second);
			fTypes[i] = t;
			switch (t) {
				case STRIKE:
					if (second != null && balls[currentBall + 1] != null) {
						int strikeOne = second.getPinDown();
						int strikeTwo = balls[currentBall + 1].getPinDown();
						currentScore += 10 + strikeOne + strikeTwo;
					} break;
				case SPARE:
					currentScore += first.getPinDown() + balls[currentBall++].getPinDown();
					current = balls[currentBall];
					// when "current" is null which means the next throw pins are not tracked yet 
					// so spare score cannot be calculated right now, hence return 0.
					currentScore = (current == null) ? 0 : (currentScore + current.getPinDown());
					break;
				case GUTTED: 
					currentScore += first.getPinDown() + balls[currentBall++].getPinDown();
					break;
				case STANDARD: 
					currentScore += first.getPinDown() + balls[currentBall++].getPinDown();
					break;
			}
		}
	    return currentScore;
	}
	
	@Override
	public int getCurrentThrowCount() {
		return currentThrow;
	}
	
	@Override
	public int getThrowPinDown(int throwCount) {
		Ball ball = null;
		try {
			ball = balls[throwCount - 1];
		} catch (ArrayIndexOutOfBoundsException ae) {
			return 0;
		}
		return ball.getPinDown();
	}
}
