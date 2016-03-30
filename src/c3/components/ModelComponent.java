package c3.components;

import org.joml.*;

import c3.assets.ModelAsset;
import c3.sysems.renderer.Program;

public class ModelComponent extends RenderableComponent {
	
	private ModelAsset modelAsset;
	
	private Matrix4f modelView;
	
	private Program program;
	
	public ModelComponent() {
		
	}
	
	public ModelComponent setModelAsset(ModelAsset asset) {
		
		this.modelAsset = asset;
		
		return this;
		
	}
	
	public ModelComponent setProgram(Program program) {
		
		this.program = program;
		
		return this;
		
	}
	
	@Override
	public void draw() {
		
		// TODO Auto-generated method stub
		
	}
	
	public AxisAngle4f getOrientation(AxisAngle4f rotation) {
		return modelView.getRotation(rotation);
	}
	
	public ModelComponent setOrientation(AxisAngle4f rotation) {
		
		modelView.set(rotation);
		
		return this;
		
	}
	
	public ModelComponent setPosition(Vector3f position) {
		
		modelView.setTranslation(position);
		
		return this;
		
	}
	
	public ModelComponent setX(float x) {
		
		modelView.m30 = x;
		
		return this;
		
	}

	public ModelComponent setY(float y) {
		
		modelView.m31 = y;
		
		return this;
		
	}

	public ModelComponent setZ(float z) {
		
		modelView.m32 = z;
		
		return this;
		
	}
	
	public Vector3f getPosition(Vector3f position) {
		
		return modelView.getTranslation(position);
		
	}
	
	public float getX() {
		
		return modelView.m03;
		
	}
	
	public float getY() {
		
		return modelView.m13;
		
	}
	
	public float getZ() {
		
		return modelView.m23;
		
	}
	
	public ModelComponent offset(Vector3f offset) {
		
		modelView.translate(offset);
		
		return this;
		
	}
	
	public void offsetX(float x) {
		
		modelView.m03 += x;
		
	}

	public void offsetY(float y) {
		
		modelView.m13 += y;
		
	}

	public void offsetZ(float z) {
		
		modelView.m23 += z;
		
	}

}