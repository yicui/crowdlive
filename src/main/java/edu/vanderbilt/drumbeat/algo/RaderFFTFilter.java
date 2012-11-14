package edu.vanderbilt.drumbeat.algo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Data;

/**
 * @author yicui
 * 
 * This filter implements the Rader Fast Fourier Transformation.
 * The resulting framesize is half of the original.
 */
@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class RaderFFTFilter implements Filter {
	private static final long serialVersionUID = 1L;

	private transient int framesize;
	private transient float MINY;  
	private transient float[] real;  
	private transient float[] imag;  
	private transient float[] sintable;  
	private transient float[] costable;  
	private transient int[] bitReverse; 	    
	private transient int framesize_logarithm;
	
	private void Initialize(int framesize) {
    	// Initialize
    	MINY = (float) ((framesize << 2) * Math.sqrt(2));
    	real = new float[framesize];  
    	imag = new float[framesize];  
    	sintable = new float[framesize >> 1];  
    	costable = new float[framesize >> 1];  
    	bitReverse = new int[framesize];  
  
    	framesize_logarithm = 0;
        for (; framesize > 1; framesize_logarithm ++)
        	framesize >>= 1;
        framesize = 1 << framesize_logarithm;
        
        int i, j, k, reve;  
        for (i = 0; i < framesize; i++) {  
            k = i;  
            for (j = 0, reve = 0; j != framesize_logarithm; j++) {  
                reve <<= 1;  
                reve |= (k & 1);  
                k >>>= 1;  
            }  
            bitReverse[i] = reve;  
        }  
  
        double theta, dt = 2 * 3.14159265358979323846 / framesize;  
        for (i = 0; i < (framesize >> 1); i++) {  
            theta = i * dt;  
            costable[i] = (float) Math.cos(theta);  
            sintable[i] = (float) Math.sin(theta);  
        }		
	}

    public void Process(Data data) {
		List<Object> dataset = data.getDataset();
		List<Object> processed_dataset = new ArrayList<Object>();

		for (int index = 0; index < dataset.size(); index ++) {
			int[] frame = (int[])dataset.get(index);									
			if (frame.length != framesize) {
				framesize = frame.length;
				// The FFT algorithm requires the framesize to be positive and power of 2				
		    	if (framesize == 0 || (framesize & (framesize - 1)) != 0)
		    		throw new RuntimeException("The framesize is not power of 2");
		    	Initialize(framesize);
			}
	        int i, j, k, ir, exchanges = 1, idx = framesize_logarithm - 1;  
	        float cosv, sinv, tmpr, tmpi;  
	        for (i = 0; i != framesize; i++) {
	        	real[i] = frame[bitReverse[i]];  
	        	imag[i] = 0;
	        }
	        
	        for (i = framesize_logarithm; i != 0; i--) {  
	            for (j = 0; j != exchanges; j++) {  
	                cosv = costable[j << idx];  
	                sinv = sintable[j << idx];  
	                for (k = j; k < framesize_logarithm; k += exchanges << 1) {  
	                    ir = k + exchanges;  
	                    tmpr = cosv * real[ir] - sinv * imag[ir];  
	                    tmpi = cosv * imag[ir] + sinv * real[ir];  
	                    real[ir] = real[k] - tmpr;  
	                    imag[ir] = imag[k] - tmpi;  
	                    real[k] += tmpr;  
	                    imag[k] += tmpi;  
	                }
	            }
	            exchanges <<= 1;  
	            idx--;  
	        }
	        
			int[] outMagSpectrum = new int[framesize/2];	            
	        sinv = MINY;  
	        cosv = -MINY;
	        for (i = framesize/2; i != 0; i--) {  
	            tmpr = real[i];  
	            tmpi = imag[i];  
	            if (tmpr > cosv && tmpr < sinv && tmpi > cosv && tmpi < sinv)  
	            	outMagSpectrum[i - 1] = 0;  
	            else
	            	outMagSpectrum[i - 1] = (int)((tmpr * tmpr + tmpi * tmpi)/1e10);
	        }
			processed_dataset.add(outMagSpectrum);	        
		}
		data.setDataset(processed_dataset);
	}
}