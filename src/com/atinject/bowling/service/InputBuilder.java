package com.atinject.bowling.service;

import com.atinject.bowling.domain.Game;

public interface InputBuilder {
	Game buildInput();
	
	void buildResultsForDisplay();
}
