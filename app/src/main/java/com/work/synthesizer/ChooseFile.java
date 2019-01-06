package com.work.synthesizer;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChooseFile extends Activity {
	
	// ***** show list of samples to choose *****
	
	private static final int REQUEST_PATH = 1;
	 
	String curFileName;
	
	EditText edittext;
	
	Button back, importFiles, browse, delete;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosefile);
		importFiles = (Button) findViewById(R.id.importFiles);
		browse = (Button) findViewById(R.id.chooseFileBrowser);
		back = (Button) findViewById(R.id.playerToMenu);
		delete = (Button) findViewById(R.id.deleteFiles);
		
		// edit button color
		//importFiles.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);	// first two numbers of integer are for alpha
		//importFiles.getBackground().setColorFilter(Color.parseColor("#fbfbfb") PorterDuff.Mode.LIGHTEN);
		importFiles.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF)); // first is lighting, second is additional color
		browse.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		back.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		delete.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent toMenu = new Intent("android.intent.action.mainActivity");  
				startActivity(toMenu);
			}
		});
	}
	
   public void getfile(View view){ 
    	Intent getFile = new Intent("android.intent.action.fileexplorer");
        startActivity(getFile);
    }
   
   public void getfiledelete(View view){ 
   	Intent getFile = new Intent("android.intent.action.filedelete");
       startActivity(getFile);
   }
    
    public void getfileimport(View view){ 
    	Intent importfile = new Intent(this, FileImport.class);
        startActivity(importfile);
    }
 // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
    	if (requestCode == REQUEST_PATH){
    		if (resultCode == RESULT_OK) { 
    			curFileName = data.getStringExtra("GetFileName"); 
            	edittext.setText(curFileName);
    		}
    	 }
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
