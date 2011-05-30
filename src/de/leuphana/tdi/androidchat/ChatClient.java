package de.leuphana.tdi.androidchat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

class ChatClient extends AsyncTask<Object, String, String> {

	@Override
	protected String doInBackground(Object... params) {
		String ip = (String) params[0];
		int port = (Integer) params[1];
		try {
			Socket clientSocket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			Log.e("CLIENT", e.getMessage());
		} catch (IOException e) {
			Log.e("CLIENT", e.getMessage());
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