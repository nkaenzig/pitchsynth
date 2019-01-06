package com.work.synthesizer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FileDelete extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filedelete);
        myPath = (TextView)findViewById(R.id.path);
        
        root = Environment.getExternalStorageDirectory().getPath() + "/synthesizer";
        getDir(root);
    }
    
    private void getDir(String dirPath)
    {
    	myPath.setText("Choose the samples you want to delete:");
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	// path.add("Delete all");	// option to delete all files at the time
    	// item.add("_delete all");
    	for(int i=0; i < files.length; i++)
    	{
    		File file = files[i];
    		
    		String tempstring = root + "/temp";

    		if(!file.isHidden() && file.canRead() && !(file.toString().equals(tempstring))){
    			path.add(file.getPath());
        		if(file.isDirectory()){
        			item.add(file.getName());
        		}else{
        			item.add(file.getName() + " *not chooseable*");
        		}
    		}	
    	}

    	ArrayAdapter<String> fileList =
    			new ArrayAdapter<String>(this, R.layout.filedelete_row, item);
    	setListAdapter(fileList);	
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(path.get(position));
		// if(position==0)
				// delete all... 
		if (file.isDirectory())
		{
			if(file.canRead()){
				// delete all files in the directory first
			        String[] children = file.list();
			        for (int i = 0; i < children.length; i++) {
			            new File(file, children[i]).delete();
			        }
			        file.delete();	// delete directory itself
				getDir(root);
			}else{
				new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("[" + file.getName() + "] folder can't be read!")
					.setPositiveButton("OK", null).show();	
				getDir(root);
			}	
		}else {
			
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.ic_launcher)
			.setTitle("This file can not be chosen!")
			.setPositiveButton("OK", null).show();
			getDir(root);
		  }
	}

}