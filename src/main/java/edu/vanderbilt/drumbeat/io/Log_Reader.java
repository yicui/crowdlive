package edu.vanderbilt.drumbeat.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.vanderbilt.drumbeat.domain.Audio;

@RooJavaBean
@RooToString
public class Log_Reader implements Reader {
	@Autowired
	private Audio audio;
	
	public void Input(String pathurl) throws Exception {
		/* Each line of the log file represents an audio frame, 
		   the format is yyyy-mm-dd hh:mm:ss.xxx appname <0xdddddddd 0xdddddddd ...>
		   where < ... > contains 2^n base64-encoded 4-byte integers  
		   e.g., 2012-07-25 22:38:16.067 aurioTouch3[4036:707] <880f9bff 3551a3ff ...> */

		ArrayList<int[]> data = new ArrayList<int[]>();
		int framesize = 0;
		int result[];

		BufferedReader reader = new BufferedReader(new FileReader(pathurl));
		String line;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date beginning_timestamp = new Date();
		Date ending_timestamp = new Date();
		
		while ((line = reader.readLine()) != null) {
			String[] numbers = line.split(" ");
			// Sometimes we meet unexpected log lines, just ignore them
			if ((numbers.length - 3) <= 0 || (numbers.length - 3) % 2 != 0) {
				continue;
			}
			// When reading the first log line, we shall learn the framesize and the beginning timestamp  
			if (framesize == 0) {
				framesize = numbers.length - 3;
				beginning_timestamp = ending_timestamp = df.parse(line);				
			}
			// In rare cases when lines of a single log file contains different framesizes, we just refuse it  
			else if (numbers.length - 3 != framesize)
				throw new IOException("inconsistent framesize");
	
			// We want to make sure whether timestamps are asymptotic
			Date new_timestamp = df.parse(line);
			long difference = new_timestamp.getTime() - ending_timestamp.getTime(); 
			if (difference < 0)
				throw new IOException("timestamps are not asymptotic");
			ending_timestamp = new_timestamp;

			// Base64-decode integers
			result = new int[framesize];			
			for (int i = 0; i < framesize; i ++) {
				// The first integer starts from "<", so its reading offset should be different from the rest 
				int offset;
				if (i == 0) offset = 1;
				else offset = 0;
				result[i] = 0;
				for (int bytes = 0; bytes < 8; bytes += 2) {
					byte x = (byte) ((Short.parseShort(numbers[3+i].substring(bytes+offset, bytes+2+offset), 16)) & 0xff);
					int shift = bytes * 4;
					result[i] += (x & 0x000000FF) << shift;
				}
			}
			data.add(result);
		}
		reader.close();

		// Set the Audio properties
		this.audio.setFrames(data.size());
		this.audio.setFramesize(framesize);
		this.audio.setDuration((int)(ending_timestamp.getTime() - beginning_timestamp.getTime()));
		this.audio.setData(data);
	}
}
