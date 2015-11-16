package ro.mps.wordsgame.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.*;

/**
 * Created by alexandru.trifan on 27.10.2015.
 */

public class Dictionary {

	private HashSet<String> wordSet = new HashSet();

	public Dictionary() throws Exception {
		readWords();
	}
	
	private void readWords() throws Exception {
		try {
			BufferedReader in = new BufferedReader(
								new InputStreamReader(
			                    new FileInputStream(this.getClass().getClassLoader()
										.getResource("words").getFile()), "UTF8"));
			
			String str;
		      
			while ((str = in.readLine()) != null) {
				str = Normalizer.normalize(str, Normalizer.Form.NFD);
				str = str.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			    wordSet.add(str.toLowerCase());
			}
			
			in.close();
		}
		
		catch(Exception e) {
			throw e;
		}
	}
	
    public boolean isValid(String word) {
        return wordSet.contains(word);
    }
}

