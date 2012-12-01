package com.atinject.bowling.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.atinject.bowling.domain.Player;

/**
 * 
 * This class works like a tiny cache, which holds the instance of {@link GameService} for players.
 * 
 * The factory produces and stores {@link GameService} for each player. Durng the game
 * play, players can easily get hold of the existing instance of {@link GameService}, so the
 * scoring state can be kept tracked in memory for a player.
 * 
 * Must always register a {@link GameService} for a player before games start.
 * 
 * @author kcai
 *
 */
public class PlayerGameRegisteryFactory {

	private static PlayerGameRegisteryFactory instance;
	private static FrameTypeResolver frameResolver = new FrameTypeResolverImpl();
	
	private final Map<Player, GameService> factory = Collections
			.synchronizedMap(new HashMap<Player, GameService>());

	/**
	 * @return singleton instance of {@link PlayerGameRegisteryFactory}
	 */
	public static PlayerGameRegisteryFactory getInstance() {

		if (instance == null) {
			synchronized (PlayerGameRegisteryFactory.class) {
				PlayerGameRegisteryFactory inst = instance;
				if (inst == null) {
					synchronized (PlayerGameRegisteryFactory.class) {
						inst = new PlayerGameRegisteryFactory();
					}
					instance = inst;
				}
			}
		}
		return instance;
	}

	/**
	 * Register a player with a new service if there isn't a one, or resigster it with
	 * the passed-in service instance.
	 * @param player {@link Player}
	 * @param game {@link GameService}
	 */
	public void registerGame(Player player, GameService game) {
		if (player == null)
			return;

		synchronized (factory) {
			if (game == null)
				factory.put(player, new GameServiceImpl(frameResolver));
			else
				factory.put(player, game);
		}
	}

	/**
	 * retrieve the existing instance of {@link GameService} for a player.
	 * @param player {@link Player}
	 * @return {@link GameService}
	 */
	public GameService getGameForPlayer(Player player) {
		if (player == null)
			return null;

		synchronized (factory) {
			return factory.get(player);
		}
	}

	/**
	 * Clear the map storing the {@link GameService} instances.
	 */
	public synchronized void clear() {
		factory.clear();
	}

}
