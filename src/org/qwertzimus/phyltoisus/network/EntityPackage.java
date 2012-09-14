package org.qwertzimus.phyltoisus.network;

public class EntityPackage implements DataPackage {
	public static int packageId=11;
	public int referencedObjectId=-1;
	@Override
	public int getPackageId() {
		return packageId;
	}

	@Override
	public void setPackageId(int packageId) {
		this.packageId=packageId;
	}

	@Override
	public long referencedObjectId() {
		return referencedObjectId;
	}

	@Override
	public void setReferencedObjectId(int referencedObjectId) {
		this.referencedObjectId=referencedObjectId;
	}
	
	
}