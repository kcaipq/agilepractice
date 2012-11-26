package com.atinject.bowling.service;

import com.atinject.bowling.FrameType;
import com.atinject.bowling.domain.Ball;

/**
 * This class resolves the frame to type, such as spare, strike, gutted, or just a standard frame.
 */
public interface FrameTypeResolver {

	/**
	 * By giving two balls in each frame, if the first ball hits 10, the type is Strike,
	 * if first plus second the result is greater than 10 the type returned is Empty, 
	 * otherwise Standard or Spare if it equals to 10.
	 * 
	 * @param firstThrow the first ball in a frame
	 * @param secondThrow the second ball in a frame. (can be null if first hits strike)
	 * @return
	 */
	FrameType resolveFrameType(final Ball firstThrow, final Ball secondThrow);
	
	FrameType resolveFrameType(final int firstThrow, final int secondThrow);
}
