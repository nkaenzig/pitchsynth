package com.work.synthesizer;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class Preview extends Activity {

	RelativeLayout own;
	String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/synthesizer/" ;
//	String root = Environment.getExternalStorageDirectory().getPath() + "/" + "synthesizer";
	String temp = root + "temp";
	File file = new File(root);
	File filetemp = new File(temp);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		if(!file.exists())
			if(!file.mkdir())
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("Unable to create directory for synthesizer")
				.setPositiveButton("OK", null).show();
		if(!filetemp.exists())
			if(!filetemp.mkdir())
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("Unable to create directory temp for synthesizer")
				.setPositiveButton("OK", null).show();
		
		
		own = (RelativeLayout) findViewById(R.id.previewid);
	

	
	own.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent toMenu = new Intent("android.intent.action.mainActivity");
			startActivity(toMenu);
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
