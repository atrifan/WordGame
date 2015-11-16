package ro.mps.wordsgame.logic;

import java.util.HashMap;

public class RomanianLanguage implements ILanguage {
	HashMap<Character, String> mappings;
	
	public RomanianLanguage() {
		mappings = new HashMap<Character, String>();
		mappings.put('a', "aăâ");
		mappings.put('s', "sş");
		mappings.put('t', "tţ");
		mappings.put('i', "iî");
	}
	
	public HashMap<Character, String> getMappings() {
		return mappings;
	}
}
