package de.leuphana.tdi.androidchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends Thread {

	private DatagramSocket serverSocket;
	private DatagramPacket packet;
	private List<InetSocketAddress> clients;
	private static final String CONNECT = "CONNECT";
	private static final String DISCONNECT = "DISCONNECT";
	private int packetSize;

	public ChatServer(int port) throws SocketException {
		clients = new ArrayList<InetSocketAddress>();
		this.packetSize = 512;
		this.serverSocket = new DatagramSocket(port);
		packet = new DatagramPacket(new byte[packetSize], packetSize);
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				serverSocket.receive(packet);
				InetSocketAddress clientAddress = (InetSocketAddress) packet
						.getSocketAddress();

				String message = new String(packet.getData(), 0,
						packet.getLength());
				if (message.equals(CONNECT)) {
					if (!clients.contains(clientAddress)) {
						clients.add(clientAddress);
					}
				} else if (message.equals(DISCONNECT)) {
					if (clients.contains(clientAddress)) {
						clients.remove(clientAddress);
					}
				} else {
					propagateMessage(message);
				}
			} catch (Exception e) {

			}
		}
	}

	private void propagateMessage(String message) throws IOException {
		for (InetSocketAddress client : clients) {
			packet.setSocketAddress(client);
			serverSocket.send(packet);
		}
	}
}
