package com.atinject.bowling.service;

import com.atinject.bowling.FrameType;
import com.atinject.bowling.domain.Ball;

public class FrameTypeResolverImpl implements FrameTypeResolver {

	@Override
	public FrameType resolveFrameType(Ball firstThrow, Ball secondThrow) {
		if (firstThrow == null) return FrameType.EMPTY;

		int first = firstThrow.getPinDown();
		int second = secondThrow == null ? 0 : secondThrow.getPinDown();

		if (10 == first) {
			return FrameType.STRIKE;
		} else if (secondThrow != null && first + second == 10) {
			return FrameType.SPARE;
		} else if (secondThrow != null && first + second == 0) {
			return FrameType.GUTTED;
		} else if (secondThrow != null) {
			return FrameType.STANDARD;
		}
		return FrameType.EMPTY;
	}
	
	@Override
	public FrameType resolveFrameType(int firstThrow, int secondThrow) {
		
		return null;
	}
	
}
