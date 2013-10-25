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

	private Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息   
	
	private String info1;//当前站点名称 
	private String info2;//线路列表
    
    private int space=40;//间距
    private int startXPoint=4;//初始位置 
    private int busYPoint=10;//公交车顶端位置 
    private int stopYPoint=20;//站点顶端位置
    private int nameYPoint=47;//名称位置 
    
    
    private int pyl=0;//偏移量
    
    public int getPyl() {    	
    	    	
		return pyl;
	}

	

	private String station;//当前站点
    
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

	private Bitmap busstop;//站点图片
    private Bitmap bus_0;//公交图片
    private Bitmap bus_1;//公交图片
	
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

         //绘制文字  
           
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响   
              
       
        
	}
	
	@Override
    protected void onDraw(Canvas canvas)
    {  
        // TODO Auto-generated method stub  
        super.onDraw(canvas);  
        //设置填充  
        mPaint.setStyle(Style.FILL);          
        //画一个矩形,前俩个是矩形左上角坐标，后面俩个是右下角坐标  
       
        
        //计算长度
        int lenth=   (stations.length -1) * space+startXPoint+9; //修改+9 
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
    
        //canvas.drawLine(1, stopYPoint+20,  500 , stopYPoint+10, mPaint);//画线

        // canvas.drawRect(new Rect(40, 100, 2000, 20), mPaint);       
        mPaint.setColor(Color.rgb(25, 120,203));
        
        
        //绘制文字  
        //canvas.drawText(info1, 100, 100, mPaint);  
        //canvas.drawBitmap(bitmap, new Matrix(), mPaint);
        
        for(int i=0;i<stations.length;i++)
        {
        	drawBusStop(canvas,i+1);        	
        }  
        
        //显示一组车 
        //if(buses!=null){
        //	for(int j=0;j<buses.length;j++){        		
        //		drawBus(canvas,Integer.parseInt(buses[j]));
        //	}        	
        //}
        
              	
        drawBus(canvas,bus0,bus1);
       System.out.println("drawbus "+"ok!"+"bus0"+bus0+"bus1"+bus1);
        
        
    }
	
	
	//绘制第几个站点 
	private void drawBusStop(Canvas canvas,int x)
	{		
		//canvas.drawBitmap(busstop,startXPoint+ (x-1)*space, stopYPoint , mPaint);
		canvas.drawCircle(startXPoint+10+ (x-1)*space,  stopYPoint+14,7,mPaint);//原点(原值6）
		String name=stations[x-1];//站点名称
		drawStopName(canvas, name,x);
		//如果是当前站点 
		if(name.equals(station))
		{			
			//显示站点图片
			//canvas.drawBitmap(busstop,startXPoint+6+ (x-1)*space, stopYPoint , mPaint);
			Paint mPaint_red = new Paint();   
			mPaint_red.setAntiAlias(true);	
			mPaint_red.setColor(Color.RED);
			
			canvas.drawCircle(  startXPoint+10+ (x-1)*space,  stopYPoint+14,7,mPaint_red);//原点（原值6）
			//偏移量
			pyl=startXPoint+6+ (x-1)*space;
			
		}
		
		
	}	
	
	//打印站点名称(纵向) 
	private void drawStopName(Canvas canvas, String name,int x)
	{
		String s[]=name.split("");
		
		for(int i=0;i<s.length;i++)
		{
			if(s[i].equals("(") ||  s[i].equals("（") ){
				s[i]="︵";				
			}
			if(s[i].equals(")") ||  s[i].equals("）") ){
				s[i]="︶";				
			}			
			canvas.drawText(s[i], startXPoint+ (x-1)*space , nameYPoint+i*25, mPaint);			
		}			
	}
	
	//显示公交车
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
