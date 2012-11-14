package edu.vanderbilt.drumbeat.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Audio;
import edu.vanderbilt.drumbeat.domain.Data;

/**
 * @author yicui
 * 
 * This reader retrieves audio data from the WAV file and passes into the Audio object.
 */

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class Wav_Reader implements Reader {
	private static final long serialVersionUID = 1L;	
	@Autowired
	private Audio audio;
	
	public void Input() {
		List<Object> dataset = new ArrayList<Object>();		
		File file = new File(this.audio.getPathurl());
		AudioInputStream ais;
		AudioFormat format;
		
        try {
    		ais = AudioSystem.getAudioInputStream(file);
    		format = ais.getFormat();
    		// We set the default to be 512 samples per frame 
    		int framesize = 512;
    		int samplesize = ais.getFormat().getSampleSizeInBits()/8;
    		/* WAV file may be mono or stereo. In case of stereo, samples from both channels
    		   are often identical, so we only extract from one channel */
    		byte[] b = new byte[framesize*ais.getFormat().getFrameSize()];		

            int i, j; 

            while(ais.read(b) != -1) {
				int[] result = new int[framesize];
				for (i = 0, j = 0; i < framesize; i++, j += ais.getFormat().getFrameSize()) {
					// two bytes per sample
	            	if (samplesize == 2) {
	            		result[i] = 0;
	    				for (int bytes = 0; bytes < 2; bytes ++) {
	    					int shift = (bytes+2) * 8;
	    					result[i] += (b[j+bytes] & 0x000000FF) << shift;
	    				}
	            	}
	            	// one byte per sample            	
	            	else if (samplesize == 1)
	            		result[i] = b[j];
	            	// four byte per sample            	            	
	            	else if (samplesize == 4) {
	            		result[i] = 0;
	    				for (int bytes = 0; bytes < 4; bytes ++) {
	    					int shift = bytes * 8;
	    					result[i] += (b[j+bytes] & 0x000000FF) << shift;
	    				}	                		
	            	}
	            }
	            dataset.add(result);
	    	}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex); 
		}

		// Set the Audio properties
		this.audio.setDuration((int)(ais.getFrameLength()*1000/format.getFrameRate()));
		Data data = this.audio.getDatamanager().peek();
		data.setDataset(dataset);
		this.audio.getDatamanager().update(data);
	}
}