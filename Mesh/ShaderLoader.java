package Mesh;

import java.io.*;

import org.lwjgl.opengl.*;

public class ShaderLoader {

	public static int shaderProgramId = -1;

	public static void loadVertexAndFragmentShaderGlobal(
			String vertexShaderLocation, String fragmentShaderLocation) {
		if (shaderProgramId != -1) {
			GL20.glDeleteProgram(shaderProgramId);
		}
		shaderProgramId = GL20.glCreateProgram();
		int vertexShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		int fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		StringBuilder vertexShaderSource = new StringBuilder();
		StringBuilder fragmentShaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					vertexShaderLocation));
			String line;
			while ((line = reader.readLine()) != null) {
				vertexShaderSource.append(line).append("/n");
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					fragmentShaderLocation));
			String line;
			while ((line = reader.readLine()) != null) {
				vertexShaderSource.append(line).append("/n");
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		GL20.glShaderSource(vertexShaderId, vertexShaderSource);
		GL20.glCompileShader(vertexShaderId);
		if (GL20.glGetShader(vertexShaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err
					.println("Vertex shader hasn't compiled correctly. Error:");
			System.err.println(GL20.glGetShaderInfoLog(vertexShaderId, 1024));
		}
		GL20.glShaderSource(fragmentShaderId, fragmentShaderSource);
		GL20.glCompileShader(fragmentShaderId);
		if (GL20.glGetShader(fragmentShaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err
					.println("Fragment shader hasn't compiled correctly. Error:");
			System.err.println(GL20.glGetShaderInfoLog(fragmentShaderId, 1024));
		}
		GL20.glAttachShader(shaderProgramId, vertexShaderId);
		GL20.glAttachShader(shaderProgramId, fragmentShaderId);
		GL20.glLinkProgram(shaderProgramId);
		GL20.glValidateProgram(shaderProgramId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);
	}

	public static int loadVertexAndFragmentShader(String vertexShaderLocation,
			String fragmentShaderLocation) {
		int shaderProgramId = GL20.glCreateProgram();
		int vertexShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		int fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		StringBuilder vertexShaderSource = new StringBuilder();
		StringBuilder fragmentShaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					vertexShaderLocation));
			String line;
			while ((line = reader.readLine()) != null) {
				vertexShaderSource.append(line).append("/n");
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					fragmentShaderLocation));
			String line;
			while ((line = reader.readLine()) != null) {
				vertexShaderSource.append(line).append("/n");
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		GL20.glShaderSource(vertexShaderId, vertexShaderSource);
		GL20.glCompileShader(vertexShaderId);
		if (GL20.glGetShader(vertexShaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err
					.println("Vertex shader hasn't compiled correctly. Error:");
			System.err.println(GL20.glGetShaderInfoLog(vertexShaderId, 1024));
		}
		GL20.glShaderSource(fragmentShaderId, fragmentShaderSource);
		GL20.glCompileShader(fragmentShaderId);
		if (GL20.glGetShader(fragmentShaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err
					.println("Fragment shader hasn't compiled correctly. Error:");
			System.err.println(GL20.glGetShaderInfoLog(fragmentShaderId, 1024));
		}
		GL20.glAttachShader(shaderProgramId, vertexShaderId);
		GL20.glAttachShader(shaderProgramId, fragmentShaderId);
		GL20.glLinkProgram(shaderProgramId);
		GL20.glValidateProgram(shaderProgramId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		return shaderProgramId;
	}
	public static void deleteShaderProgram(int shaderProgramId){
		GL20.glDeleteProgram(shaderProgramId);
	}
}
