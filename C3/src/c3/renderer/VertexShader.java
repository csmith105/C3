package c3.renderer;

import org.lwjgl.opengl.ARBVertexShader;

import c3.assets.VertexShaderAsset;

public class VertexShader extends Shader {

	private VertexShaderAsset asset;
	
	public VertexShader(VertexShaderAsset asset) {
		this.asset = asset;
	}
	
	public void load() {
		compile(asset.getData(), ARBVertexShader.GL_VERTEX_SHADER_ARB);
	}

}