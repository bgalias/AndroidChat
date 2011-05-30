package de.leuphana.tdi.androidchat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ChatWindow extends ListActivity {
	private ArrayList<ChatListItem> chatListItems;
	private MenuAdapter menuAdapter;
	
	@Override
	public void onPause(){
		super.onPause();
		this.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatwindow);

		// Create the Listadapter
		chatListItems = new ArrayList<ChatListItem>();
		this.menuAdapter = new MenuAdapter(this, R.layout.chatrow,
				chatListItems);
		setListAdapter(this.menuAdapter);
	}

	private class MenuAdapter extends ArrayAdapter<ChatListItem> {
		private ArrayList<ChatListItem> items;

		public MenuAdapter(Context context, int textViewResourceId,
				ArrayList<ChatListItem> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.chatrow, null);
			}
			ChatListItem o = items.get(position);
			if (o != null) {
				// TextView itemDiscription = (TextView)
				// v.findViewById(R.id.discription);
				// TextView itemDate = (TextView) v.findViewById(R.id.date);
				// TextView itemContent = (TextView)
				// v.findViewById(R.id.content);
				//
				// if (itemDiscription != null) {
				// itemDiscription.setText(o.getDiscription());
				// }
				// if (itemDate != null) {
				// itemDate.setText(o.getDate());
				// }
				// if (itemContent != null) {
				// itemContent.setText(o.getContent());
			}
			return v;
		}
	}

	private class ChatClient extends AsyncTask<Object, String, String> {

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
			// for (int i = 0; i < anamnesisMenuItems.size(); i++)
			// menuAdapter.add(anamnesisMenuItems.get(i));
		}

		@Override
		protected void onPostExecute(String result) {

		}
	}
}
