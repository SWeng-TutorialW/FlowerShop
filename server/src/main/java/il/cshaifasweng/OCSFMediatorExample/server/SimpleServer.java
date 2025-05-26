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
	private static final String ADMIN_PASSWORD = "12345"; // password to be admin


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
				case "verifyAdminCode":
					String code = (String) map.get("code");
					boolean allowed = ADMIN_PASSWORD.equals(code); // check against the real password
					try {
						client.sendToClient(allowed ? "admin_login_success" : "admin_login_failed");
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
						for (Thread t : getClientConnections()) {
							ConnectionToClient c = (ConnectionToClient) t;
							try {
								c.sendToClient(allFlowers);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
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
