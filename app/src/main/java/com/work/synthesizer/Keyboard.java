package com.work.synthesizer;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Keyboard extends Activity {
	
	Button options, choose, menu;
	Button one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve;
	TextView keyboardpath;
	String filepath;
	String filename;
	File file;
	String TAG = "play";
	
//	/******Soundpool*******/
//	SoundPool soundPool;
//	HashMap<Integer, Integer> soundPoolMap;
//	int soundID = 1;
//	/******Soundpool*******/

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyboard);	
		options = (Button) findViewById(R.id.optionsKeyboard);
		choose = (Button) findViewById(R.id.chooseKeyboard);
		menu = (Button) findViewById(R.id.keyboardToMenu);
		Intent filename = getIntent();
		filepath = filename.getStringExtra("Directory");
		file = new File(filepath);
		keyboardpath = (TextView) findViewById(R.id.keyboardPath);
		keyboardpath.setText("Filename: " + file.getName());
		
		final Audioplayer ap = new Audioplayer(filepath);
		final Audioplayer ap1 = new Audioplayer(filepath);
		final Audioplayer ap2 = new Audioplayer(filepath);
		final Audioplayer ap3 = new Audioplayer(filepath);
		final Audioplayer ap4 = new Audioplayer(filepath);
		final Audioplayer ap5 = new Audioplayer(filepath);
		final Audioplayer ap6 = new Audioplayer(filepath);
		final Audioplayer ap7 = new Audioplayer(filepath);
		final Audioplayer ap8 = new Audioplayer(filepath);
		final Audioplayer ap9 = new Audioplayer(filepath);
		final Audioplayer ap10 = new Audioplayer(filepath);
		final Audioplayer ap11 = new Audioplayer(filepath);
		final Audioplayer ap12 = new Audioplayer(filepath);

		
		final int decay = 10000;



		one = (Button) findViewById(R.id.button01);
		two = (Button) findViewById(R.id.button02);
		three = (Button) findViewById(R.id.button03);
		four = (Button) findViewById(R.id.button04);
		five = (Button) findViewById(R.id.button05);
		six = (Button) findViewById(R.id.button06);
		seven = (Button) findViewById(R.id.button07);
		eight = (Button) findViewById(R.id.button08);
		nine = (Button) findViewById(R.id.button09);
		ten = (Button) findViewById(R.id.button10);
		eleven = (Button) findViewById(R.id.button11);
		twelve = (Button) findViewById(R.id.button12);
		settilesize();
		
		ap1.initializeAudioplayer(filepath + "/1.pcm");
		ap2.initializeAudioplayer(filepath + "/2.pcm");
		ap3.initializeAudioplayer(filepath + "/3.pcm");
		ap4.initializeAudioplayer(filepath + "/4.pcm");
		ap5.initializeAudioplayer(filepath + "/5.pcm");
		ap6.initializeAudioplayer(filepath + "/6.pcm");
		ap7.initializeAudioplayer(filepath + "/7.pcm");
		ap8.initializeAudioplayer(filepath + "/8.pcm");
		ap9.initializeAudioplayer(filepath + "/9.pcm");
		ap10.initializeAudioplayer(filepath + "/10.pcm");
		ap11.initializeAudioplayer(filepath + "/11.pcm");
		ap12.initializeAudioplayer(filepath + "/12.pcm");



		
		options.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// ***** may fragment or normally activity for some further options to play sample *****
			}
		});
		choose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent toPlayer = new Intent("android.intent.action.choosefile");  // ***** could replace it by fragment *****
				startActivity(toPlayer);
				
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
		
		one.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down 1");
					// ***** play recorded sample *****
					try {
						ap1.play();
						//ap.PlayAudioFileViaAudioTrack(filepath + "/1.pcm");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			    	
			    	break;
			    	
			    case MotionEvent.ACTION_UP:
			    	Log.d(TAG, "action up 1");
			    	v.performClick();
					// ***** stop recorded sample *****
					try {
						ap1.stop(ap1.at, decay);
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
		
		two.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down 2");
					// ***** play recorded sample *****
					try {
						ap2.play();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			    	
			    	break;
			    	
			    case MotionEvent.ACTION_UP:
			    	Log.d(TAG, "action up 2");
			    	v.performClick();
					// ***** stop recorded sample *****
					try {
						ap2.stop(ap2.at, decay);
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
		
		three.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down 3");
					// ***** play recorded sample *****
					try {
						ap3.play();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			    	
			    	break;
			    	
			    case MotionEvent.ACTION_UP:
			    	Log.d(TAG, "action up 3");
			    	v.performClick();
					// ***** stop recorded sample *****
					try {
						ap3.stop(ap3.at, decay);
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
		
		four.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down 4");
					// ***** play recorded sample *****
					try {
						ap4.play();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			    	
			    	break;
			    	
			    case MotionEvent.ACTION_UP:
			    	Log.d(TAG, "action up 4");
			    	v.performClick();
					// ***** stop recorded sample *****
					try {
						ap4.stop(ap4.at, decay);
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
		
		five.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down 5");
					// ***** play recorded sample *****
					try {
						ap5.play();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			    	
			    	break;
			    	
			    case MotionEvent.ACTION_UP:
			    	Log.d(TAG, "action up 5");
			    	v.performClick();
					// ***** stop recorded sample *****
					try {
						ap5.stop(ap5.at, decay);
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
		
		six.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap6.play();
						//ap.PlayAudioFileViaAudioTrack(filepath + "/6.pcm");
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
						ap6.stop(ap6.at, decay);

						//ap.StopAudioFileViaAudioTrack(ap.at);
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
		
		seven.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap7.play();
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
						ap7.stop(ap7.at, decay);
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
		
		eight.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap8.play();
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
						ap8.stop(ap8.at, decay);
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
		
		nine.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap9.play();
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
						ap9.stop(ap9.at, decay);
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
		
		ten.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap10.play();
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
						ap10.stop(ap10.at, decay);
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
		
		eleven.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap11.play();
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
						ap11.stop(ap11.at, decay);
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
		
		twelve.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	Log.d(TAG, "action down");
					// ***** play recorded sample *****
					try {
						ap12.play();
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
						ap12.stop(ap12.at, decay);
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
		
	}
	
	// initialize the keyboard tiles, set position and there width and height
	void settilesize()
	{
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);	
		int whitewidth = (size.x-20)/7;
		int newheight = (size.y);
		int settop = 190;
		float blackfactor = 1.7f;			// shorten the black tiles by this factor from white tiles. ONLY change this value!
		float blacksizefloat = ((float) whitewidth)/blackfactor;
		int blackwidth = (int) blacksizefloat;
		float offset = 1f - 0.5f/blackfactor;

			one.setX(0);
			two.setX(offset*whitewidth);
			three.setX(whitewidth);
			four.setX((1+offset)*whitewidth);
			five.setX(2*whitewidth);
			six.setX(3*whitewidth);
			seven.setX((3+offset)*whitewidth);
			eight.setX(4*whitewidth);
			nine.setX((4+offset)*whitewidth);
			ten.setX(5*whitewidth);
			eleven.setX((5+offset)*whitewidth);
			twelve.setX(6*whitewidth);
			
			one.setY(settop);
			two.setY(settop);
			three.setY(settop);
			four.setY(settop);
			five.setY(settop);
			six.setY(settop);
			seven.setY(settop);
			eight.setY(settop);
			nine.setY(settop);
			ten.setY(settop);
			eleven.setY(settop);
			twelve.setY(settop);
			
		    ViewGroup.LayoutParams paraone = one.getLayoutParams();
		    paraone.width = whitewidth;
		    paraone.height = newheight;
		    one.setLayoutParams(paraone);
		    
		    ViewGroup.LayoutParams paratwo = two.getLayoutParams();
		    paratwo.width = blackwidth;
		    paratwo.height = newheight/3;
		    //two.setBackgroundColor(Color.BLACK);
		    two.setLayoutParams(paratwo);
		    
		    ViewGroup.LayoutParams parathree = three.getLayoutParams();
		    parathree.width = whitewidth;
		    parathree.height = newheight;
		    //three.setBackgroundColor(Color.WHITE);
		    three.setLayoutParams(parathree);
		    
		    ViewGroup.LayoutParams parafour = four.getLayoutParams();
		    parafour.width = blackwidth;
		    parafour.height = newheight/3;
		    four.setLayoutParams(parafour);
		    
		    ViewGroup.LayoutParams parafive = five.getLayoutParams();
		    parafive.width = whitewidth;
		    parafive.height = newheight;
		    five.setLayoutParams(parafive);
		    
		    ViewGroup.LayoutParams parasix = six.getLayoutParams();
		    parasix.width = whitewidth;
		    parasix.height = newheight;
		    six.setLayoutParams(parasix);
		    
		    ViewGroup.LayoutParams paraseven = seven.getLayoutParams();
		    paraseven.width = blackwidth;
		    paraseven.height = newheight/3;
		    seven.setLayoutParams(paraseven);
		    
		    ViewGroup.LayoutParams paraeight = eight.getLayoutParams();
		    paraeight.width = whitewidth;
		    paraeight.height = newheight;
		    eight.setLayoutParams(paraeight);
		    
		    ViewGroup.LayoutParams paranine = nine.getLayoutParams();
		    paranine.width = blackwidth;
		    paranine.height = newheight/3;
		    nine.setLayoutParams(paranine);
		    
		    ViewGroup.LayoutParams paraten = ten.getLayoutParams();
		    paraten.width = whitewidth;
		    paraten.height = newheight;
		    ten.setLayoutParams(paraten);
		    
		    ViewGroup.LayoutParams paraeleven = eleven.getLayoutParams();
		    paraeleven.width = blackwidth;
		    paraeleven.height = newheight/3;
		    eleven.setLayoutParams(paraeleven);
		    
		    ViewGroup.LayoutParams paratwelve = twelve.getLayoutParams();
		    paratwelve.width = whitewidth;
		    paratwelve.height = newheight;
		    twelve.setLayoutParams(paratwelve);
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