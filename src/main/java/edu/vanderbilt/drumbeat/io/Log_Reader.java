package edu.vanderbilt.drumbeat.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * This reader retrieves audio data from the log file and passes into the Audio object.
 * Each line of the log file represents an audio frame, 
 * the format is yyyy-mm-dd hh:mm:ss.xxx appname <0xdddddddd 0xdddddddd ...>
 * where the hexadecimal string < ... > contains 2^n 4-byte integers
 * e.g., 2012-07-25 22:38:16.067 aurioTouch3[4036:707] <880f9bff 3551a3ff ...>
 */

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class Log_Reader implements Reader {
	private static final long serialVersionUID = 1L;	
	@Autowired
	private Audio audio;
	
	public void Input() {
		List<Object> dataset = new ArrayList<Object>();
		int framesize = 0;
		int result[];

		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.audio.getPathurl()));
			String line;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date beginning_timestamp = new Date();
			Date ending_timestamp = new Date();
			
			while ((line = reader.readLine()) != null) {
				// Sometimes we meet unexpected log lines, just ignore them
				if (!line.matches(".*<[[a-z0-9]{8} ]*[a-z0-9]{8}>"))
					continue;
				String[] numbers = line.split(" ");
				// When reading the first log line, we shall learn the beginning timestamp  
				if (framesize == 0)
					beginning_timestamp = ending_timestamp = df.parse(line);				
				framesize = numbers.length - 3;
		
				// We want to make sure whether timestamps are asymptotic
				Date new_timestamp = df.parse(line);
				long difference = new_timestamp.getTime() - ending_timestamp.getTime(); 
				if (difference < 0)
					throw new RuntimeException("Audio log timestamps are not asymptotic");
				ending_timestamp = new_timestamp;
	
				// decode hexadecimal string into integers
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
				dataset.add(result);
			}
			reader.close();
			// Set the Audio properties
			this.audio.setDuration((int)(ending_timestamp.getTime() - beginning_timestamp.getTime()));
			Data data = this.audio.getDatamanager().peek();
			data.setDataset(dataset);
			this.audio.getDatamanager().update(data);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex); 
		}
	}
}
