package com.work.synthesizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ImportName extends Activity {

	Button confirm;
	String filepath;
	TextView importPath;
	EditText name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.importname);
		confirm = (Button) findViewById(R.id.importConfirm);
		importPath = (TextView) findViewById(R.id.importPath);
		name = (EditText) findViewById(R.id.importNameText);
		Intent importintent = getIntent();
		filepath = importintent.getStringExtra("GetPath");
		importPath.setText(filepath);


		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// tests, if there's a path entered. if so, give the name back as argument and finish this activity
				if(name.getText().toString()!="...path...")
				{
		    	Intent nameback = new Intent();
		        nameback.putExtra("Name",name.getText().toString());
		        nameback.putExtra("chosenFile", filepath);
		        setResult(RESULT_OK, nameback);
		        finish();
				}
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
