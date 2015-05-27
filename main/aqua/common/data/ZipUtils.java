package aqua.common.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtils
{
	public static boolean unzip(String file, String path, boolean replace)
	{
		try
		{
			ZipFile zipfile = new ZipFile(file);    
			Enumeration <? extends ZipEntry> entries = zipfile.entries();

			while(entries.hasMoreElements()) 
			{
				ZipEntry entry = entries.nextElement();
				extractFromZip(file, path, entry.getName(), zipfile, entry, replace);
			}
		
			zipfile.close();
			new File(file).delete();
		}
		catch(Exception e)
		{
			System.out.println("Can't unzip file: " + file.toString());
            e.printStackTrace();
			return false;
		}
		
		return true;
	}

	static void extractFromZip(String zipPath, String extractPath, String name, ZipFile zipFile, ZipEntry zipEntry, boolean replace) throws Exception
	{
		if(zipEntry.isDirectory())
			return;
	
		String dstName = slash2sep(name);
		String entryDir;
	
		if(dstName.lastIndexOf(File.separator) != -1) 
			entryDir = dstName.substring(0, dstName.lastIndexOf(File.separator));
		else
			entryDir = "";
		
		File newDir = new File(extractPath + File.separator + entryDir);
	
		if(!newDir.exists())
			newDir.mkdirs();	 
		
		File dstFile = new File(extractPath + File.separator + dstName);
		
		if(!replace && dstFile.exists())
			return;
		
		FileOutputStream fos = new FileOutputStream(dstFile);
	
		InputStream is = zipFile.getInputStream(zipEntry);
		byte[] buf = new byte[1024];
	
		while(true)
		{
			int length = is.read(buf);
			
			if(length < 0) 
				break;
			
			fos.write(buf, 0, length);
		}

		is.close();
		fos.close();
	}
	
	static String slash2sep(String src)
	{
		char[] chars = src.toCharArray();
	
		for(int i = 0; i < chars.length; i++)
		{
			if(chars[i] == '/')
				chars[i] = File.separatorChar;
		}
		
		return new String(chars);
	}
}
