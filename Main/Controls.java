package Main;

/**
 * Beschreiben Sie hier die Klasse Controls.
 * 
 * @author Sascha 
 * @version 0.01
 */
import org.lwjgl.input.*;
public class Controls
{
    public static char high,low,up,down,left,right,menu,jump,statusChange,inventory,fly,fullScreen;
    public static boolean isHighPressed,isLowPressed,isUpPressed,isDownPressed,isLeftPressed,isRightPressed,isMenuPressed,isJumpPressed,isStatusChangePressed,isInventoryPressed,isLeftClicked,isRightClicked,isFlyPressed,isFullScreenPressed;
    public static boolean isHighPressedLastFrame,isLowPressedLastFrame,isUpPressedLastFrame,isDownPressedLastFrame,isLeftPressedLastFrame,isRightPressedLastFrame,isMenuPressedLastFrame,isJumpPressedLastFrame,isStatusChangePressedLastFrame,isInventoryPressedLastFrame,isLeftClickedLastFrame,isRightClickedLastFrame,isFlyPressedLastFrame,isFullScreenPressedLastFrame;
    public static void loadControls()
    {
        high=Keyboard.KEY_Q;
        low=Keyboard.KEY_E;
        up= Keyboard.KEY_W;
        down= Keyboard.KEY_S;
        left= Keyboard.KEY_A;
        right= Keyboard.KEY_D;
        menu= Keyboard.KEY_ESCAPE;
        jump= Keyboard.KEY_SPACE;
        fly=Keyboard.KEY_F;
        statusChange=Keyboard.KEY_B;
        inventory= Keyboard.KEY_I;
        fullScreen=Keyboard.KEY_F10;
    }

    public static void handleInput()
    {
        handleLastFrameInput();
        isHighPressed=Keyboard.isKeyDown(high);
        isLowPressed=Keyboard.isKeyDown(low);
        isUpPressed=Keyboard.isKeyDown(up);
        isLeftPressed=Keyboard.isKeyDown(left);
        isRightPressed=Keyboard.isKeyDown(right);
        isDownPressed=Keyboard.isKeyDown(down);
        isMenuPressed=Keyboard.isKeyDown(menu);
        isJumpPressed=Keyboard.isKeyDown(jump);
        isStatusChangePressed=Keyboard.isKeyDown(statusChange);
        isInventoryPressed=Keyboard.isKeyDown(inventory);
        isFlyPressed=Keyboard.isKeyDown(fly);
        isLeftClicked=Mouse.isButtonDown(0);
        isRightClicked=Mouse.isButtonDown(1);
        isFullScreenPressed=Keyboard.isKeyDown(fullScreen);
    }


    public static void handleLastFrameInput()
    {
        isHighPressedLastFrame=isHighPressed;
        isLowPressedLastFrame=isLowPressed;
        isUpPressedLastFrame=isUpPressed;
        isDownPressedLastFrame=isDownPressed;
        isLeftPressedLastFrame=isLeftPressed;
        isRightPressedLastFrame=isRightPressed;

        isMenuPressedLastFrame=isMenuPressed;
        isJumpPressedLastFrame=isJumpPressed;
        isStatusChangePressedLastFrame=isStatusChangePressed;
        isInventoryPressedLastFrame=isInventoryPressed;
        isFlyPressedLastFrame=isFlyPressed;
        isLeftClickedLastFrame=isLeftClicked;
        isRightClickedLastFrame=isRightClicked;
    }
}

