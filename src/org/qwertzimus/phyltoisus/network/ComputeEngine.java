package org.qwertzimus.phyltoisus.network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements Compute {

	public static int port=3591;
	public ComputeEngine() {
		super();
	}

	public <T> T executeTask(Task<T> t) {
		return t.execute();
	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		} else{
			System.setSecurityManager(new SecurityManager());
			}
		try {
			String name = "Compute";
			Compute engine = new ComputeEngine();
			Compute stub = (Compute) UnicastRemoteObject
					.exportObject(engine, 0);
			LocateRegistry.createRegistry(1099);
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.rebind(name, stub);
			System.out.println("ComputeEngine bound");
		} catch (Exception e) {
			System.err.println("ComputeEngine exception:");
			e.printStackTrace();
		}
	}
}
