package com.work.synthesizer;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;
import android.util.Log;

public class pvLibrary {
	
	public static double[] makeWindow(int n) {
	    double[] window = new double[n];
	    for(int i = 0; i < n; i++)
	    	window[i] = 0.5 - 0.5*Math.cos(2*Math.PI*i/(n-1)); //Hanning window
		return window;
	    
	  }
	
	public static int nextPow2(int n){
	    int p = 1;
	    if(n%2 == 0)
	        return n;
	 
	    while (p < n) {
	        p <<= 1;
	    }
	    return p;
	}
	
	public static double getMagnitude(double re, double im){
		return Math.sqrt(re*re + im*im);
	}
	
	public static double getPhase(double re, double im){
		return Math.atan2(im, re);
	}
	
	public static double wrapPhase(double phi){
		while(phi > Math.PI) phi -= 2*Math.PI;
		while(phi < -Math.PI) phi += 2*Math.PI;
		return phi;
	}
	
	public static double getAbsMax(double[] x){
		double max = 0;
		
		for(int i=0; i<x.length; i++)
		{
			if(Math.abs(x[i]) > max) max = Math.abs(x[i]);
		}
		return max;
	}
	
	
	/**
	 * Creates windowed Frames of an input signal x.
	 * (Could be implemented more efficiently by just storing the indexes of the frames, rather than copying every frame...)
	 * @param x	The input signal
	 * @param hop step size
	 * @param winLength
	 * @return Frames[][]: Matrix, each row contains one frame
	 */
	public static double[][] createFrames(double[] x, int hop, int winLength, double[] window){
		int NrOfFrames = (x.length-winLength)/hop;  // type cast rundet ab
		
		double[][] Frames = new double[NrOfFrames][winLength];
		
		// if(NrOfFrames*hop + winLength < x.length)   ==> end of x gets cut...
		int index = 0;
		for(int i=0; i<NrOfFrames; i++){
			for(int j=0; j<winLength; j++)
			{
				Frames[i][j] = window[j]*x[index+j];
			}
			
			index += hop;
		}
		
		return Frames;
		
	}
	
	/**
	 * Aligns frames using overlap-method. The signal gets stretched by factor hopIn/hopOut
	 * 
	 * @param Frames[][] Matrix, each row contains one frame
	 * @param hop
	 */
	public static double[] addFrames(double[][] Frames, int hop, double[] window){  
		int NrOfFrames = Frames.length;
		int winLength = Frames[0].length;
		int len = NrOfFrames*hop + winLength - hop;
		double[] y = new double[len];  //output
		for(int i=0; i<len; i++) y[i] = 0;

		int index = 0;
		for(int i=0; i<NrOfFrames; i++){
			for(int j=0; j<winLength; j++)
			{
				y[index+j] += Frames[i][j];
			} 
			index += hop;
		}
		
//		double compFactor = amplitudeCompensation(window,hop);
//		for(int i=0; i<len; i++) y[i] /= compFactor;
		
		
			/////!!!!!!!! only for overlaping factor of 0.5!!!!!!!!
			//for(int i=1; i<winLength/2; i++) y[i] = y[i]/window[i];
			/////
			
			
		return y;
	}
	
//	public static double amplitudeCompensation(double[] window, int hop){
//		double compFactor = 0;
//		
//		for(int i=0; i<100; i++) compFactor += window[i]*window[i];
//		
//		return compFactor/hop;
//		
//	}
	




public static double[] InterpolateLin(double[] x, double fac, int length){
	
	double[] y = new double[length];
	
	double interpVal;
	double index = 0;
	
	int i = 0;
	int t1 = 0;
	
	while(i < length && t1 < x.length-1)
	{
		interpVal = x[t1] + (index-t1)*(x[t1+1]-x[t1]);
		y[i] = interpVal;
		
		index += fac;
		t1 = (int)index;
		i++;
	}
	return y;
}

public static void WriteOctave(double[] x, String filepath){
	PhaseVocoder pv = new PhaseVocoder();
	
	double[] tmp;
	String path = null;
	//upper half
	path = filepath + "/6.pcm";
	Audioplayer.WriteDoubleToPCM(path,x);
	
	tmp = pv.shift(x, 1);
	path = filepath + "/7.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(tmp, 2);
	path = filepath + "/9.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(tmp, 2);
	path = filepath + "/11.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(x, 2);
	path = filepath + "/8.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(tmp, 2);
	path = filepath + "/10.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(tmp, 2);
	path = filepath + "/12.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	
	//lower half
	tmp = pv.shift(x, -1);
	path = filepath + "/5.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(tmp, -2);
	path = filepath + "/3.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(tmp, -2);
	path = filepath + "/1.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(x, -2);
	path = filepath + "/4.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
	tmp = pv.shift(tmp, -2);
	path = filepath + "/2.pcm";
	Audioplayer.WriteDoubleToPCM(path,tmp);
		

}

//public static double[] readPCM(String filepath) {
//    double[] result = null;
//
//    try {
//        File file = new File(filepath);
//        InputStream in = new FileInputStream(file);
//        int bufferSize = (int) (file.length()/2);
//
//        result = new double[bufferSize];
//
//        DataInputStream is = new DataInputStream(in);
//
//        for (int i = 0; i < bufferSize; i++) {
//            result[i] = is.readShort() / 32768.0;
//        }
//
//    } catch (FileNotFoundException e) {
//        Log.i("File not found", "" + e);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//
//    return result;
//
//}
	
}
