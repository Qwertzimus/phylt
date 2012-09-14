package org.qwertzimus.phyltoisus.network;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;

public class Client {
	public static void main(String args[]) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		else{
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "Compute";
			Registry registry = LocateRegistry.getRegistry("127.0.0.1");//args[0]
			Compute comp = (Compute) registry.lookup(name);
			Pi task = new Pi(Integer.parseInt("9200"));
			
			BigDecimal pi = comp.executeTask(task);
			System.out.println(pi);
		} catch (Exception e) {
			System.err.println("ComputePi exception:");
			e.printStackTrace();
		}
	}
}
