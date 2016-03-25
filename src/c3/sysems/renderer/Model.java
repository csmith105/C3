package c3.sysems.renderer;

import c3.assets.ModelAsset;
import c3.math.*;

public class Model {
	
	private Matrix4f modelView;
	
	public Model(ModelAsset asset) {
		
		
		
	}
	
	public void setPosition(Vector3f position) {
		modelView.m03 = position.x;
		modelView.m13 = position.y;
		modelView.m23 = position.z;
	}
	
	public void setX(float x) {
		modelView.m03 = x;
	}

	public void setY(float y) {
		modelView.m13 = y;
	}

	public void setZ(float z) {
		modelView.m23 = z;
	}
	
	public void offset(Vector3f offset) {
		modelView.m03 += offset.x;
		modelView.m13 += offset.y;
		modelView.m23 += offset.z;
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
	
	public Vector3f getPosition() {
		return new Vector3f(modelView.m03, modelView.m13, modelView.m23);
	}

}