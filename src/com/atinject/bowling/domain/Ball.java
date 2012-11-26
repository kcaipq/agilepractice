package com.atinject.bowling.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ball implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pinDown;
	private int gameThrow; //the throw number in a particular game.
	
	public Ball(final int gameThrow, int pinDown) {
		this.gameThrow = gameThrow;
		this.pinDown = pinDown;
	}
	
	public Ball() {
	}

	public int getPinDown() {
		return pinDown;
	}

	@XmlElement
	public void setPinDown(int pinDown) {
		this.pinDown = pinDown;
	}

	public int getGameThrow() {
		return gameThrow;
	}

	public void setGameThrow(int gameThrow) {
		this.gameThrow = gameThrow;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + this.pinDown;
	}

}
