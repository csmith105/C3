package c3.sysems.renderer;

import org.lwjgl.opengl.GL20;

import c3.assets.VertexShaderAsset;

public class VertexShader extends Shader {

	private VertexShaderAsset asset;
	
	public VertexShader(VertexShaderAsset asset) {
		this.asset = asset;
	}
	
	public void compile() {
		compile(asset.getData(), GL20.GL_VERTEX_SHADER);
	}

}