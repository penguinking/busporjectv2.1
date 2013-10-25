package com.bus.customized;

import com.bus.activities.R;

import android.content.Context;   
import android.content.res.Resources;
import android.content.res.TypedArray;   
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;   
import android.graphics.Color;   
import android.graphics.Paint;   
import android.graphics.Paint.Style;   
import android.graphics.Rect;
import android.util.AttributeSet;   
import android.widget.HorizontalScrollView;

public class BusInfoView extends HorizontalScrollView {

	private Paint mPaint; //����,�����˻�����ͼ�Ρ��ı��ȵ���ʽ����ɫ��Ϣ   
	
	private String info1;//��ǰվ������ 
	private String info2;//��·�б�
    
    private int space=40;//���
    private int startXPoint=4;//��ʼλ�� 
    private int busYPoint=10;//����������λ�� 
    private int stopYPoint=20;//վ�㶥��λ��
    private int nameYPoint=47;//����λ�� 
    
    
    private int pyl=0;//ƫ����
    
    public int getPyl() {    	
    	    	
		return pyl;
	}

	

	private String station;//��ǰվ��
    
    private int bus0;
    private int bus1;
    public int getBus0() {
		return bus0;
	}

	public void setBus0(int bus0) {
		this.bus0 = bus0;
	}

	public int getBus1() {
		return bus1;
	}

	public void setBus1(int bus1) {
		this.bus1 = bus1;
	}

	
    
    
    public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	private String [] stations={};    
    
    public String[] getStations() {
		return stations;
	}

	public void setStations(String[] stations) {
		this.stations = stations;
	}
	
	
	private String buses[];	

	public String[] getBuses() {
		return buses;
	}

	public void setBuses(String[] buses) {
		this.buses = buses;
	}

	private Bitmap busstop;//վ��ͼƬ
    private Bitmap bus_0;//����ͼƬ
    private Bitmap bus_1;//����ͼƬ
	
	public BusInfoView(Context context) {   
        super(context);  
        mPaint = new Paint();
        
    }   
	
	public BusInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		
		
		mPaint = new Paint();   
		mPaint.setAntiAlias(true);		
       
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BusInfoView);   
       
        info1=array.getString(R.styleable.BusInfoView_info1);
        info2=array.getString(R.styleable.BusInfoView_info2);
               
        
        Resources res = getResources();
        busstop = BitmapFactory.decodeResource(res, R.drawable.xzp);
        bus_0= BitmapFactory.decodeResource(res, R.drawable.icon_bus);
        bus_1= BitmapFactory.decodeResource(res, R.drawable.icon_bus_red);
		//mPaint.setTextSize(5);            

         //��������  
           
        array.recycle(); //һ��Ҫ���ã�������ε��趨����´ε�ʹ�����Ӱ��   
              
       
        
	}
	
	@Override
    protected void onDraw(Canvas canvas)
    {  
        // TODO Auto-generated method stub  
        super.onDraw(canvas);  
        //�������  
        mPaint.setStyle(Style.FILL);          
        //��һ������,ǰ�����Ǿ������Ͻ����꣬�������������½�����  
       
        
        //���㳤��
        int lenth=   (stations.length -1) * space+startXPoint+9; //�޸�+9 
        Rect rect = new Rect(); 
        this.getWindowVisibleDisplayFrame(rect);
        //Log.i("out", "height--"+rect.height()+" width--"+rect.width());
        if (rect.width()==320)
        {
        	mPaint.setTextSize(19); 
        }else if(rect.width()>=480){
        	mPaint.setTextSize(26); 
        }else{
        	mPaint.setTextSize(22);
        }
        canvas.drawRect(new Rect(startXPoint, stopYPoint+13, lenth, stopYPoint+18), mPaint);   
    
        //canvas.drawLine(1, stopYPoint+20,  500 , stopYPoint+10, mPaint);//����

        // canvas.drawRect(new Rect(40, 100, 2000, 20), mPaint);       
        mPaint.setColor(Color.rgb(25, 120,203));
        
        
        //��������  
        //canvas.drawText(info1, 100, 100, mPaint);  
        //canvas.drawBitmap(bitmap, new Matrix(), mPaint);
        
        for(int i=0;i<stations.length;i++)
        {
        	drawBusStop(canvas,i+1);        	
        }  
        
        //��ʾһ�鳵 
        //if(buses!=null){
        //	for(int j=0;j<buses.length;j++){        		
        //		drawBus(canvas,Integer.parseInt(buses[j]));
        //	}        	
        //}
        
              	
        drawBus(canvas,bus0,bus1);
       System.out.println("drawbus "+"ok!"+"bus0"+bus0+"bus1"+bus1);
        
        
    }
	
	
	//���Ƶڼ���վ�� 
	private void drawBusStop(Canvas canvas,int x)
	{		
		//canvas.drawBitmap(busstop,startXPoint+ (x-1)*space, stopYPoint , mPaint);
		canvas.drawCircle(startXPoint+10+ (x-1)*space,  stopYPoint+14,7,mPaint);//ԭ��(ԭֵ6��
		String name=stations[x-1];//վ������
		drawStopName(canvas, name,x);
		//����ǵ�ǰվ�� 
		if(name.equals(station))
		{			
			//��ʾվ��ͼƬ
			//canvas.drawBitmap(busstop,startXPoint+6+ (x-1)*space, stopYPoint , mPaint);
			Paint mPaint_red = new Paint();   
			mPaint_red.setAntiAlias(true);	
			mPaint_red.setColor(Color.RED);
			
			canvas.drawCircle(  startXPoint+10+ (x-1)*space,  stopYPoint+14,7,mPaint_red);//ԭ�㣨ԭֵ6��
			//ƫ����
			pyl=startXPoint+6+ (x-1)*space;
			
		}
		
		
	}	
	
	//��ӡվ������(����) 
	private void drawStopName(Canvas canvas, String name,int x)
	{
		String s[]=name.split("");
		
		for(int i=0;i<s.length;i++)
		{
			if(s[i].equals("(") ||  s[i].equals("��") ){
				s[i]="��";				
			}
			if(s[i].equals(")") ||  s[i].equals("��") ){
				s[i]="��";				
			}			
			canvas.drawText(s[i], startXPoint+ (x-1)*space , nameYPoint+i*25, mPaint);			
		}			
	}
	
	//��ʾ������
	private void drawBus(Canvas canvas, int x0,int x1)
	{	
		if(x0>0){
			canvas.drawBitmap(bus_0,startXPoint+15+ (x0-1)*space, busYPoint , mPaint);
		}
		
		if(x1>0){
			canvas.drawBitmap(bus_1,startXPoint+15+ (x1-1)*space, busYPoint , mPaint);
		}
		
	}
	

}
