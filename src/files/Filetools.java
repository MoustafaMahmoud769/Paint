package files;

/**
 * 
 */

import java.io.File;
public class Filetools {

	/*
	 * Get the extension of a file.
	 */
	public static String getExtension(File file) {
		String extension = null;
		String name = file.getName();
		int index = name.lastIndexOf('.');

		if (index > 0 && index < name.length() - 1) {
			extension = name.substring(index + 1).toLowerCase();
		}
		return extension;
	}
}
