
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class conventer {
	

	//convent String to in
	
	public static InputStream convent(String txt) throws IOException {
		InputStream txt2 = new ByteArrayInputStream(txt.getBytes(Charset.forName("UTF-8")));
		return txt2;
	}
	//convent in to String
	
	public static String convent2(InputStream in) throws IOException{
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))) {	
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		return stringBuilder.toString();
	}
		

}



