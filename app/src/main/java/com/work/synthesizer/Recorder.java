package com.work.synthesizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Recorder extends Activity {
	
	private static final int REQUEST_PATH = 1;
	private static final String TAG = "VoiceRecord";

	private static final int RECORDER_SAMPLERATE = 44100;     
	private static final int RECORDER_CHANNELS_IN = AudioFormat.CHANNEL_IN_MONO;
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

	private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
	
    String filePath = Environment.getExternalStorageDirectory()+"/synthesizer/temp/audiorecording.pcm";
	Audioplayer ap = new Audioplayer(filePath);


	// Initialize minimum buffer size in bytes.
	private int bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_IN, RECORDER_AUDIO_ENCODING);

	private AudioRecord recorder = null;
	private Thread recordingThread = null;
	private boolean isRecording = false;
	MediaPlayer mediaplayer;
    
	boolean rec = false;
    boolean playing = false;
    String colorStringRed = "#FF0000";
    String colorStringGreen = "#84FF6B";
    boolean recorded,stored = false;
    String storedfilepath;



	
	Button record, listen, store, play, menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorder);		
		record = (Button) findViewById(R.id.startRecord);
		listen = (Button) findViewById(R.id.listenFromRecorder);
		store = (Button) findViewById(R.id.storeFromRecorder);
		menu = (Button) findViewById(R.id.recorderToMenu);
		play = (Button) findViewById(R.id.playFromRecorder);
		
		record.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		listen.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		store.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		menu.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		play.getBackground().setColorFilter(new LightingColorFilter(0x4FFFFFFF, 0xFFFFFFFF));
		
		
		record.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ***** record sample *****
				if (rec) {
					record.setText("Record");
					recorded = true;
					rec = false;
					record.setBackgroundColor(Color.GRAY);
					//record.setBackgroundColor(Color.parseColor(colorStringGreen));
					try{
						stopRecording();
					}catch(Exception e) {
						e.printStackTrace();
					}
					return;
				}
				else {
					rec = true;
					record.setText("Stop");
					record.setBackgroundColor(Color.parseColor(colorStringRed));
					try{
					startRecording();
					}catch(Exception e) {
						e.printStackTrace();
					} 
					return;
				}

			}
		});
		listen.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap.PlayAudioFileViaAudioTrack(filePath);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			    	
			    	break;
			    	
			    case MotionEvent.ACTION_UP:
			    	Log.d(TAG, "action up");
			    	v.performClick();
					// ***** stop recorded sample *****
					try {
						ap.StopAudioFileViaAudioTrack(ap.at);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			        break;
			    default:
			        break;
			    }
			    return true;
			}
			
		});
		store.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(recorded && !rec)
				{
				Intent toStore = new Intent("android.intent.action.importname");
				toStore.putExtra("GetPath", filePath);
		        startActivityForResult(toStore,REQUEST_PATH);   
				}				
			}
		});
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!rec)
				{
				if(stored)
				{
				Intent toKeyboard = new Intent("android.intent.action.keyboard");
				toKeyboard.putExtra("Directory", storedfilepath);
				startActivity(toKeyboard);
				}
				else{
					Intent toChoosefile = new Intent("android.intent.action.choosefile");
					startActivity(toChoosefile);
				}
				}
			}
		});
		
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toMenu = new Intent("android.intent.action.mainActivity");
				startActivity(toMenu);
			}
		});
		
	}
	
	private void startRecording() {
	    if( bufferSize == AudioRecord.ERROR_BAD_VALUE)
	        Log.e( TAG, "Bad Value for \"bufferSize\", recording parameters are not supported by the hardware");

	    if( bufferSize == AudioRecord.ERROR )
	        Log.e( TAG, "Bad Value for \"bufferSize\", implementation was unable to query the hardware for its output properties");

	    Log.e( TAG, "\"bufferSize\"="+bufferSize);

	    // Initialize Audio Recorder.    
	    recorder = new AudioRecord(AUDIO_SOURCE, RECORDER_SAMPLERATE, RECORDER_CHANNELS_IN, RECORDER_AUDIO_ENCODING, bufferSize);
	    // Starts recording from the AudioRecord instance.
	    recorder.startRecording();

	    isRecording = true;

	    recordingThread = new Thread(new Runnable() {
	        public void run() {
	            writeAudioDataToFile();
	        }
	    }, "AudioRecorder Thread");
	    recordingThread.start();
	}

	private void writeAudioDataToFile() {
	    //Write the output audio in byte
;
	    byte saudioBuffer[] = new byte[bufferSize];

	    FileOutputStream os = null;
	    try {
	        os = new FileOutputStream(filePath);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    while (isRecording) {
	        // gets the voice output from microphone to byte format
	        recorder.read(saudioBuffer, 0, bufferSize);
	        try {
	            //  writes the data to file from buffer stores the voice buffer
	            os.write(saudioBuffer, 0, bufferSize);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    try {
	        os.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	private void writeAudioDataToFileTest() {
	    //Write the output audio in byte
;
	    byte saudioBuffer[] = new byte[bufferSize];
	    
	    FileOutputStream os = null;
	    try {
	        os = new FileOutputStream(filePath);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    while (isRecording) {
	        // gets the voice output from microphone to byte format
	        recorder.read(saudioBuffer, 0, bufferSize);
	        byte[] sinebuffer = Audioplayer.createSinWaveBuffer(440, 1000);
	        try {
	            //  writes the data to file from buffer stores the voice buffer
	        	os.write(sinebuffer, 0, bufferSize);

	        	//os.write(saudioBuffer, 0, bufferSize);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    try {
	        os.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private void stopRecording() throws IOException {
	    //  stops the recording activity
	    if (null != recorder) {
	        isRecording = false;  
	        recorder.stop();
	        recorder.release();
	        recorder = null;
	        recordingThread = null;
	    }   
	}

	/*
	private void PlayAudioFileViaAudioTrack(String filePath) throws IOException{
	    if (filePath==null)
	        return;

	    //Reading the file.. 
	    File file = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or "/sdcard/samplesound.wav"
	    byte[] byteData = new byte[(int) file.length()];
	    Log.d(TAG,"Filelengh: " + (int) file.length()+"");

	    FileInputStream in = null;
	    try {
	        in = new FileInputStream( file );
	        in.read( byteData );
	        in.close(); 
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    // Set and push to audio track..
	    int intSize = android.media.AudioTrack.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING); 
	    Log.d(TAG,"intsize: " + intSize+"");

	    at = new AudioTrack(AudioManager.STREAM_MUSIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING, intSize, AudioTrack.MODE_STREAM); 
	    if (at!=null) { 
	        at.play();
	        // Write the byte array to the track
	        at.write(byteData, 0, byteData.length); 
	        
	        //StopAudioFileViaAudioTrack(at);
	        //at.stop();
	        //at.release();
	    }
	    else
	        Log.d(TAG, "audio track is not initialised ");

	}
	private void StopAudioFileViaAudioTrack(AudioTrack at) throws IOException{
		at.flush();
        at.stop();
        at.release();

	}
	*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    	if (requestCode == REQUEST_PATH){
    		if (resultCode == RESULT_OK) { 
    	    	String filename = data.getStringExtra("Name");
    	    	storedfilepath = Environment.getExternalStorageDirectory().getPath() + "/" + "synthesizer/" + filename;
    	    	File fileToName = new File(storedfilepath);
    	    	if(fileToName.exists())
    	    	{
    				new AlertDialog.Builder(this)
    				.setIcon(R.drawable.ic_launcher)
    				.setTitle("Theres already a sample with this name!")
    				.setPositiveButton("OK", null).show();
    				Intent toRecorder = new Intent("android.intent.action.recorder");  
    				startActivity(toRecorder);
    	    	}
    	    	fileToName.mkdir();
    	    	
    	    	// *****    import/convert file *****  (filepath:file, to directory ../synthesizer/temp/audiorecording.aac)
    	    	double[] input = Audioplayer.ShortToDouble(Audioplayer.PcmToShort(filePath));
    	    	
    	    	pvLibrary.WriteOctave(input, storedfilepath);
    	    	
//    	    	PhaseVocoder pv = new PhaseVocoder();
//    	    	double[] output = pv.shift(input,3);
//    	    	output = pv.shift(output,3);
//    	    	
//    	    	WavFile wavFile;
//				try {
//					wavFile = WavFile.newWavFile(new File(storedfilepath + "/rectest.wav"), 1, output.length, 16, RECORDER_SAMPLERATE);
//					wavFile.writeFrames(output, output.length);
//					wavFile.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (WavFileException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
    	    	

    	    	stored = true;
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
