package com.atinject.bowling.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.atinject.bowling.FrameType;
import com.atinject.bowling.domain.Ball;

public class FrameTypeResolverTest {
	
	private FrameTypeResolver fr;
	
	@Before
	public void before() {
		fr = new FrameTypeResolverImpl();
	}
	
	@Test
	public void testEmptyFrame() {
		Ball b2 = new Ball(2, 1);
		FrameType fType = fr.resolveFrameType(null, b2);
		Assert.assertEquals(FrameType.EMPTY, fType);
	}

	@Test
	public void testStrikeFrame() {
		Ball b1 = new Ball(1, 10);
		Ball b2 = new Ball(2, 1);
		FrameType fType = fr.resolveFrameType(b1, b2);
		Assert.assertEquals(FrameType.STRIKE, fType);
	}
	
	@Test
	public void testSpareFrame() {
		Ball b1 = new Ball(1, 9);
		Ball b2 = new Ball(2, 1);
		FrameType fType = fr.resolveFrameType(b1, b2);
		Assert.assertEquals(FrameType.SPARE, fType);
	}
	
	@Test
	public void testStandardFrame() {
		Ball b1 = new Ball(1, 8);
		Ball b2 = new Ball(2, 1);
		FrameType fType = fr.resolveFrameType(b1, b2);
		Assert.assertEquals(FrameType.STANDARD, fType);
	}
}
