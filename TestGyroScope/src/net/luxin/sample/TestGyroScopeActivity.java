package net.luxin.sample;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager.WakeLock;
import android.os.PowerManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.app.AlertDialog;
import android.widget.TextView;

public class TestGyroScopeActivity extends Activity  implements SensorEventListener{
	private WakeLock wakeLock;
	private SensorManager sm;
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	android.util.Log.i("mylog", " onPause");
    	wakeLock.release();
    	sm.unregisterListener(this);
    	
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		android.util.Log.i("mylog", " onResume");
		wakeLock.acquire();
		sm.registerListener(this,  sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
		
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		android.util.Log.i("mylog", " onDestroy");
		wakeLock.release();
		super.onDestroy();
	}
	public void exitApp(){
		try {
			android.util.Log.i("mylog", " exitApp");
			this.finalize();
			System.exit(0);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder alart = new Builder(this);
			alart.setMessage(R.string.exithelp);
			
			alart.setNeutralButton(R.string.ok , new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					exitApp();
				}
			});
			alart.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			alart.create().show();
			
			
		}
		return super.onKeyDown(keyCode, event);
	}
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initServiceCompment();
    }
    public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
        int axisX = (int)event.values[0];
        int axisY = (int)event.values[1];
        int axisZ = (int)event.values[2];
        //android.util.Log.e("test", "axisX:" + axisX);
		TextView tv = (TextView)(findViewById(R.id.txtGyroscopeValue));
		TextView directionText = (TextView)(findViewById(R.id.txtDirection));
		tv.setText("axisX:"+axisX+"  axisY:"+axisY + "  axisZ:"+axisZ);
		if(axisX >= 350 || axisX <= 10){
			directionText.setText("正北");
		}else if(axisX >= 11 && axisX <=79){
			directionText.setText("东北");
		}else if(axisX >=80 && axisX <=100){
			directionText.setText("正东");
		}else if(axisX >=101 && axisX <=169){
			directionText.setText("东南");
		}else if(axisX >=170 && axisX <=190){
			directionText.setText("正南");
		}else if(axisX >=191 && axisX <=259){
			directionText.setText("西南");
		}else if(axisX >=260 && axisX <=280){
			directionText.setText("正西");
		}else if(axisX >=281 && axisX <=349){
			directionText.setText("西北");
		}
		directionText.invalidate();
		tv.invalidate();
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	public void initServiceCompment(){
		
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); 
        sm.registerListener(this,  sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
        wakeLock = ((PowerManager)getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "TestGyroScopeActivity");
		
	}
	
}