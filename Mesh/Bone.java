package Mesh;

import org.lwjgl.util.vector.*;
import java.util.*;

public class Bone {

	List<Joint> joints;

	public Bone() {
		joints = new ArrayList<Joint>();
	}

	public Joint getJoint(int i){
		return joints.get(i);
	}
	public List<Joint> getJoints() {
		return joints;
	}

	public void setJoints(List<Joint> joints) {
		this.joints = joints;
	}
	
	public int getJointCount(){
		return joints.size()-1;
	}
}
