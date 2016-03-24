package c3.sysems.renderer;

import org.lwjgl.opengl.GL20;

import c3.assets.FragmentShaderAsset;

public class FragmentShader extends Shader {

	private FragmentShaderAsset asset;
	
	public FragmentShader(FragmentShaderAsset asset) {
		this.asset = asset;
	}
	
	public void compile() {
		compile(asset.getData(), GL20.GL_FRAGMENT_SHADER);
	}
	
}
