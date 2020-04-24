package nz.ac.ara.srj0070.Sokoban.tests;

import org.junit.jupiter.api.*;

import nz.ac.ara.srj0070.Sokoban.Game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TestEmptyGame {
	 Game game;
	 @BeforeEach
	 void setUp() throws Exception {
		 this.game = new Game();
	 }
	 
	 @Test
	 void testEmptyGame_getLevelCount() {
		 int expectedlevelCount = 0;
		 int actualLevelCount = game.getLevelCount();
		 assertEquals(expectedlevelCount, actualLevelCount);
	 }

	 @Test
	 void testEmptyGame_toString() {
		 String expectedString = "no levels";
		 String actualString = game.toString();
		 assertEquals(expectedString, actualString);
	 }

	 @Test
	 void testEmptyGame_getCurrentLevelName() {
		 String expectedString = "no levels";
		 String actualString = game.getCurrentLevelName();
		 assertEquals(expectedString, actualString);
	 }

	 @Test
	 void testEmptyGame_getLevelNames() {
		 int expectedLevelNamesCount = 0;
		 List<String> levelNames = game.getLevelNames();
		 boolean actuallyIsEmpty = levelNames.isEmpty();
		 int actualLevelNamesCount = levelNames.size();
		 assertEquals(expectedLevelNamesCount, actualLevelNamesCount);
		 assertTrue(actuallyIsEmpty);
	 }
}