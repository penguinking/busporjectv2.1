package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImgUtil {
	
	public static String saveBmpToSd(String Url,String filename) { 	
		
		//…Ë÷√ƒ¨»œ÷µ
		if(Url.equals(""))
		{
			Url="http://221.202.84.168:8080/handbus/img/"+filename;			
		}
		
		Bitmap bm=null;		
		
		try {

			URL url = new URL(Url);
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			
			InputStream is = conn.getInputStream();			

			bm= BitmapFactory.decodeStream(is);

		} catch (Exception e) {

			// TODO Auto-generated catch block
			return Url+"   "+e.getMessage();
			//return null;
		}	
		
		
		if(bm==null)
		{
			return "null";			
		}		
		
        String dir ="/sdcard/busimg"; //getDirectory(filename); 
        File file = new File(dir); 
        if(!file.exists()){
			file.mkdirs();
		}
        try { 
        	
        	file = new File(dir+"/"+filename);
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file); 
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
            outStream.flush(); 
            outStream.close(); 
            return "true";
            //Log.i(TAG, "Image saved tosd"); 
        } catch (FileNotFoundException e) { 
            //Log.w(TAG,"FileNotFoundException");
        	return e.toString();
        } catch (IOException e) { 
            //Log.w(TAG,"IOException"); 
        	return e.toString();
        } 
    } 
	
	

}
