package logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bus.activities.R;

public class CityLogic {
	
	private static final int BUFFER_SIZE = 400000;
	public static final String DB_NAME = "bizinfocus.db"; //��������ݿ��ļ���
    public static final String PACKAGE_NAME = "com.bus.activities";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;  //���ֻ��������ݿ��λ��
	 
	
	public static List<String []> getCityList(Context context,  int  parentId)
	{
		
		
		String sql="";
		
	    sql="SELECT deptNodeID,deptNodeName  from _deptNode where parentDeptNodeID="+parentId+" order by deptNodeSort";					
		
		
		if(!sql.equals(""))
		{	
			String dbfile=DB_PATH + "/" + DB_NAME;
			if (!(new File(dbfile).exists())) {//�ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
                InputStream is = context.getResources().openRawResource(R.raw.bizinfocus); //����������ݿ�
                FileOutputStream fos;
				try {
					fos = new FileOutputStream(dbfile);
					byte[] buffer = new byte[BUFFER_SIZE];
	                int count = 0;
	                while ((count = is.read(buffer)) > 0) {
	                    fos.write(buffer, 0, count);
	                }
	                fos.close();
	                is.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					System.out.println(e.toString()+"�����쳣��");
				}
	               
	        }
			
			SQLiteDatabase database;
			//DBManager dbHelper;
			database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
			
			
			
			Cursor cur = database.rawQuery(sql, null);
			List<String []> list= new ArrayList<String []>();
			for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext())
			{
				String s[]=new String[2];
				s[0]=cur.getString(0);
				s[1]=cur.getString(1);
				
				list.add(s);
			}
			cur.close();
			database.close();			
			return list;
			
		}
		else
		{
			return null;			
		}
		
		
	}

}
