package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Audio;

@RooJavaBean
@RooToString
public class RaderFFT_Preprocessor implements Preprocessor {
	@Autowired
	private Audio audio;
	
    private float MINY;  
    private float[] real, imag, sintable, costable;  
    private int[] bitReverse;  
    private int framesize_logarithm;
	
    private void Initialize(int framesize) {
    	this.MINY = (float) ((framesize << 2) * Math.sqrt(2));
    	this.real = new float[framesize];  
    	this.imag = new float[framesize];  
    	this.sintable = new float[framesize >> 1];  
    	this.costable = new float[framesize >> 1];  
    	this.bitReverse = new int[framesize];  
  
    	this.framesize_logarithm = 0;
        for (; framesize > 1; this.framesize_logarithm ++)
        	framesize >>= 1;
        framesize = 1 << this.framesize_logarithm;
        
        int i, j, k, reve;  
        for (i = 0; i < framesize; i++) {  
            k = i;  
            for (j = 0, reve = 0; j != this.framesize_logarithm; j++) {  
                reve <<= 1;  
                reve |= (k & 1);  
                k >>>= 1;  
            }  
            this.bitReverse[i] = reve;  
        }  
  
        double theta, dt = 2 * 3.14159265358979323846 / framesize;  
        for (i = 0; i < (framesize >> 1); i++) {  
            theta = i * dt;  
            costable[i] = (float) Math.cos(theta);  
            sintable[i] = (float) Math.sin(theta);  
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
	        int i, j, k, ir, exchanges = 1, idx = this.framesize_logarithm - 1;  
	        float cosv, sinv, tmpr, tmpi;  
	        for (i = 0; i != framesize; i++) {
	        	this.real[i] = data.get(index)[this.bitReverse[i]];  
	        	this.imag[i] = 0;
	        }
	        
	        for (i = this.framesize_logarithm; i != 0; i--) {  
	            for (j = 0; j != exchanges; j++) {  
	                cosv = this.costable[j << idx];  
	                sinv = this.sintable[j << idx];  
	                for (k = j; k < this.framesize_logarithm; k += exchanges << 1) {  
	                    ir = k + exchanges;  
	                    tmpr = cosv * this.real[ir] - sinv * this.imag[ir];  
	                    tmpi = cosv * this.imag[ir] + sinv * this.real[ir];  
	                    this.real[ir] = this.real[k] - tmpr;  
	                    this.imag[ir] = this.imag[k] - tmpi;  
	                    this.real[k] += tmpr;  
	                    this.imag[k] += tmpi;  
	                }
	            }
	            exchanges <<= 1;  
	            idx--;  
	        }
	        
			int[] outMagSpectrum = new int[framesize/2];	            
	        sinv = this.MINY;  
	        cosv = -this.MINY;
	        for (i = framesize/2; i != 0; i--) {  
	            tmpr = this.real[i];  
	            tmpi = this.imag[i];  
	            if (tmpr > cosv && tmpr < sinv && tmpi > cosv && tmpi < sinv)  
	            	outMagSpectrum[i - 1] = 0;  
	            else
	            	outMagSpectrum[i - 1] = (int)((tmpr * tmpr + tmpi * tmpi)/1e10);
	        }
			processed_data.add(outMagSpectrum);	        
		}
		this.audio.setProcesseddata(processed_data);
	}
}
