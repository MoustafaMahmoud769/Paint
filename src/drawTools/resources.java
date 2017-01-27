package drawTools;
import java.io.InputStream;


public class resources {
	public static InputStream resourceLoader(String path){
		InputStream input=resources.class.getResourceAsStream(path);
		if(input==null){
			input=resources.class.getResourceAsStream("/"+path);
		}
		return input;
		
	}

}
