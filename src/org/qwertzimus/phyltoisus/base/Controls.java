package org.qwertzimus.phyltoisus.base;

import org.lwjgl.input.*;

public class Controls {
	public static char high, low, forward, backward, left, right, menu, jump,
			statusChange, inventory, fly, fullScreen, polygonMode,
			modelBuildMode, undo, redo, playerRotateMode,c,t,p;
	public static boolean isHighPressed, isLowPressed, isForwardPressed,
			isBackwardPressed, isLeftPressed, isRightPressed, isMenuPressed,
			isJumpPressed, isStatusChangePressed, isInventoryPressed,
			isLeftClicked, isRightClicked, isFlyPressed, isFullScreenPressed,
			isPolygonModePressed, isModelBuildModePressed, isRedoPressed,
			isUndoPressed, isPlayerRotateModePressed,isTPressed,isCPressed,isPPressed;
	public static boolean isHighPressedLastFrame, isLowPressedLastFrame,
			isForwardPressedLastFrame, isBackwardPressedLastFrame,
			isLeftPressedLastFrame, isRightPressedLastFrame,
			isMenuPressedLastFrame, isJumpPressedLastFrame,
			isStatusChangePressedLastFrame, isInventoryPressedLastFrame,
			isLeftClickedLastFrame, isRightClickedLastFrame,
			isFlyPressedLastFrame, isFullScreenPressedLastFrame,
			isPolygonModePressedLastFrame, isModelBuildModePressedLastFrame,
			isRedoPressedLastFrame, isUndoPressedLastFrame,
			isPlayerRotateModePressedLastFrame,isTPressedLastFrame,isCPressedLastFrame,isPPressedLastFrame;

	public static void loadControls() {
		high = Keyboard.KEY_Q;
		low = Keyboard.KEY_E;
		forward = Keyboard.KEY_W;
		backward = Keyboard.KEY_S;
		left = Keyboard.KEY_A;
		right = Keyboard.KEY_D;
		menu = Keyboard.KEY_ESCAPE;
		jump = Keyboard.KEY_SPACE;
		fly = Keyboard.KEY_F;
		statusChange = Keyboard.KEY_B;
		inventory = Keyboard.KEY_I;
		fullScreen = Keyboard.KEY_F10;
		polygonMode = Keyboard.KEY_F1;
		modelBuildMode = Keyboard.KEY_F4;
		redo = Keyboard.KEY_Y;
		undo = Keyboard.KEY_Z;
		playerRotateMode=Keyboard.KEY_R;
		t=Keyboard.KEY_T;
		c=Keyboard.KEY_C;
		p=Keyboard.KEY_P;
	}

	public static void handleInput() {
		handleLastFrameInput();
		isHighPressed = Keyboard.isKeyDown(high);
		isLowPressed = Keyboard.isKeyDown(low);
		isForwardPressed = Keyboard.isKeyDown(forward);
		isLeftPressed = Keyboard.isKeyDown(left);
		isRightPressed = Keyboard.isKeyDown(right);
		isBackwardPressed = Keyboard.isKeyDown(backward);
		isMenuPressed = Keyboard.isKeyDown(menu);
		isJumpPressed = Keyboard.isKeyDown(jump);
		isStatusChangePressed = Keyboard.isKeyDown(statusChange);
		isInventoryPressed = Keyboard.isKeyDown(inventory);
		isFlyPressed = Keyboard.isKeyDown(fly);
		isLeftClicked = Mouse.isButtonDown(0);
		isRightClicked = Mouse.isButtonDown(1);
		isFullScreenPressed = Keyboard.isKeyDown(fullScreen);
		isPolygonModePressed = Keyboard.isKeyDown(polygonMode);
		isModelBuildModePressed = Keyboard.isKeyDown(modelBuildMode);
		isUndoPressed = Keyboard.isKeyDown(undo);
		isRedoPressed = Keyboard.isKeyDown(redo);
		isPlayerRotateModePressed=Keyboard.isKeyDown(playerRotateMode);
		isTPressed=Keyboard.isKeyDown(t);
		isCPressed=Keyboard.isKeyDown(c);
		isPPressed=Keyboard.isKeyDown(p);
	}

	public static void handleLastFrameInput() {
		isHighPressedLastFrame = isHighPressed;
		isLowPressedLastFrame = isLowPressed;
		isForwardPressedLastFrame = isForwardPressed;
		isBackwardPressedLastFrame = isBackwardPressed;
		isLeftPressedLastFrame = isLeftPressed;
		isRightPressedLastFrame = isRightPressed;

		isMenuPressedLastFrame = isMenuPressed;
		isJumpPressedLastFrame = isJumpPressed;
		isStatusChangePressedLastFrame = isStatusChangePressed;
		isInventoryPressedLastFrame = isInventoryPressed;
		isFlyPressedLastFrame = isFlyPressed;
		isLeftClickedLastFrame = isLeftClicked;
		isRightClickedLastFrame = isRightClicked;
		isPolygonModePressedLastFrame = isPolygonModePressed;
		isModelBuildModePressedLastFrame = isModelBuildModePressed;
		isUndoPressedLastFrame = isUndoPressed;
		isRedoPressedLastFrame = isRedoPressed;
		isPlayerRotateModePressedLastFrame= isPlayerRotateModePressed;
		isTPressedLastFrame=isTPressed;
		isCPressedLastFrame=isCPressed;
		isPPressedLastFrame=isPPressed;
	}
}
