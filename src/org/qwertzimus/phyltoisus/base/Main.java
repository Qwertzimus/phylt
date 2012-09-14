package org.qwertzimus.phyltoisus.base;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexArrayBgra;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.ARBVertexProgram;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBVertexType2_10_10_10_REV;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
import org.qwertzimus.phyltoisus.gui.*;
import org.qwertzimus.phyltoisus.physic.Time;
import org.qwertzimus.phyltoisus.world.*;

public class Main {
	public static boolean isRunning, isLoaded;
	public static World world;
	public static Player player;
	public static int xMousePosition, yMousePosition, shaderId, vertexShaderId,
			fragmentShaderId, lightValueLoc, baseLightValueLoc;

	boolean[][][] booleans;

	public Main() {
		isRunning = true;
		init();
	}

	public void init() {
		try {
			Display.create();
			// Display.makeCurrent();
			Display.setDisplayMode(new DisplayMode(1024, 768));
			// Display.setVSyncEnabled(true);
		} catch (Exception e) {
			System.out.println(e);
		}
		Textures.loadTextures();
		Textures.loadTextureCoordinates();
		Text.setupFont(18);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GLU.gluOrtho2D(0, (float) 1024, 0, (float) 768);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glDisable(GL_DEPTH_TEST);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);
	}

	public static void main(String[] args) {

		System.out.println("total heap size: "
				+ Runtime.getRuntime().totalMemory());
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		// System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")
		// + "/natives/");
		Main main = new Main();
		main.run();
	}

	public static void createVertexShader(String path) {
		vertexShaderId = ARBShaderObjects
				.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
		String vertexCode = "";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while ((line = reader.readLine()) != null) {
				vertexCode += line + "\n";
			}
		} catch (Exception e) {
			System.out.println("Failed reading vertex shading code");
			return;

		}
		ARBShaderObjects.glShaderSourceARB(vertexShaderId, vertexCode);
		ARBShaderObjects.glCompileShaderARB(vertexShaderId);

	}

	public static void createFragmentShader(String path) {
		fragmentShaderId = ARBShaderObjects
				.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		String fragCode = "";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while ((line = reader.readLine()) != null) {
				fragCode += line + "\n";
			}
		} catch (Exception e) {
			System.out.println("Failed reading vertex shading code");
			return;

		}
		ARBShaderObjects.glShaderSourceARB(fragmentShaderId, fragCode);
		ARBShaderObjects.glCompileShaderARB(fragmentShaderId);

	}

	public void run() {
		System.out.println(GL11.glGetString(GL11.GL_VERSION));
		// System.out.println(GL11.glGetString(.GL_));
		shaderId = ARBShaderObjects.glCreateProgramObjectARB();
		// ARBVertexProgram.glEnableVertexAttribArrayARB(0);
		createVertexShader("./shaders/testshader01.vert");
		createFragmentShader("./shaders/testshader01.frag");

		ARBShaderObjects.glAttachObjectARB(shaderId, vertexShaderId);
		ARBShaderObjects.glAttachObjectARB(shaderId, fragmentShaderId);
		ARBVertexShader.glBindAttribLocationARB(shaderId, 2, "lightValue");
		ARBShaderObjects.glLinkProgramARB(shaderId);

		if (ARBShaderObjects.glGetObjectParameteriARB(shaderId,
				ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.out.println("buagduawgdu");
			return;
		}
		ARBShaderObjects.glValidateProgramARB(shaderId);

		if (ARBShaderObjects.glGetObjectParameteriARB(shaderId,
				ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.out.println("buagduawgdu");
			return;
		}
		lightValueLoc = ARBVertexShader.glGetAttribLocationARB(shaderId,
				"lightValue");
		baseLightValueLoc = ARBShaderObjects.glGetUniformLocationARB(shaderId,
				"baseLight");

		ARBShaderObjects.glGetUniformLocationARB(Main.shaderId, "baseLight");
		System.out.println(lightValueLoc);
		World.isFirstLoaded = false;
		Controls.loadControls();
		world = new World();
		world.init(0);
		Text.drawTextOnDisplay("LOADING.......", Display.getWidth() / 2 - 50,
				Display.getHeight() / 2);
		Text.drawTextOnDisplay(
				"(the window may not be responding while loading)",
				Display.getWidth() / 2 - 50, Display.getHeight() / 2 + 20);
		Text.drawTextOnDisplay("Please be patient",
				Display.getWidth() / 2 - 50, Display.getHeight() / 2 + 40);

		/*
		 * player = new Player(); player.setCanFly(true); player.setSize(12);
		 * player.setWidth(48); player.setHeight(48); player.setOffset(12, 16,
		 * -12, -16); player.setPosition(200, 256); player.updateBuffer();
		 * player.setAnimated(true);
		 * player.loadTextures("./images/Rainbow Dash/");
		 * world.registerEntity(player);
		 */
		// world.loadWorldParts();
		player = new Player();
		player.setCanFly(true);
		player.setSize(12);
		player.setWidth(48);
		player.setHeight(48);
		player.setOffset(12, 16, -12, -16); // rd
		// player.setOffset(12, 4, -12, -16); //pp
		player.setPosition(200, 256);
		Light l = new Light();
		l.setPosition(player.getPosition());
		l.setLightIntensity(1f);
		l.setRange(10);
		world.lights.add(l);
		player.updateBuffer();
		player.setAnimated(true);
		// player.loadTextures("./images/Rainbow Dash/");

		/*
		 * for (int i = 0; i < 250; i++) {
		 * Textures.loadAnimations("./images/Pinkie Pie/");
		 * Textures.loadAnimations("./images/Lotus/");
		 * Textures.loadAnimations("./images/Octavia/"); }
		 */
		long t = System.currentTimeMillis();
		// for (int i = 0; i < 50; i++)
		{
			Textures.loadAnimations("./images/Pinkie Pie/");
			Textures.loadAnimations("./images/Rainbow Dash/");
			Textures.loadAnimations("./images/Daring Do/");
			Textures.loadAnimations("./images/Princess Luna");
			Textures.loadAnimations("./images/Princess Celestia");
		}
		player.setAnimation(Textures.animations.get("Rainbow Dash"));
		world.registerEntity(player);
		System.out.println(System.currentTimeMillis() - t + "ms");
		/*
		 * for (int i = 0; i < 100; i++) { Entity ent = new Entity();
		 * ent.setCanFly(true); ent.setSize(12); ent.setWidth(48);
		 * ent.setHeight(48); ent.setOffset(12, 4, -12, -16);
		 * ent.setPosition(200, 512); ent.updateBuffer(); ent.setAnimated(true);
		 * ent.setAnimation(Textures.animations.get("Pinkie Pie"));
		 * EntityController entC = new EntityController(ent); // Light l1=new
		 * Light(); // l1.setPosition(ent.getPosition()); //
		 * l1.setLightIntensity(1f); // l1.setRange(10); //
		 * world.lights.add(l1); // entC.setTarget(player); entC.start();
		 * world.registerEntity(ent); world.registerEntityController(entC); }
		 */
		Thread worldUpdater = new Thread() {
			public void run() {
				while (isRunning) {
					try {
						world.loadWorldParts(player, 0);
						World.isFirstLoaded = true;

						sleep(World.worldUpdaterSleepTime);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				System.out.println("ended");
				try {
					System.out.println("saving...");
					world.save();
					System.out.println("saved World successfully");
				} catch (Exception e) {
				}
				System.exit(0);
			}

		};
		worldUpdater.start();
		while (isRunning) {

			Display.update();

			if (Display.isCloseRequested()) {
				isRunning = false;
			}
			// if (Display.isActive())
			{

				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

				// for (int i = 0; i < 32; i++)
				{
					// System.out.println(ARBShaderObjects.glGetActiveUniformARB(
					// shaderId, 0, 16));
					// System.out.println(ARBVertexShader.glGetAttribLocationARB(shaderId,
					// "lightValue"));
				}

				if (World.isFirstLoaded) {

					putput();
					world.update();
					world.updateAIs();
					player.usePerspective();
					glBindTexture(GL_TEXTURE_2D, 0);
					glClear(GL_COLOR_BUFFER_BIT);
					world.draw(player);
				} else {
					glBindTexture(GL_TEXTURE_2D, 0);
					glClear(GL_COLOR_BUFFER_BIT);
					Text.drawTextOnDisplay("LOADING.......",
							Display.getWidth() / 2 - 50,
							Display.getHeight() / 2);
					Text.drawTextOnDisplay(
							"(the window may not be responding while loading)",
							Display.getWidth() / 2 - 50,
							Display.getHeight() / 2 + 20);
					Text.drawTextOnDisplay("Please be patient",
							Display.getWidth() / 2 - 50,
							Display.getHeight() / 2 + 40);
					player.usePerspective();
					world.draw(player);
				}
				// ARBShaderObjects.glUseProgramObjectARB(0);

				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				updateGUI();
				drawGUI();
			}
			// Textures.update("Pinkie Pie",new
			// String[]{"trotcycle_left.gif","trotcycle_right.gif"});
			Time.updateFPS();

			Display.sync(200);
			// System.out.println(player.getVelocity());
		}
		Display.destroy();
		// System.exit(0);
	}

	public void updateGUI(){
		if (world.isFirstLoaded) {
			GUI.updateStats();
		}
	}
	public void drawGUI() {
		if (world.isFirstLoaded) {
			player.inventory.draw();
		}
	}

	public void putput() {
		Controls.handleInput();
		xMousePosition = Mouse.getX();
		yMousePosition = Mouse.getY();
		int[] sPos = getSelectedPosition();

		if (Controls.isLeftPressed) {
			player.moveLeft();
		}
		if (Controls.isRightPressed) {
			player.moveRight();
		}
		if (Controls.isForwardPressed) {
			player.moveUp();
		}
		if (Controls.isBackwardPressed) {
			player.moveDown();
		}
		if(Controls.isInventoryPressed){
			if(!Controls.isInventoryPressedLastFrame){
				player.inventory.setInForeground(!player.inventory.isInForeground());
			}
		}
		if (Controls.isPPressed) {
			if (!Controls.isPPressedLastFrame) {
				// System.out.println(player.getPosition());
				// System.out.println(getSelectedBlock().isSolid());
				Light l1 = new Light();
				l1.setPosition(player.getPosition());
				l1.setLightIntensity(1f);
				l1.setRange(10);
				world.lights.add(l1);
			}
		}
		if (Controls.isCPressed) {
			if (!Controls.isCPressedLastFrame) {
				System.out.println(getSelectedBlock().getLightValue());
			}
		}
		if (Controls.isTPressed) {
			if (!Controls.isTPressedLastFrame) {
				player.setInHoverMode(!player.isInHoverMode());
			}
		}
		if (Controls.isJumpPressed) {
			if (!Controls.isJumpPressedLastFrame) {
				player.jump();
			}
		}
		if (Controls.isLeftClicked) {
			// if (!Controls.isLeftClickedLastFrame)
			{// System.out.println((getSelectedBlock()).getPosition());
				/* System.out.println */
				Block selectedBlock = getSelectedBlock();
				if (selectedBlock != null) {
					if (selectedBlock.getId() != 0)
						selectedBlock.setId(0);
				}

			}
		}
		if (Controls.isRightClicked) {
			// if (!Controls.isLeftClickedLastFrame)
			{// System.out.println((getSelectedBlock()).getPosition());
				/* System.out.println */
				Block selectedBlock = getSelectedBlock();
				if (selectedBlock != null) {
					if (selectedBlock.getId() != 1)
						selectedBlock.setId(1);
				}
			}
		}
	}

	public Chunk getSelectedChunk() {
		Chunk chunk;
		int xSelectedPosition = (int) ((xMousePosition + player.getX() - Display
				.getWidth() / 2));
		int ySelectedPosition = (int) ((yMousePosition + player.getY() - Display
				.getHeight() / 2));
		// get selectedChunk position
		while (xSelectedPosition % 256 != 0) {
			xSelectedPosition--;
		}
		while (ySelectedPosition % 256 != 0) {
			ySelectedPosition--;
		}

		// System.out.println(xSelectedPosition+" "+ySelectedPosition);
		chunk = world.getChunk(xSelectedPosition, ySelectedPosition);
		return chunk;
	}

	// gibt den Block wieder in/über dem die Maus sich gerade befindet
	public Block getSelectedBlock() {
		Block b;
		int xSelectedPosition = (int) ((xMousePosition + player.getX() - Display
				.getWidth() / 2));
		int ySelectedPosition = (int) ((yMousePosition + player.getY() - Display
				.getHeight() / 2));

		while (xSelectedPosition % (16 * 16) != 0) {
			xSelectedPosition--;
		}
		while (ySelectedPosition % (16 * 16) != 0) {
			ySelectedPosition--;
		}

		Chunk selectedChunk = world.getChunk(xSelectedPosition,
				ySelectedPosition);

		xSelectedPosition = (int) (xMousePosition + player.getX() - Display
				.getWidth() / 2);
		ySelectedPosition = (int) (yMousePosition + player.getY() - Display
				.getHeight() / 2);
		xSelectedPosition -= selectedChunk.getPosition().x;
		ySelectedPosition -= selectedChunk.getPosition().y;
		while (xSelectedPosition >= 256) {
			xSelectedPosition -= 256;
		}
		while (ySelectedPosition >= 256) {
			ySelectedPosition -= 256;
		}
		while (xSelectedPosition % 16 != 0) {
			xSelectedPosition--;
		}
		while (ySelectedPosition % 16 != 0) {
			ySelectedPosition--;
		}
		xSelectedPosition = xSelectedPosition / 16;
		ySelectedPosition = ySelectedPosition / 16;
		System.out.println(xSelectedPosition + " " + ySelectedPosition);
		return b = selectedChunk.getBlock(xSelectedPosition, ySelectedPosition,
				0);
	}

	public int[] getSelectedPosition() {
		int xSelectedPosition = (int) ((xMousePosition + player.getPosition().x - Display
				.getWidth() / 2));
		int ySelectedPosition = (int) ((yMousePosition + player.getPosition().y - Display
				.getHeight() / 2));
		while (xSelectedPosition % (16) != 0) {
			xSelectedPosition--;
		}
		while (ySelectedPosition % (16) != 0) {
			ySelectedPosition--;
		}
		return new int[] { xSelectedPosition, ySelectedPosition };
	}
}
