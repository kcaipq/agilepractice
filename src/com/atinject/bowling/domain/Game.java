package com.atinject.bowling.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Team> teams;

	public Game(String name, List<Team> teams) {
		this.name = name;
		this.teams = teams;
	}

	public Game() {
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public List<Team> getTeams() {
		return teams;
	}

	@XmlElement
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

}
