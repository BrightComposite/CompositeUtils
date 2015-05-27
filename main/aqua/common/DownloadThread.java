package aqua.common;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.List;

public class DownloadThread extends Thread
{
	public static enum State
	{
		WAITING,
		CALCULATE,
		DOWNLOAD,
		DONE
	}

	public int procents = 0;
	public long totalsize = 0;
	public long currentsize = 0;
	public String currentfile = "...";
	public int downloadspeed = 0;
	public List<String> files;
	public State state = State.WAITING;
	public boolean error = false;
	public String answer;
	
	public DownloadThread(List<String> files, String answer)
	{
		this.files = files;
		this.answer = answer;
	}

	public void download(String urlTo, String pathTo) throws Exception
	{
		state = State.CALCULATE;
		
		for (int i = 0; i < files.size(); i++)
		{
			URLConnection urlconnection = new URL(urlTo + files.get(i)).openConnection();
			urlconnection.setDefaultUseCaches(false);
			totalsize += urlconnection.getContentLength();
		}
		
		state = State.DOWNLOAD;
		
		byte[] buffer = new byte[65536];
		for (int i = 0; i < files.size(); i++)
		{
			currentfile = files.get(i);
			System.out.println("Downloading file: " + currentfile);
			InputStream is = new BufferedInputStream(new URL(urlTo + files.get(i)).openStream());
			FileOutputStream fos = new FileOutputStream(pathTo + files.get(i));
			long downloadStartTime = System.currentTimeMillis();
			int downloadedAmount = 0, bs = 0;
			MessageDigest m = MessageDigest.getInstance("MD5");
			while((bs = is.read(buffer, 0, buffer.length)) != -1)
			{
				fos.write(buffer, 0, bs);
				m.update(buffer, 0, bs);
				currentsize += bs;
				procents = (int)(currentsize * 100 / totalsize);
				downloadedAmount += bs;
				long timeLapse = System.currentTimeMillis() - downloadStartTime;
				if (timeLapse >= 1000L)
				{
					downloadspeed = (int)((int) (downloadedAmount / (float) timeLapse * 100.0F) / 100.0F);
					downloadedAmount = 0;
					downloadStartTime += 1000L;
				}
			}
			is.close();
			fos.close();
			System.out.println("File downloaded: " + currentfile);
			System.out.println("File size: " + String.valueOf(currentsize));
		}
		state = State.DONE;
	}
	
}
