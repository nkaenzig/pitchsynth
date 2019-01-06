package com.work.synthesizer;


import java.io.*;

public class PhaseVocoder {
	
	private int winLength = 1024;
	private int fftLength = winLength;
	
	private int hopIn = winLength/4;
	public double shift;
	public int hopOut;
	
	private double[] window;
	public double[][] frames;
	
	public PhaseVocoder(){
		window = pvLibrary.makeWindow(winLength);
	}
	
	public double[] shift(double[] input, int semitones){
		double[][] Frames = pvLibrary.createFrames(input, hopIn, winLength, window);
		shift = Math.pow(2, (double)semitones/12);
		hopOut = (int) Math.round(shift*hopIn);
		
		int NrOfFrames = Frames.length;
		FFT fft = new FFT(fftLength);
		
	    double[][] im = new double[NrOfFrames][fftLength];  //values are automatically set to 0!
	    double mag;
	    double phi;
	    double deltaPhi;
	    double deltaPhiPrime;
	    double trueFreq;
	    double[] previousPhase = new double[fftLength];
	    double[] phaseCumulative = new double[fftLength];
		
		for(int i=0; i<NrOfFrames; i++)
		{
			fft.fft(Frames[i], im[i]);  // Frames[i] contains real parts
			
			for(int j=0; j<fftLength; j++)
			{
				mag = pvLibrary.getMagnitude(Frames[i][j], im[i][j]);
				phi = pvLibrary.getPhase(Frames[i][j], im[i][j]);
				
				deltaPhi = phi - previousPhase[j];
				previousPhase[j] = phi; 
			
				/* N./(1:(N/2)) is the cycle length of the sinusoids at the center of bins 1:N/2 of the FFT (counting from 0)
				-- i.e. FFT bin 1 corresponds to a sinusoid that completes 1 cycle in N samples (period N/1), and the highest 
				bin (bin N/2) corresponds to a sinusoid that completes 1 cycle every 2 samples i.e. period N/(N/2). 
				So hop/(N./(1:N/2)) is the proportion of a cycle represented by hop samples, and 2*pi*... is that cycle proportion in radians. 
				*/
				deltaPhiPrime = deltaPhi - (double)hopIn*2*Math.PI*j/winLength;  //-expected phase diff (beacause of overlap)
				deltaPhiPrime = pvLibrary.wrapPhase(deltaPhiPrime);				 //if hop=winLength: expected phase diff is multiple of 2pi <-> the measured signal lies exactly on the bins
				
				trueFreq = (double)2*Math.PI*j/fftLength + deltaPhiPrime/hopIn;  //bin freq + deltaFreq   (in radian!!!) 
				
				phaseCumulative[j] += hopOut * trueFreq;   // +new "delta phi" <-> the deltas of each consecutive frame have to be added to get the total phase
			
				phi = phaseCumulative[j];
				Frames[i][j] = mag*Math.cos(phi);  // conversion from complex polar back to rectangular form
				im[i][j] = mag*Math.sin(phi);
				
			}
			
			fft.ifft(Frames[i], im[i]);
			for(int k=0; k<winLength;k++) Frames[i][k] *= window[k];  //After spectral modifications,  the individual frames may no longer follow the envelope of the analysis window.
																      // Has to be windowed again!!!
			
		}
	
		double[] y = pvLibrary.addFrames(Frames, hopOut, window);
		double[] z = pvLibrary.InterpolateLin(y, shift, input.length);  // RESAMPLING
		
		double removeClip = pvLibrary.getAbsMax(y);			// scaling to [-1,1]
		for(int k=0; k<y.length;k++) y[k] /= removeClip;
		
		Frames = null;
		im = null;
		System.gc();
		
		return z;
	}
	
	
}
