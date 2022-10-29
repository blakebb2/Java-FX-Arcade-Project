package gamePlaySystem.LevelSystem;

import javafx.scene.Group;
import gameComponent.MovableObject.BallBreakout;
import gameComponent.NPCObject.GameNPC;
import gameComponent.NPCObject.NPCGalagaWingedPowerUp;
import gameComponent.NPCObject.NPCGalagaWingedRed;
import gamePlaySystem.Player;


/**
 * @author Xu Yan
 * 
 * GameBreakoutLevel_2.java
 * 
 */

public class GalagaLevel_2 extends GameLevel {

	private static final int BRICKS_Y_OFFSET = 20;
	private static final int LEVEL = 1;
	// the layout has row: 6, column: 18
	private int[][] LAYOUT_L2 = {
			{0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0},
			{0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0},
	};

	public GalagaLevel_2() {
		// super parameters: (brickRows, eachRowBrick, unbreakableBricksQty, emptySpaceQty, bricksYOffset)
		super(BRICKS_Y_OFFSET, LEVEL);
		STARTING_POSITION = 1.8/4.0;
		allowedHealth = 3;
		gameNPCLayout = LAYOUT_L2;
		eachRowNPCs = LAYOUT_L2[0].length;
	}
	
    // create the winged based on its type
    protected void createWingeds(Group root) {
    	for (int row = 0; row < gameNPCLayout.length; row++) {
    		for (int col = 0; col < gameNPCLayout[row].length; col++) {
    			String typeStr = integerToStringOfEachWingedType.get(gameNPCLayout[row][col]);
    			if (!typeStr.equals(EMPTY)) {
    				generateWingeds(col, row, root, typeStr);
    			}
    		}
    	}
    }
    
	// load the winged on the screen
	private void generateWingeds(int col, int row, Group root, String wingedType) {
		final String POWER_UP_WINGED_IMAGE_SOURCE = "resources/scorepower.png";
		final String RED_WINGED_IMAGE_SOURCE = "resources/winged2.png";
		
		if (wingedType.equals(POWER_UP)) {
			npc = new NPCGalagaWingedPowerUp(eachRowNPCs, bricksOffsetFromTop, POWER_UP_WINGED_IMAGE_SOURCE);
		} else {
			npc = new NPCGalagaWingedRed(eachRowNPCs, bricksOffsetFromTop, RED_WINGED_IMAGE_SOURCE);
		}
		npc.setNPC(col, row);
		wingedsListOfEachWingedType.get(wingedType).add(npc);
		allNPCs.add(npc);
		root.getChildren().add(npc.getNPC());
	}
	
	// deal with the collision of the bullet and winged
	protected void collideWithWingeds(Group root, BallBreakout ball, Player player) {
		final boolean isPaddle = false;
		for (GameNPC npc: allNPCs) {
			if (ball.getView().getBoundsInParent().intersects(npc.getNPC().getBoundsInParent())) {
				redWinged.remove(npc);
				allNPCs.remove(npc);
				root.getChildren().remove(npc.getNPC());
				player.addScore(1);
				if (powerUpWinged.contains(npc)) {
//					ball.powerUpBall();
				}
//				ball.bounceOnRectangle(npc.getNPCImageView(), isPaddle);
			}
			winCheckForLevel();
		}
	}
	
}
