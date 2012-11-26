package com.atinject.bowling.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.atinject.bowling.FrameType;
import com.atinject.bowling.domain.Ball;

public class GameServiceTest {

	private GameService game;
	private FrameTypeResolver resolver;

	@Before
	public void before() {
		resolver = new FrameTypeResolverImpl();
		game = new GameServiceImpl(resolver);
	}

	@Test
	public void testOneThrow() {
		Ball b = new Ball(1, 4);
		game.addThrow(b);
		Assert.assertEquals(0, game.getCurrentScore());
	}

	@Test
	public void testTwoThrows() {
		game.addThrow(new Ball(1, 4));
		game.addThrow(new Ball(2, 4));
		Assert.assertEquals(8, game.getCurrentScore());
	}

	@Test
	public void testMoreThrows() {
		game.addThrow(new Ball(1, 4));
		game.addThrow(new Ball(2, 5));
		game.addThrow(new Ball(3, 5));
		Assert.assertEquals(9, game.getCurrentScore());
	}

	@Test
	public void testMoreThrows1() {
		game.addThrow(new Ball(1, 4));
		game.addThrow(new Ball(2, 5));
		game.addThrow(new Ball(3, 1));
		game.addThrow(new Ball(4, 2));
		Assert.assertEquals(12, game.getCurrentScore());
	}

	@Test
	public void testAddThrowInput() {
		try {
			game.addThrow(null);
		} catch (NullPointerException npe) {
			Assert.fail("Cannot add empty or null ball in a game.");
		}
	}

	/**
	 * 22 throws are not permitted in a game. the 22nd is ignored.
	 */
	@Test
	public void testTooManyThrow() {
		game.addThrow(new Ball(1, 1));
		game.addThrow(new Ball(2, 1));
		game.addThrow(new Ball(3, 1));
		game.addThrow(new Ball(4, 1));
		game.addThrow(new Ball(5, 1));
		game.addThrow(new Ball(6, 1));
		game.addThrow(new Ball(7, 1));
		game.addThrow(new Ball(8, 1));
		game.addThrow(new Ball(9, 1));
		game.addThrow(new Ball(10, 1));
		game.addThrow(new Ball(11, 1));
		game.addThrow(new Ball(12, 1));
		game.addThrow(new Ball(13, 1));
		game.addThrow(new Ball(14, 1));
		game.addThrow(new Ball(15, 1));
		game.addThrow(new Ball(16, 1));
		game.addThrow(new Ball(17, 1));
		game.addThrow(new Ball(18, 1));
		game.addThrow(new Ball(19, 9));
		game.addThrow(new Ball(20, 1));
		game.addThrow(new Ball(21, 1));
		game.addThrow(new Ball(22, 1));
		// TODO when done with strike case come back here to get it passed..
		Assert.assertEquals(29, game.getCurrentScore());
	}

	@Test
	public void testGetScoreWithUnfinishedSecondFrame() {
		game.addThrow(new Ball(1, 4));
		game.addThrow(new Ball(2, 5));
		game.addThrow(new Ball(3, 4));
		Assert.assertEquals(9, game.getCurrentScore());
	}

	@Test
	public void testGetFrameScoreIllegalInput() {
		game.addThrow(new Ball(1, 4));
		game.addThrow(new Ball(2, 5));
		Assert.assertEquals(0, game.getScoreForFrame(0));
		Assert.assertEquals(0, game.getScoreForFrame(-1));
		Assert.assertEquals(0, game.getScoreForFrame(11));
	}

	@Test
	public void testOneFrameScoreNoStrikeSpare() {
		game.addThrow(new Ball(1, 4));
		game.addThrow(new Ball(2, 5));
		Assert.assertEquals(9, game.getScoreForFrame(1));
		Assert.assertEquals(9, game.getCurrentScore());
	}

	@Test
	public void testMoreFrameScoreNoStrikeSpare() {
		game.addThrow(new Ball(1, 4));
		game.addThrow(new Ball(2, 5));
		game.addThrow(new Ball(3, 4));
		game.addThrow(new Ball(4, 4));
		game.addThrow(new Ball(5, 1));
		game.addThrow(new Ball(6, 2));
		Assert.assertEquals(9, game.getScoreForFrame(1));
		Assert.assertEquals(17, game.getScoreForFrame(2));
		Assert.assertEquals(20, game.getScoreForFrame(3));
		Assert.assertEquals(20, game.getCurrentScore());
	}

	/**
	 * start testing frame with spare.
	 */

	@Test
	public void testOneThrowStrikeNextTwoThrowNotRecorded() {
		Ball b = new Ball(1, 10);
		game.addThrow(b);
		Assert.assertEquals(0, game.getCurrentScore());
	}

