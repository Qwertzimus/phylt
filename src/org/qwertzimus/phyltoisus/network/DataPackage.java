package org.qwertzimus.phyltoisus.network;

public interface DataPackage {
	long referencedObjectId();
	void setReferencedObjectId(int referencedObjectId);
	
	int getPackageId();
	void setPackageId(int packageId);
}
