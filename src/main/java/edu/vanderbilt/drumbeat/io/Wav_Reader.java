package edu.vanderbilt.drumbeat.io;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Audio;

@RooJavaBean
@RooToString
public class Wav_Reader implements Reader {
	@Autowired
	private Audio audio;
	
	public void Input(String pathurl) throws Exception {
		ArrayList<int[]> data = new ArrayList<int[]>();		
		File file = new File(pathurl);
		AudioInputStream ais = AudioSystem.getAudioInputStream(file);
		AudioFormat format = ais.getFormat();
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
            data.add(result);
    	}
    	
		// Set the Audio properties
		this.audio.setFrames(data.size());
		this.audio.setFramesize(framesize);
		this.audio.setDuration((int)(ais.getFrameLength()*1000/format.getFrameRate()));
		this.audio.setData(data);		
	}	
}
