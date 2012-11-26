package com.atinject.bowling.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Only allow to play one game at a time. Player's name is unique.
 * 
 * @author kcai
 * 
 */
@XmlRootElement
public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int score;
	private String name;
	private List<Ball> balls;

	public Player(final String name) {
		this.name = name;
		balls = new ArrayList<Ball>(21);
	}
	
	public Player() {
		balls = new ArrayList<Ball>(21);
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Ball> getBalls() {
		return balls;
	}

	@XmlElement
	public void setBalls(List<Ball> balls) {
		this.balls = balls;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Player p = (Player) obj;
		// TODO Auto-generated method stub
		return p.name != null && p.name.equals(this.name);
	}
}
