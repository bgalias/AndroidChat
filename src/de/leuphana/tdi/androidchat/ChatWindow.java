package de.leuphana.tdi.androidchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
				Log.e("CHAT", "CLICK");
				try {
					Socket socket = new Socket("192.168.1.19", 14911);
					PrintWriter printWriter = new PrintWriter(
							new OutputStreamWriter(socket.getOutputStream()));
					printWriter.print(message.getText().toString());
					printWriter.flush();
					printWriter.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Object[] params = { "192.168.1.19", 14911 };
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

				if (o.getMessage() != null) {
					if (messageName != null && messageContent != null) {
						messageName.setText("USER");
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
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));

				while (true) {
					String line = null;
					StringBuilder message = new StringBuilder();
					while ((line = reader.readLine()) != null) {
						message.append(line);
					}
					ChatListItem item = new ChatListItem();
					item.setMessage(message.toString());
					publishProgress(item);
				}
			} catch (UnknownHostException e) {
				Log.e("CLIENT", e.getMessage());
			} catch (IOException e) {
				Log.e("CLIENT", e.getMessage());
			}
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
