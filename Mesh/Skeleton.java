package Mesh;

import java.util.*;
import org.lwjgl.util.vector.*;

public class Skeleton {
	List<Bone> bones;

	public Skeleton() {
		bones = new ArrayList<Bone>();
	}

	public Bone getBone(int i) {
		return bones.get(i);
	}

	public List<Bone> getBones() {
		return bones;
	}

	public int getBoneCount() {
		return bones.size() - 1;
	}

	public void setBones(List<Bone> bones) {
		this.bones = bones;
	}

	public int addBone(Bone bone) {
		bones.add(bone);
		return bones.size() - 1;
	}

	public void removeBone(int i) {
		bones.remove(i);
	}

	public void removeBone(Bone bone) {
		bones.remove(bone);
	}

}