	@Test
	public void testOneThrowsStrikeNextTwoThrowRecorded() {
		game.addThrow(new Ball(1, 10));
		game.addThrow(new Ball(2, 1));
		game.addThrow(new Ball(3, 4));
		Assert.assertEquals(15, game.getScoreForFrame(1));
		Assert.assertEquals(20, game.getScoreForFrame(2));
		Assert.assertEquals(20, game.getCurrentScore());
	}

	@Test
	public void testGetFrameScoreWithSpare() {
		game.addThrow(new Ball(1, 1));
		game.addThrow(new Ball(2, 9));
		game.addThrow(new Ball(3, 3));
		Assert.assertEquals(13, game.getScoreForFrame(1));
		Assert.assertEquals(13, game.getCurrentScore());
	}

	@Test
	public void testGetFrameScoreMixedWithSpare() {
		game.addThrow(new Ball(1, 1));
		game.addThrow(new Ball(2, 3));

		game.addThrow(new Ball(3, 9));
		game.addThrow(new Ball(4, 1));

		game.addThrow(new Ball(5, 1));
		game.addThrow(new Ball(6, 7));

		Assert.assertEquals(4, game.getScoreForFrame(1));
		Assert.assertEquals(4 + 10 + 1, game.getScoreForFrame(2));
		Assert.assertEquals(8 + 15, game.getScoreForFrame(3));
		Assert.assertEquals(8 + 15, game.getCurrentScore());
	}

	/**
	 * Test strike.
	 */
	@Test
	public void testTwoThrowsSpareNextThrowNotRecorded() {
		game.addThrow(new Ball(1, 0));
		game.addThrow(new Ball(2, 10));
		Assert.assertEquals(0, game.getCurrentScore());
	}

	@Test
	public void testTwoThrowsSpareNextThrowRecorded() {
		game.addThrow(new Ball(1, 0));
		game.addThrow(new Ball(2, 10));
		game.addThrow(new Ball(3, 10));
		Assert.assertEquals(20, game.getCurrentScore());
		Assert.assertEquals(20, game.getScoreForFrame(1));
		Assert.assertEquals(20, game.getScoreForFrame(2));
	}

	@Test
	public void testSimpleOneStrike() {
		game.addThrow(new Ball(1, 10));
		game.addThrow(new Ball(2, 4));
		game.addThrow(new Ball(3, 5));
		Assert.assertEquals(19, game.getScoreForFrame(1));
		Assert.assertEquals(28, game.getScoreForFrame(2));
		Assert.assertEquals(28, game.getCurrentScore());
	}

	@Test
	public void testFrameScoreMixedWithStrike() {
		game.addThrow(new Ball(1, 1));
		game.addThrow(new Ball(2, 2));
		game.addThrow(new Ball(3, 10));
		game.addThrow(new Ball(4, 5));
		game.addThrow(new Ball(5, 4));
		Assert.assertEquals(3, game.getScoreForFrame(1));
		Assert.assertEquals(22, game.getScoreForFrame(2));
		Assert.assertEquals(31, game.getScoreForFrame(3));
		Assert.assertEquals(31, game.getCurrentScore());
	}

	@Test
	public void testFrameScoreMixedWithStrikeAndSpare() {
		game.addThrow(new Ball(1, 1));
		game.addThrow(new Ball(2, 2));
		game.addThrow(new Ball(3, 10));
		game.addThrow(new Ball(4, 5));
		game.addThrow(new Ball(5, 5));
		game.addThrow(new Ball(6, 4));
		Assert.assertEquals(3, game.getScoreForFrame(1));
		Assert.assertEquals(23, game.getScoreForFrame(2));
		Assert.assertEquals(37, game.getScoreForFrame(3));
		Assert.assertEquals(37, game.getCurrentScore());
	}

	@Test
	public void testAllStrike10Frames() {
		for (int i = 0; i < 15; i++) { // only 12 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 10));
		}
		Assert.assertEquals(300, game.getScoreForFrame(10));
		Assert.assertEquals(300, game.getCurrentScore());
	}

	@Test
	public void testAllSpare10Frames() {
		for (int i = 0; i < 25; i++) { // only 21 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 5));
		}
		Assert.assertEquals(150, game.getScoreForFrame(10));
		Assert.assertEquals(150, game.getCurrentScore());
	}

	@Test
	public void testAllGutted10Frames() {
		for (int i = 0; i < 25; i++) { // only 21 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 0));
		}
		Assert.assertEquals(0, game.getScoreForFrame(10));
		Assert.assertEquals(0, game.getCurrentScore());
	}

	@Test
	public void test9GuttedFrames1Strike() {
		for (int i = 0; i < 18; i++) { // only 18 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 0));
		}
		game.addThrow(new Ball(19, 10));
		game.addThrow(new Ball(20, 1));
		game.addThrow(new Ball(21, 4));
		Assert.assertEquals(15, game.getScoreForFrame(10));
		Assert.assertEquals(15, game.getCurrentScore());
	}

