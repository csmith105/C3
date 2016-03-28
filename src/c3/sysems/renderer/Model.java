package c3.sysems.renderer;

import org.joml.*;

import c3.assets.ModelAsset;

public class Model {
	
	private Matrix4f modelView;
	
	public Model(ModelAsset asset) {
		
		
		
	}
	
	public AxisAngle4f getOrientation(AxisAngle4f rotation) {
		return modelView.getRotation(rotation);
	}
	
	public Model setOrientation(AxisAngle4f rotation) {
		modelView.set(rotation);
		return this;
	}
	
	public Model setPosition(Vector3f position) {
		
		modelView.setTranslation(position);
		
		return this;
		
	}
	
	public Model setX(float x) {
		
		modelView.m30 = x;
		
		return this;
		
	}

	public Model setY(float y) {
		
		modelView.m31 = y;
		
		return this;
		
	}

	public Model setZ(float z) {
		
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
	
	public Model offset(Vector3f offset) {
		
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