package com.atinject.bowling.service;

import junit.framework.Assert;

import org.junit.Test;

import com.atinject.bowling.domain.Game;

/**
 * Simple tests to take input from XML data and output to screen.
 * This test case is not intended to verify the scores for players.
 * Refer to {@link GameServiceImpl} and {@link GameServiceTest} for functioning testing.
 */
public class XMLDataBuilderTest {

	@Test
	public void testConstructor() {
		XMLInputBuilder b = new XMLInputBuilder("");
		Assert.assertTrue(b.INPUT_PATH.contains("Game.xml"));
		b = new XMLInputBuilder(null);
		Assert.assertTrue(b.INPUT_PATH.contains("Game.xml"));
		b = new XMLInputBuilder("New Path");
		Assert.assertEquals("New Path", b.INPUT_PATH);
	}
	
	@Test
	public void testBuildInput() {
		XMLInputBuilder b = new XMLInputBuilder("Invalid Path");
		Assert.assertNull(b.buildInput());
		b = new XMLInputBuilder("Game.xml");
		Game g = b.buildInput();
		Assert.assertNotNull(g);
		Assert.assertNotNull(g.getTeams());
	}
	
	@Test
	public void testResultsForDisplay() {
		XMLInputBuilder b = new XMLInputBuilder("Game.xml");
		try{
			b.buildResultsForDisplay();
		} catch (Exception e) {
			Assert.fail("Error in displaying output.");
		}
	}
}
