package com.atinject.bowling.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Team implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int score;
	private List<Player> players;

	public Team(final String name) {
		this.name = name;
	}
	
	public Team() {
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	@XmlElement
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getScore() {
		return score;
	}

	@XmlElement
	public void setScore(int score) {
		this.score = score;
	}

}
