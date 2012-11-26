package com.atinject.bowling.domain;

import junit.framework.Assert;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testEquals() {
		Player p1 = new Player("Kun");
		Player p2 = new Player("kun");
		Assert.assertFalse(p1.equals(p2));
		Assert.assertFalse(p1.equals(null));
	}
	
	@Test
	public void testContstructor() {
		Player p = new Player("Kun");
		Assert.assertNotNull(p.getBalls());
	}
}
