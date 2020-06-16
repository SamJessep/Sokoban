package nz.ac.ara.srj0070.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.ara.srj0070.model.Game;
import nz.ac.ara.srj0070.model.Direction;
import nz.ac.ara.srj0070.model.Game;

class TestGameMove {
	 Game game;
	 @BeforeEach
	 void setUp() throws Exception {
		 this.game = new Game();
		 this.game.addLevel("Test1", 6, 7,
				 			"#######" +
		 					"#+x+..#" +
				 			"#.....#" +		
		 					"#..w..#" +
		 					"#.....#" +
					 		"#######" );
	 }
	 
	 @Test
	 void testGameMove_canMoveUp() {
		 game.move(Direction.UP);
		 String expectedLevelString = "Test1\n"+
				 					"#######\n" +
									"#+x+..#\n" +
						 			"#..w..#\n" +		
									"#.....#\n" +
									"#.....#\n" +
							 		"#######\n" +
									"move 1\n"+
							 		"completed 0 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_canMoveDown() {
		 game.move(Direction.DOWN);
		 
		 String expectedLevelString = "Test1\n"+
								 	"#######\n" +
									"#+x+..#\n" +
						 			"#.....#\n" +		
									"#.....#\n" +
									"#..w..#\n" +
							 		"#######\n" +
									"move 1\n"+
							 		"completed 0 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_canMoveLeft() {
		 game.move(Direction.LEFT);
		 String expectedLevelString = "Test1\n"+
				 	"#######\n" +
					"#+x+..#\n" +
		 			"#.....#\n" +		
					"#.w...#\n" +
					"#.....#\n" +
			 		"#######\n" +
					"move 1\n"+
			 		"completed 0 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_canMoveRight() {
		 game.move(Direction.RIGHT);
		 String expectedLevelString = "Test1\n"+
				 	"#######\n" +
					"#+x+..#\n" +
		 			"#.....#\n" +		
					"#...w.#\n" +
					"#.....#\n" +
			 		"#######\n" +
					"move 1\n"+
			 		"completed 0 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_StopsAtWall() {
		 game.move(Direction.DOWN);
		 game.move(Direction.DOWN);
		 String expectedLevelString = "Test1\n"+
				 	"#######\n" +
					"#+x+..#\n" +
		 			"#.....#\n" +		
					"#.....#\n" +
					"#..w..#\n" +
			 		"#######\n" +
					"move 1\n"+
			 		"completed 0 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_CanWalkOnTarget() {
		 game.move(Direction.UP);
		 game.move(Direction.UP);
		 String expectedLevelString = "Test1\n"+
				 	"#######\n" +
					"#+xW..#\n" +
		 			"#.....#\n" +		
					"#.....#\n" +
					"#.....#\n" +
			 		"#######\n" +
					"move 2\n"+
			 		"completed 0 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_CanPushBoxOnTarget() {
		 game.move(Direction.UP);
		 game.move(Direction.UP);
		 game.move(Direction.LEFT);
		 String expectedLevelString = "Test1\n"+
				 	"#######\n" +
					"#Xw+..#\n" +
		 			"#.....#\n" +		
					"#.....#\n" +
					"#.....#\n" +
			 		"#######\n" +
					"move 3\n"+
			 		"completed 1 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_CantPushBoxOnWall() {
		 game.move(Direction.UP);
		 game.move(Direction.UP);
		 game.move(Direction.LEFT);
		 game.move(Direction.LEFT);
		 String expectedLevelString = "Test1\n"+
				 	"#######\n" +
					"#Xw+..#\n" +
		 			"#.....#\n" +		
					"#.....#\n" +
					"#.....#\n" +
			 		"#######\n" +
					"move 3\n"+
			 		"completed 1 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_BoxStaysWhenWorkerLeaves() {
		 game.move(Direction.UP);
		 game.move(Direction.UP);
		 game.move(Direction.LEFT);
		 game.move(Direction.RIGHT);
		 String expectedLevelString = "Test1\n"+
				 	"#######\n" +
					"#X.W..#\n" +
		 			"#.....#\n" +		
					"#.....#\n" +
					"#.....#\n" +
			 		"#######\n" +
					"move 4\n"+
			 		"completed 1 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 
	 @Test
	 void testGameMove_BoxBlocksBoxMovement() {
		 game.addLevel("Test2", 7, 7,
		 			"#######" +
 					"#.....#" +
					"#..x..#" +
		 			"#..x..#" +		
					"#..w..#" +
					"#++...#" +
			 		"#######" );
		 game.move(Direction.UP);
		 String expectedLevelString = "Test2\n"+
				 	"#######\n" +
					"#.....#\n" +
					"#..x..#\n" +
		 			"#..x..#\n" +		
					"#..w..#\n" +
					"#++...#\n" +
			 		"#######\n" +
					"move 0\n"+
			 		"completed 0 of 2\n";
		 String actualLevelString = this.game.toString();
		 assertTrue(expectedLevelString.equals(actualLevelString));
	 }
	 

}
