package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.FlowerAccess;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;

import java.util.HashMap;
import java.util.List;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof String) {
			String command = (String) msg;

			switch (command) {
				case "getAllFlowers":
					List<Flower> flowers = FlowerAccess.getAllFlowers();
					try {
						client.sendToClient(flowers);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				// You can add more simple String commands here
				default:
					System.out.println("Unknown string command: " + command);
			}
		}
		else if (msg instanceof HashMap) {
			HashMap<String, Object> map = (HashMap<String, Object>) msg;
			String command = (String) map.get("command");

			switch (command) {
				case "getFlowerBySku":
					int sku = (int) map.get("sku");
					Flower flower = FlowerAccess.getFlowerBySku(sku);
					try {
						client.sendToClient(flower);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "updateFlowerPrice":
					int skuToUpdate = (int) map.get("sku");
					double newPrice = (double) map.get("price");
					boolean updated = FlowerAccess.updateFlowerPrice(skuToUpdate, newPrice);
					try {
						client.sendToClient(updated);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "updateFlower":
					Flower updatedFlower = (Flower) map.get("flower");
					boolean updatedOk = FlowerAccess.updateFlower(updatedFlower);
					if (updatedOk) {
						// Broadcast updated catalog to ALL clients
						List<Flower> allFlowers = FlowerAccess.getAllFlowers();
						for (Thread c : getClientConnections()) {
                            c.setName(allFlowers.toString());
                        }
					} else {
						//send error message to client
						try {
							client.sendToClient("Flower update failed");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
				default:
					System.out.println("Unknown hashmap command: " + command);
			}
		}
		else {
			System.out.println("Unknown message type from client: " + msg.getClass());
		}
	}


	public void sendToAllClients(Object message) {
		for (SubscribedClient subscribedClient : SubscribersList) {
			try {
				subscribedClient.getClient().sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
