package aqua.common.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public class GuardUtils
{
	public static String getMD5(InputStream input, String inputName)
	{
		DigestInputStream dis = null;
		BufferedInputStream bis = null;
		Formatter formatter = null;
		try
		{	
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			bis = new BufferedInputStream(input);
			dis = new DigestInputStream(bis, messagedigest);
			while(dis.read() != -1);
	        byte digest[] = messagedigest.digest();
	        formatter = new Formatter();
	        
	        byte digestData[] = digest;
	        int i = digestData.length;
	        
	        for(int j = 0; j < i; j++)
	        {
	            formatter.format("%02x", Byte.valueOf(digestData[j]));
	        }
	        
	        System.out.println("SECURITY: MD5 of " + inputName + " - " + formatter.toString());
	        return formatter.toString();
		} catch(Exception e) { return ""; }
		finally
		{
			try { dis.close(); } catch (Exception e){}
			try { bis.close(); } catch (Exception e){}
			try { formatter.close(); } catch (Exception e){}
		}
	}
	
    public static void delete(File file)
    {
        try
        {
            if (!file.exists())
            	return;
            
            if(file.isDirectory())
            {
                for (File f : file.listFiles())
                	delete(f);
                
                file.delete();
            }
            else
            	file.delete();
        } catch (Exception e) {}
    }	
}