	@Test
	public void test9GuttedFrames1Spare() {
		for (int i = 0; i < 18; i++) { // only 18 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 0));
		}
		game.addThrow(new Ball(19, 9));
		game.addThrow(new Ball(20, 1));
		game.addThrow(new Ball(21, 4));
		Assert.assertEquals(14, game.getScoreForFrame(10));
		Assert.assertEquals(14, game.getCurrentScore());
	}

	@Test
	public void test9GuttedFrames1SpareAndBonusStrike() {
		for (int i = 0; i < 18; i++) { // only 18 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 0));
		}
		game.addThrow(new Ball(19, 9));
		game.addThrow(new Ball(20, 1));
		game.addThrow(new Ball(21, 10));
		Assert.assertEquals(20, game.getScoreForFrame(10));
		Assert.assertEquals(20, game.getCurrentScore());
	}

	@Test
	public void testAllFramesSkipeButBonusOneStrike() {
		for (int i = 0; i < 10; i++) { // only 19 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 10));
		}
		game.addThrow(new Ball(11, 10));
		game.addThrow(new Ball(12, 9));
		Assert.assertEquals(299, game.getScoreForFrame(10));
		Assert.assertEquals(299, game.getCurrentScore());
	}

	@Test
	public void testAllFramesSkipeButBonusNoStrike() {
		for (int i = 0; i < 10; i++) { // only 19 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 10));
		}
		game.addThrow(new Ball(11, 1));
		game.addThrow(new Ball(12, 8));
		Assert.assertEquals(280, game.getScoreForFrame(10));
		Assert.assertEquals(280, game.getCurrentScore());
	}

	@Test
	public void testAllFramesSkipeButBonusSpare() {
		for (int i = 0; i < 10; i++) { // only 19 throws are calculated, the
										// rest is ignored.
			game.addThrow(new Ball(i + 1, 10));
		}
		game.addThrow(new Ball(11, 2));
		game.addThrow(new Ball(12, 8));
		Assert.assertEquals(282, game.getScoreForFrame(10));
		Assert.assertEquals(282, game.getCurrentScore());
	}

	@Test
	public void test9FramesSkipe1FrameSpare() {
		for (int i = 0; i < 9; i++) { // only 19 throws are calculated, the rest
										// is ignored.
			game.addThrow(new Ball(i + 1, 10));
		}
		game.addThrow(new Ball(10, 9));
		game.addThrow(new Ball(11, 1));
		game.addThrow(new Ball(12, 1));
		Assert.assertEquals(270, game.getScoreForFrame(10));
		Assert.assertEquals(270, game.getCurrentScore());
	}
	
	@Test
	public void testAddThrowsFullGame() {
		List<Ball> balls = new ArrayList<Ball>(21);
		balls.add(new Ball(1, 1));
		balls.add(new Ball(2, 4));
		balls.add(new Ball(3, 4));
		balls.add(new Ball(4, 5));
		balls.add(new Ball(5, 6));
		balls.add(new Ball(6, 4));
		balls.add(new Ball(7, 5));
		balls.add(new Ball(8, 5));
		balls.add(new Ball(9, 10));
		balls.add(new Ball(10, 0));
		balls.add(new Ball(11, 1));
		balls.add(new Ball(12, 7));
		balls.add(new Ball(13, 3));
		balls.add(new Ball(14, 6));
		balls.add(new Ball(15, 4));
		balls.add(new Ball(16, 10));
		balls.add(new Ball(17, 2));
		balls.add(new Ball(18, 8));
		balls.add(new Ball(19, 7));
		
		game.addThrows(balls);
		Assert.assertEquals(134, game.getScoreForFrame(10));
		Assert.assertEquals(134, game.getCurrentScore());
	}
	
	@Test
	public void testGetFrameType() {
		FrameType t = game.getFrameTypeForFrame(11);
		Assert.assertEquals(FrameType.EMPTY, t);
		t = game.getFrameTypeForFrame(-1);
		Assert.assertEquals(FrameType.EMPTY, t);
	}
	
	@Test
	public void testGetThrowCountEmpty() {
		Assert.assertEquals(0, game.getCurrentThrowCount());
	}
	
	@Test
	public void testGetThrowCount() {
		game.addThrow(new Ball(1, 10));
		game.addThrow(new Ball(2, 2));
		game.addThrow(new Ball(3, 8));
		game.addThrow(new Ball(4, 7));
		Assert.assertEquals(4, game.getCurrentThrowCount());
	}
	
	@Test
	public void testGetThrowPinDown() {
		game.addThrow(new Ball(1, 10));
		game.addThrow(new Ball(2, 2));
		game.addThrow(new Ball(3, 8));
		game.addThrow(new Ball(4, 7));
		Assert.assertEquals(8, game.getThrowPinDown(3));
		Assert.assertEquals(7, game.getThrowPinDown(4));
		Assert.assertEquals(0, game.getThrowPinDown(22));
		Assert.assertEquals(0, game.getThrowPinDown(-1));
	}
}
