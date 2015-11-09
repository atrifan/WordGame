package ro.mps.wordsgame.logic;

import java.util.HashMap;

/**
 * 
 * @author cosmin
 * 
 * Abstraction for the concept of language
 * This class provides a mapping from english characters
 * to special characters in other languages, for instance
 * in Romanian 'a' can be mapped to 'ă', 'â'
 *
 */
public interface ILanguage {
	public HashMap<Character, String> getMappings();
}
