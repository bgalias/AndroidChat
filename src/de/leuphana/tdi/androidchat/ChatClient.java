package de.leuphana.tdi.androidchat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.os.AsyncTask;

class ChatClient extends AsyncTask<Object, String, String> {

	@Override
	protected String doInBackground(Object... params) {
		int port = 14911;
		try {
			DatagramSocket clientSocket = new DatagramSocket(port);
			DatagramPacket packet = new DatagramPacket(new byte[512], 512);

			while (true) {
				clientSocket.receive(packet);
				publishProgress(new String(packet.getData(), 0,
						packet.getLength()));
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... params) {

	}

	@Override
	protected void onPostExecute(String result) {

	}
}