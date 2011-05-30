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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatWindow extends ListActivity {
	private ArrayList<ChatListItem> chatListItems;
	private MenuAdapter menuAdapter;
	private EditText message;
	private Button send;
	private ChatClient chatClient;

	@Override
	public void onPause() {
		chatClient = null;
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

		message = (EditText) findViewById(R.id.editText_message);
		send = (Button) findViewById(R.id.button_sendMessage);

		send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

			}
		});

		Object[] params = { "192.168.1.23", 14911 };
		chatClient = new ChatClient();
		chatClient.execute(params);
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
				TextView messageName = (TextView) findViewById(R.id.textView_messagename);
				TextView messageContent = (TextView) findViewById(R.id.textView_messagecontent);

				if (o.getUsername() != null && o.getMessage() != null) {
					if (messageName != null && messageContent != null) {
						messageName.setText(o.getUsername());
						messageContent.setText(o.getMessage());
					}
				}
			}
			return v;
		}
	}

	private class ChatClient extends AsyncTask<Object, ChatListItem, String> {

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
			ChatListItem item = new ChatListItem();
			item.setUsername("testUser");
			item.setMessage("testMessage");
			publishProgress(item);
			return null;
		}

		@Override
		protected void onProgressUpdate(ChatListItem... params) {
			menuAdapter.add(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {

		}
	}
}
