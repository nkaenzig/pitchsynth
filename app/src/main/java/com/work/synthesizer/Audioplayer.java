package com.work.synthesizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class Audioplayer {
	static final String TAG = "Audioplayer";
	static final int RECORDER_SAMPLERATE = 44100;
	static final int RECORDER_CHANNELS_IN = AudioFormat.CHANNEL_IN_MONO;
	static final int RECORDER_CHANNELS_OUT = AudioFormat.CHANNEL_OUT_MONO;
	static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

	static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;

	AudioTrack at;
	boolean audiostopped = false;
	String filePath;
	String TestfilePath=Environment.getExternalStorageDirectory()+"/synthesizer/sinetest.pcm";
	Thread playthread;
	File file;

	Audioplayer(String filePath) {
		this.filePath = filePath;
	}

	void PlayAudioFileViaAudioTrack(String filePath) throws IOException {
		if (filePath == null) {
			Log.e(TAG, "filepath = null");
			return;
		}

		// Reading the file..
		File file = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or"/sdcard/samplesound.wav"
		final byte[] byteData = new byte[(int) file.length()];
		Log.d(TAG, "Filelengh: " + (int) file.length() + "");		
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			in.read(byteData);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		// For Debuging
//		//final byte[] TestbyteData = createSinWaveBuffer(440, 1000);
//		//short[] shortarray1 = ByteToShort(TestbyteData);
//		short[] array2 = PcmToShort(filePath);
//		for (int i=0; i<(900); i++ ) {
//		//Log.e(TAG, "ByteToShort: shortarray1["+i+"] = " + shortarray1[i]);
//			if (array2[i] !=0)
//		Log.e(TAG, "PcmToshort:  array2["+i+"] = " + array2[i]);
//		};

		

		// Set and push to audio track..
		int intSize = AudioTrack.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING);
		Log.d(TAG, "intsize: " + intSize + "");
		int intSize2 = 512/2;

		at = new AudioTrack(AudioManager.STREAM_MUSIC, RECORDER_SAMPLERATE,	RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING, intSize,
				AudioTrack.MODE_STREAM);
		Log.e(TAG, "Samplerate = " + at.getSampleRate());


		if (at != null) {
			at.play();
			audiostopped = false;

			// Write the byte array to the track
			playthread = new Thread(new Runnable() {
				public void run() {
					Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

					while (!audiostopped) {
						Log.e(TAG, "Stop");
						at.write(byteData, 0, byteData.length);
						//Log.e(TAG, "Bytedatalength = " + byteData.length);
						

					}
//					Log.e(TAG, "while loop exited");
					
				}
			});
//			Log.e(TAG, "start:");

			playthread.start();

		} else
			Log.d(TAG, "audio track is not initialised ");

	}
	
	
	void initializeAudioplayer(String filePath) {
		
		if (filePath == null) {
			Log.e(TAG, "filepath = null");
			return;
		}

		// Reading the file..
		File file = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or"/sdcard/samplesound.wav"
		final byte[] byteData = new byte[(int) file.length()];
		Log.d(TAG, "Filelengh: " + (int) file.length() + "");	
		Log.d(TAG, "Filelengh: " + (int) byteData.length + "");	

		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			in.read(byteData);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int intSize = AudioTrack.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING);
		int intSize2 = 124;

		//Log.d(TAG, "intsize2: " + intSize2 + "");

		at = new AudioTrack(AudioManager.STREAM_MUSIC, RECORDER_SAMPLERATE,	RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING,(int) file.length(),
				AudioTrack.MODE_STATIC);
		at.write(byteData, 0, byteData.length);
		at.setLoopPoints(0, byteData.length/2-20, -1);
		

		return;		
	}
	
	void PlayByteArray(final byte[] bytedata){

		if (at != null) {
			//at.play();
			audiostopped = false;

			// Write the byte array to the track
			playthread = new Thread(new Runnable() {
				public void run() {
					Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
					Log.e(TAG, "Stop");

					while (!audiostopped) {
						//Log.e(TAG, "Stop");

						at.play();
						

						//at.write(bytedata, 0, bytedata.length);
						//Log.e(TAG, "Bytedatalength = " + byteData.length);
						

					}
					Log.e(TAG, "while loop exited");
//					at = new AudioTrack(AudioManager.STREAM_MUSIC, RECORDER_SAMPLERATE,	RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING,(int) bytedata.length,
//							AudioTrack.MODE_STATIC);
//					at.write(bytedata, 0, bytedata.length);


				}
			});
//			Log.e(TAG, "start;");

			playthread.start();

		} else
			Log.d(TAG, "audio track is not initialised ");

		
	}
	
	void play(){
		at.play();

	}

	void StopAudioFileViaAudioTrack(AudioTrack at) throws IOException {
		audiostopped = true;
		at.stop();
		at.release();
		at = null;
		playthread = null;
		
	}
	
	void stop(AudioTrack at, int decay) throws IOException {
		for (float i=0; i<decay; i++){
			at.setStereoVolume(1-10-i/decay, 1-10-i/decay);					
		}
		
		at.pause();
		at.setPlaybackHeadPosition(0);
		at.setStereoVolume(1, 1);

		
	}

	
	
	   public static byte[] createSinWaveBuffer(double freq, int ms) {
	       int samples = (int)((ms * RECORDER_SAMPLERATE) / 1000);
	       byte[] output = new byte[samples];
	       
	       double period = (double)RECORDER_SAMPLERATE / freq;
	       for (int i = 0; i < output.length; i++) {
	           double angle = 2.0 * Math.PI * i / period;
	           output[i] = (byte)(Math.sin(angle) * 127f);  }

	       return output;
	   }


	   public static short[] PcmToShort(String filePath) {
		
			if (filePath == null) {
				Log.e(TAG, "filepath = null");
				return null;
			}
			// Reading the file..
			Log.e(TAG, "filepath: " + filePath);
			File file2 = new File(filePath); // for ex. path= "/sdcard/samplesound.pcm" or"/sdcard/samplesound.wav"
			final byte[] bytes2 = new byte[(int) file2.length()];
			

			
			FileInputStream in2 = null;
			try {
				in2 = new FileInputStream(file2);
				in2.read(bytes2);
				in2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			short[] bts = ByteToShort(bytes2);
		   return bts;
		   
		   
	   }

	   public static short[] ByteToShort(byte[] bytes) {

		   short[] shorts = new short[bytes.length/2];
		// to turn bytes to shorts as either big endian or little endian. 
		ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
		return shorts;
		


		   //		    short[] out = new short[bytes.length / 2]; // will drop last byte if odd number
//		    ByteBuffer bb = ByteBuffer.wrap(bytes);
//		    for (int i = 0; i < out.length; i++) {
//		        out[i] = bb.getShort();
//		    }
//		    return out;
		}

		public static double[] ShortToDouble(short[] x){
			double[] y = new double[x.length];
			for(int i=0; i<x.length; i++){
				y[i] = (double)x[i];
				if(y[i] < 0) y[i] /= 32768.0;
				else y[i] /= 32767.0;
			}
			return y;
			
		}
		
		public static void WriteDoubleToPCM(String filepath, double[] x){
			
			//Find start of recording
			int start = 0;
			while(start < x.length) 
			{
				if(x[start] > 1e-6) break;
				start++;
			}
			
			//Double to 16bit short
			short[] y = new short[x.length-start];
			double tmp;
			for(int i=start; i<x.length; i++){
				if(x[i] < 0) tmp = x[i]*32768.0;
				else tmp = x[i]*32767.0;
				y[i-start] = (short)tmp;
			}
			
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(filepath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byte buffer[] = new byte[2*y.length];
			
			int j = 0;
			for(int i=0; i<y.length; i++){
				
				//short to byte conversion
				buffer[j] = (byte)(y[i] & 0xff);
				buffer[j+1] = (byte)((y[i] >> 8) & 0xff);
				j += 2;
			}
			
			try {
				out.write(buffer, 0, buffer.length);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	   
	   
//	   public static double[] load16BitPCMRawDataFileAsDoubleArray(String filePath) {
//			File file = new File(filePath);
//		    InputStream in = null;
//		    if (file.isFile()) {
//		      long size = file.length();
//		      try {
//		        in = new FileInputStream(file);
//		        return readStreamAsDoubleArray(in, size);
//		      } catch (Exception e) {
//		      }
//		    }
//		    return null;
//		  }
//
//		  public static double[] readStreamAsDoubleArray(InputStream in, long size)
//		      throws IOException {
//		    int bufferSize = (int) (size / 2);
//		    double[] result = new double[bufferSize];
//		    DataInputStream is = new DataInputStream(in);
//		    for (int i = 0; i < bufferSize; i++) {
//		      result[i] = is.readShort() / 32768.0;
//		    }
//		    return result;
//		  }


}
