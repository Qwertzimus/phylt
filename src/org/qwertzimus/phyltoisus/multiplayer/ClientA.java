package org.qwertzimus.phyltoisus.multiplayer;

import java.io.IOException;
import java.net.InetAddress;

import org.qwertzimus.phyltoisus.multiplayer.world.IWorld;
import org.qwertzimus.phyltoisus.world.World;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

public class ClientA {
	public static int portTCP = 5555, portUDP = 54777;
	Kryo kryo;
	Client client;
	ObjectSpace objectSpace;
	public IWorld iworld;
	public World world;
	public ClientA() {
		client = new Client();
		init();
	}

	public void init() {
		kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		kryo.register(SomeObject.class);
		kryo.register(World.class);
		kryo.register(IWorld.class);
		ObjectSpace.registerClasses(kryo);
	}

	public void run() {

		client.start();
		try {
			client.connect(5000, "127.0.0.1", portTCP, portUDP);
		} catch (IOException e) {
			System.out.println(e);
		}
		SomeRequest request = new SomeRequest();
		request.text = "Hello Server!";
		client.sendTCP(request);
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof SomeResponse) {
					SomeResponse response = (SomeResponse) object;
					System.out.println(response.text);
				}
			}
		});
		objectSpace = new ObjectSpace();
		objectSpace.addConnection(client);
		SomeObject someObject = ObjectSpace.getRemoteObject(client, 42,
				SomeObject.class);
		iworld=ObjectSpace.getRemoteObject(client, 43, IWorld.class);
		// ((RemoteObject)someObject).setNonBlocking(true, true);
		
		objectSpace.register(42, someObject);
		objectSpace.register(43, iworld);
		
		System.out.println(iworld.isServer());
		SomeResponse result = someObject.doSomething();
		System.out.println(result.text);
	}

	public static void main(String args[]) {
		ClientA c = new ClientA();
		c.run();
	}
}
