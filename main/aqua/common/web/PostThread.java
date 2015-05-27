package aqua.common.web;

import java.lang.reflect.Method;
import java.net.URL;

public class PostThread extends Thread
{
	URL 	url;
	Object 	parameters[];
	Object 	callbackInstance;
	Method 	callback;
	
	public PostThread(URL url, Object parameters[], Object callbackInstance, Method callback)
	{
		this.url = url;
		this.parameters = parameters;
		this.callbackInstance = callbackInstance;
		this.callback = callback;
	}
	
	@Override
	public void run()
	{
		try
		{
			WebTarget wt = Web.post(url, parameters);

			if(callback != null)
				callback.invoke(callbackInstance, wt.getResponse());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
