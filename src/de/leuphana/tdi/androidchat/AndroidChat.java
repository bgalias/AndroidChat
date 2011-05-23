package de.leuphana.tdi.androidchat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class AndroidChat extends Activity {
	
	private AsyncTask<Integer, String, String> chatClient;
	private Integer port;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        chatClient = new ChatClient().execute(port);
    }
    
    private class ChatClient extends AsyncTask<Integer, String, String>{

		@Override
		protected String doInBackground(Integer... params) {
			try {
				DatagramSocket clientSocket = new DatagramSocket(params[0]);
				DatagramPacket packet = new DatagramPacket(new byte[512], 512);
				
				while(true){
					clientSocket.receive(packet);
					publishProgress(new String(packet.getData(),0,packet.getLength()));
				}
			} catch (Exception e) {
			}
			return null;
		}
    	
		@Override
		protected void onProgressUpdate(String... params){
			
		}
		
		@Override
		protected void onPostExecute(String result){
			
		}
    }
}