package com.atinject.bowling.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.atinject.bowling.domain.Ball;
import com.atinject.bowling.domain.Player;

public class InputValidatorTest {
	
	private InputValidator v;
	private static PlayerGameRegisteryFactory factory = PlayerGameRegisteryFactory.getInstance();
	
	@Before
	public void before() {
		v = new InputValidatorImpl();
	}

	@Test
	public void testTeamName() {
		Assert.assertFalse(v.validateTeamName(null));
		Assert.assertFalse(v.validateTeamName(""));
	}
	
	@Test
	public void testPlayername() {
		Player p = new Player("atinject");
		Player newPlayer = new Player("Kun Cai");
		factory.registerGame(p, null);
		Assert.assertFalse(v.validatePlayerName(p));
		Assert.assertTrue(v.validatePlayerName(newPlayer));
	}
	
	@Test
	public void testValidateThrows() {
		final int currentThrow = 10;
		Player p = new Player("atinject");
		factory.registerGame(p, null);
		GameService game = factory.getGameForPlayer(p);
		game.addThrow(new Ball(1, 1));
		game.addThrow(new Ball(2, 3));
		game.addThrow(new Ball(3, 10));
		Assert.assertTrue(v.validatePinDownForFrame(p, currentThrow));
	}
	
	@Test
	public void testValidateThrowsAsList() {
		List<Ball> balls = new ArrayList<Ball>();
		balls.add(new Ball(1, 1));
		balls.add(new Ball(2, 4));
		balls.add(new Ball(3, 4));
		balls.add(new Ball(4, 5));
		balls.add(new Ball(5, 6));
		balls.add(new Ball(6, 4));
		balls.add(new Ball(7, 5));
		balls.add(new Ball(8, 5));
		balls.add(new Ball(9, 1));
		balls.add(new Ball(10, 0));
		balls.add(new Ball(11, 1));
		balls.add(new Ball(12, 7));
		balls.add(new Ball(13, 3));
		balls.add(new Ball(14, 6));
		balls.add(new Ball(15, 4));
		balls.add(new Ball(16, 3));
		balls.add(new Ball(17, 2));
		balls.add(new Ball(18, 8));
		balls.add(new Ball(19, 10));
		balls.add(new Ball(20, 9));
		balls.add(new Ball(21, 2));
		System.out.println(v.validateThrows(balls));
	}
}
