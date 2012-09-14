package org.qwertzimus.phyltoisus.multiplayer;



import org.qwertzimus.phyltoisus.multiplayer.world.IWorld;
import org.qwertzimus.phyltoisus.world.World;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class ServerA {
	public Kryo kryo;
	public Server server;
	public static int portTCP = 5555, portUDP = 54777;
	ObjectSpace objectSpace;
	SomeObject someObject;
	public static IWorld iworld;
	public static World world;
	public ServerA() {
		try {
			
			server = new Server();
			init();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void init() {
		kryo = server.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		kryo.register(SomeObject.class);
		kryo.register(World.class);
		kryo.register(IWorld.class);
		ObjectSpace.registerClasses(kryo);
		world=new World();
		iworld=world;
	
	}

	public void run() {
		server.start();
		try {
			server.bind(portTCP, portUDP);
		} catch (Exception e) {
			System.out.println(e);
		}
		someObject=new SomeObject(){
			public SomeResponse doSomething() {
				SomeResponse sR = new SomeResponse();
				sR.text = "raindom";
				System.out.println(sR.text);
				return sR;
			}
		};
		objectSpace = new ObjectSpace();
		objectSpace.register(42, someObject);
		objectSpace.register(43, world);
		world.isServer=true;
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof SomeRequest) {
					objectSpace.addConnection(connection);
					SomeRequest request = (SomeRequest) object;
					System.out.println(request.text);

					SomeResponse response = new SomeResponse();
					response.text = "Hey there!";
					connection.sendTCP(response);
				}
				if (object instanceof SomeResponse) {
					/*objectSpace = new ObjectSpace();
					SomeObject someObject = ObjectSpace.getRemoteObject(
							server.getConnections()[0], 42, SomeObject.class);
					objectSpace.register(42, someObject);
					
					SomeResponse result = someObject.doSomething();
					*/
				}
			}
		});
	}

	public static void main(String args[]) {
		ServerA s = new ServerA();
		s.run();
	}
}
