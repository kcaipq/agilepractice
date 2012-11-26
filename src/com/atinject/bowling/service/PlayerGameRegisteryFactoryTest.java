package com.atinject.bowling.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.atinject.bowling.domain.Player;

/**
 * Simple tests to test the {@link GameService} registry for players.
 * @author kcai
 *
 */
public class PlayerGameRegisteryFactoryTest {
	
	private static PlayerGameRegisteryFactory factory = PlayerGameRegisteryFactory.getInstance();
	
	@Before
	public void before() {
		factory.clear();
	}

	@Test
	public void testGetGameForSamePlayer() {
		factory.registerGame(new Player("Kun"), null);
		GameService game = factory.getGameForPlayer(new Player("Kun"));
		Assert.assertNotNull(game);
	}
	
	@Test
	public void testRegisterGame() {
		factory.registerGame(null, null);
		Assert.assertNull(factory.getGameForPlayer(new Player("Kun")));
	}
	
	@Test
	public void testGetGameForPlayer() {
		Assert.assertNull(factory.getGameForPlayer(null));
	}
	
	@Test
	public void testClear() {
		factory.registerGame(new Player("Kun"), null);
		Assert.assertNotNull(factory.getGameForPlayer(new Player("Kun")));
		factory.clear();
		Assert.assertNull(factory.getGameForPlayer(new Player("Kun")));
	}
}
