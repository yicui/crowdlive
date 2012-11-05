package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Audio;

@RooJavaBean
@RooToString
public class CooleyTukeyFFT_Preprocessor implements Preprocessor {
	@Autowired
	private Audio audio;
	
	private static double M_PI=3.14159265358979323846;

	// spectrum analysis
	private short[] weightingWindow;
	private int[] fftBuffer_real;
	private int[] fftBuffer_imag;
	private int[] twiddleFactors;
	
	// utility functions
	private static final int mul32_16b(int a, int b) {
		return ((int)(((long)(a) * (long)((b) & 0x0000ffff))>>16));
	}
	private static final int mul32_16t(int a, int b) {
		return ((int)(((long)(a) * (long)(((b) & 0xffff0000)>>16))>>16));
	}
	private static final long SquareMag(int re, int im) { 
		return (re>>16)*(re>>16)+(im>>16)*(im>>16); 
	}
	private static int FloatToInt16(float x)	{
		int y;
		if(x<0.f) {
			if(x<=-32768.0f) y = -32768;
			else y = (int)(x - 0.5f);
		} 
		else {
			if(x>=32767.0f) y = 32767;
			else y = (int)(x + 0.5f);		
		}
		return y;
	}
	// complex number
	private void BitReverseReorder(int real[], int imag[], int N)
	{
		int linearIdx, bitReversedIdx;
		for(linearIdx = 1, bitReversedIdx = 0; linearIdx < N - 1; ++linearIdx) {
			int halfSize = N;
			do {
				halfSize >>=1;
				bitReversedIdx ^= halfSize;
			} while(bitReversedIdx < halfSize);

			if(linearIdx < bitReversedIdx) {
				/* Swap linear and bit reversed indexed values */
				int tmp = real[bitReversedIdx];
				real[bitReversedIdx] = real[linearIdx];
				real[linearIdx] = tmp;
				tmp = imag[bitReversedIdx];
				imag[bitReversedIdx] = imag[linearIdx];
				imag[linearIdx] = tmp;			
			}
		}
	}
	private void Radix2IntCplxFFT(int real[], int imag[], int size, int[] twiddleFactors, int twiddleFactorsStrides)
	{
		int span, twiddle, strides;

		/* Reorder input data in bit reversed order */
		BitReverseReorder(real, imag, size);

		span = 1;
		twiddle = 1;
		strides = twiddleFactorsStrides*size / 2;
		
		do {
			int x0_real, x1_real, x0_imag, x1_imag;
			int idx = 0;

			do {
				/* Multiply-less butterfly */
				x1_real = real[idx+span];	x1_imag = imag[idx+span];
				x0_real = real[idx];		x0_imag = imag[idx];
				// Radix2IntButterfly
				int tmp = x0_real>>1;
				x0_real = tmp + (x1_real>>1);	x1_real = tmp - (x1_real>>1);
				tmp = x0_imag>>1;			
				x0_imag = tmp + (x1_imag>>1);	x1_imag = tmp - (x1_imag>>1);
	
				real[idx] = x0_real;		imag[idx] = x0_imag;
				real[idx+span] = x1_real;	imag[idx+span] = x1_imag;
				idx += span << 1;
			} while(idx < size);

			twiddle = 1;
			
			while(twiddle < span) {
				int packedTwiddleFactor = twiddleFactors[strides*twiddle];
				idx = twiddle;
				
				do {
					x1_real = real[idx+span];	x1_imag = imag[idx+span];
					x0_real = real[idx];		x0_imag = imag[idx];
					// CplxMul32Packed
					int tmp = x1_real; int c = (packedTwiddleFactor) & 0xffff0000; int s = ((packedTwiddleFactor)<<16);
					x1_real = (int)( ( (long)tmp * (long)c - (long)x1_imag * (long)s ) >> 32 );
					x1_imag = (int)( ( (long)tmp * (long)s + (long)x1_imag * (long)c ) >> 32 );
					// Radix2IntButterfly					
					tmp = x0_real>>1;
					x0_real = tmp + x1_real; 	x1_real = tmp - x1_real;
					tmp = x0_imag>>1;
					x0_imag = tmp + x1_imag;	x1_imag = tmp - x1_imag;

					real[idx] = x0_real;		imag[idx] = x0_imag;					
					real[idx+span] = x1_real;	imag[idx+span] = x1_imag;
					idx += span << 1;
				} while(idx < size);
				++twiddle;
			}
			span <<= 1;
			strides >>= 1;		
		} while(span < size);
	}
	
	private void Initialize(int framesize) {
		this.weightingWindow = new short[framesize];
		for(int i = 0; i < framesize/2; ++i) {
			/* Hamming window */
			float w = (float) (0.53836-0.46164*Math.cos(2.0*M_PI*i/(double)(framesize)));
			this.weightingWindow[i] = (short)(Math.pow(2.0, 15.0)*w);				
			this.weightingWindow[framesize-i-1] = this.weightingWindow[i];
		}
		
		this.fftBuffer_real = new int[framesize]; // assumed to be initialized as 0
		this.fftBuffer_imag = new int[framesize]; // assumed to be initialized as 0
		
		this.twiddleFactors = new int[framesize];
		float scaleFac = (float)(1<<15);
		for(int i = 0; i < framesize/2; ++i) {
			int cosSin;
			float tmp;
			tmp = scaleFac*(float)Math.cos(2.0*M_PI*i/framesize);
			cosSin = FloatToInt16(tmp) << 16;
			tmp = -scaleFac*(float)Math.sin(2.0*M_PI*i/framesize);
			cosSin |= FloatToInt16(tmp) & 0x0000ffff;
			this.twiddleFactors[i] = cosSin;
		}
	}
	
	public void Process() {
		ArrayList<int[]> data = this.audio.getData();
		ArrayList<int[]> processed_data = new ArrayList<int[]>();

		int framesize = 0;
		for (int index = 0; index < data.size(); index ++) {
			if (data.get(index).length != framesize) {
				framesize = data.get(index).length; 
		    	if (framesize == 0 || (framesize & (framesize - 1)) != 0)
		    		throw new RuntimeException("The framesize is not power of 2");			
				Initialize(framesize);
			}
			
			// Apply weighting window
			for(int i = 0; i < data.get(index).length; i += 2) {
				int dualCoef = (this.weightingWindow[i] & 0xffff) | ((int)(this.weightingWindow[i+1])<<16);
				this.fftBuffer_real[i] = mul32_16b((int)(data.get(index)[i]) << 7, dualCoef) << 1;
				this.fftBuffer_imag[i] = 0;
				this.fftBuffer_real[i+1] = mul32_16t((int)(data.get(index)[i+1]) << 7, dualCoef) << 1;
				this.fftBuffer_imag[i+1] = 0;
			}
			Radix2IntCplxFFT(this.fftBuffer_real, this.fftBuffer_imag, framesize, this.twiddleFactors, 1);
			
			int[] outMagSpectrum = new int[framesize/2];
			// Calculate squared magnitude spectrum
			for(int i = 0; i < framesize/2; ++i)
				outMagSpectrum[i] = (int)SquareMag(this.fftBuffer_real[i], this.fftBuffer_imag[i]);
			processed_data.add(outMagSpectrum);
		}
		this.audio.setProcesseddata(processed_data);
	}
}