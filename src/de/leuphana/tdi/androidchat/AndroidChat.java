package de.leuphana.tdi.androidchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AndroidChat extends Activity implements OnClickListener {

	private EditText editIp, editPort, editusername;
	private Button buttonClient;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		editusername = (EditText) findViewById(R.id.editText_username);
		editIp = (EditText) findViewById(R.id.editText_ip);
		editPort = (EditText) findViewById(R.id.editText_port);
		buttonClient = (Button) findViewById(R.id.button_client);
		
		buttonClient.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v.equals(buttonClient)) {
			try {
				String ip = editIp.getText().toString();
				int port = new Integer(editPort.getText().toString());
			} catch (Exception e) {
				Toast.makeText(this, "IP oder Port ung�ltig.", Toast.LENGTH_SHORT).show();
			}
			Intent i = new Intent(this, ChatWindow.class);
			startActivity(i);
		}
	}
}