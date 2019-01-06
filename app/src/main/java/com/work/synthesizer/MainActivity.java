package com.work.synthesizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button record, play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity);
		record = (Button) findViewById(R.id.MenuToRecord);
		play = (Button) findViewById(R.id.MenuToChooseFile);
		
		record.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		play.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		
		record.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toRecorder = new Intent("android.intent.action.recorder");
				startActivity(toRecorder);
			}
		});
		
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toChooseFile = new Intent("android.intent.action.choosefile");
				startActivity(toChooseFile);
				
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
