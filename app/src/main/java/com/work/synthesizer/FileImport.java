package com.work.synthesizer;



import java.io.File;
import java.io.IOException;
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

public class FileImport extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;
	private static final int REQUEST_PATH = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fileimport);
        myPath = (TextView)findViewById(R.id.path);
        
        root = Environment.getExternalStorageDirectory().getPath();
        
        getDir(root);
    }
    
    // file explorer
    private void getDir(String dirPath)
    {
    	myPath.setText("Location: " + dirPath);
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	
    	if(!dirPath.equals(root))
    	{
    		item.add(root);
    		path.add(root);
    		item.add("../");
    		path.add(f.getParent());	
    	}
    	
    	for(int i=0; i < files.length; i++)
    	{
    		File file = files[i];
    		
    		if(!file.isHidden() && file.canRead()){
    			path.add(file.getPath());
        		if(file.isDirectory()){
        			item.add(file.getName() + "/");
        		}else{
        			item.add(file.getName());
        		}
    		}	
    	}

    	// Linker
    	ArrayAdapter<String> fileList =
    			new ArrayAdapter<String>(this, R.layout.fileimport_row, item);
    	setListAdapter(fileList);	
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(path.get(position));
		
		if (file.isDirectory())
		{
			if(file.canRead()){
				getDir(path.get(position));
			}else{
				new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("[" + file.getName() + "] folder can't be read!")
					.setPositiveButton("OK", null).show();	
			}	
		}else {
			// ask for name of the sample with path: file
	    	Intent getname = new Intent(this, ImportName.class);
	        getname.putExtra("GetPath",file.toString());
	        startActivityForResult(getname,REQUEST_PATH);   
		  }
	}
	
	// get name from ImportName and create a directory with the name for import
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    	if (requestCode == REQUEST_PATH){
    		if (resultCode == RESULT_OK) { 
    	    	String filename = data.getStringExtra("Name");
    	    	String filepath = Environment.getExternalStorageDirectory().getPath() + "/" + "synthesizer/" + filename;
    	    	File fileToName = new File(filepath);
    	    	if(fileToName.exists())
    	    	{
    				new AlertDialog.Builder(this)
    				.setIcon(R.drawable.ic_launcher)
    				.setTitle("Theres already a sample with this name!")
    				.setPositiveButton("OK", null).show();
    				Intent toChoosefile = new Intent("android.intent.action.choosefile");  
    				startActivity(toChoosefile);
    	    	}
    	    	fileToName.mkdir();
    	    	
    	    	// *****    import/convert file *****  (filepath:file, to directory ../synthesizer/filename)
    	    	
    	    	// only for 44100 sr so far!!!
    	    	WavFile wav;
				try {
					wav = WavFile.openWavFile(new File(data.getStringExtra("chosenFile")));
					int N = (int) wav.getNumFrames();
	    			int numChannels = wav.getNumChannels();
	    			double[] input = new double[N*numChannels];
	    			wav.readFrames(input, N);
	    			//long fs = wav.getSampleRate();
	    	    	
	    	    	pvLibrary.WriteOctave(input, filepath);
	    			
//	    			WavFile wavFile;
//					wavFile = WavFile.newWavFile(new File(filepath + "/test.wav"), 1, output.length, 16, fs);
//					wavFile.writeFrames(output, output.length);
//					wavFile.close();
	    			
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WavFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    	    	  			
    			Intent toChoosefile = new Intent("android.intent.action.choosefile");  
    			startActivity(toChoosefile);
    		}
    	 }
    }

}