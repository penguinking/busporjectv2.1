package com.bus.activities;

import android.content.Context; 
import android.widget.ImageView; 
import android.widget.LinearLayout; 
import android.widget.TextView; 
 
//�Զ���ImageButton��ģ��ImageButton���������·���ʾ���� 
//�ṩButton�Ĳ��ֽӿ� 
public class MyImageButton extends LinearLayout { 
 
  public MyImageButton(Context context, int imageResId, int textResId) { 
    super(context); 
 
    mButtonImage = new ImageView(context); 
    mButtonText = new TextView(context); 
 
    setImageResource(imageResId); 
    mButtonImage.setPadding(0, 0, 0, 0);
    mButtonImage.setBackgroundColor(0xFFFFFF); 
 
    setText(textResId); 
    setTextColor(0xFF000000); 
    mButtonText.setPadding(0, 0, 0, 0); 
    
    //���ñ����ֵ����� 
    setClickable(true);  //�ɵ�� 
    setFocusable(true);  //�ɾ۽� 
    //setBackgroundResource(android.R.drawable.btn_default);  //���ֲ�����ͨ��ť�ı��� 
    setOrientation(LinearLayout.VERTICAL);  //��ֱ���� 
    setGravity(0x11);
    
     
    //�������Image��Ȼ������Text 
    //���˳�򽫻�Ӱ�첼��Ч�� 
    addView(mButtonImage); 
    addView(mButtonText); 
  } 
 
  // ----------------public method----------------------------- 
  /* 
   * setImageResource���� 
   */ 
  public void setImageResource(int resId) { 
    mButtonImage.setImageResource(resId); 
  } 
 
  /* 
   * setText���� 
   */ 
  public void setText(int resId) { 
    mButtonText.setText(resId); 
  } 
 
  public void setText(CharSequence buttonText) { 
    mButtonText.setText(buttonText); 
  } 
 
  /* 
   * setTextColor���� 
   */ 
  public void setTextColor(int color) { 
    mButtonText.setTextColor(color); 
  } 
 
  // ----------------private attribute----------------------------- 
  private ImageView mButtonImage = null; 
  private TextView mButtonText = null; 
} 