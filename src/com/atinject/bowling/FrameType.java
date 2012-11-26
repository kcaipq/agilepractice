package com.atinject.bowling;

public enum FrameType {
	STRIKE,
	SPARE,
	GUTTED, // two/three throws are all ZERO
	STANDARD, //STANDARD
	EMPTY; // INVALID THROWS, i.e. two/last throw are more than 10.
}
