package aqua.common.web;

import java.io.IOException;
import java.net.URL;

public class Web
{
	public static WebTarget post(URL url, Object[] parameters) throws IOException
	{
		WebTarget wt = new WebTarget(url);
		wt.post(parameters);
		
		return wt;
	}
}
