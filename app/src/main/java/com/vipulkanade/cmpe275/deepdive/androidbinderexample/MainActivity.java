package com.vipulkanade.cmpe275.deepdive.androidbinderexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vipulkanade.cmpe275.deepdive.aidl.IMathService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private IMathService mathService;
    private MathServiceConnection serviceConnection;
    private EditText value1;
    private EditText value2;
    private EditText result;

    double number_1 = 0, number_2 = 0, math_result = -1;
    boolean return_value;

    class MathServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mathService = IMathService.Stub.asInterface( (IBinder) service);
            Log.i(TAG, "onServiceConnected(): Connected");
            Toast.makeText(MainActivity.this, "Binder Example Service Connected", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mathService = null;
            Log.i(TAG, "onServiceDisconnected(): Disconnected");
            Toast.makeText(MainActivity.this, "Binder Example Service Disconnected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        value1	= (EditText) findViewById(R.id.editTextValue1);
        value2  = (EditText) findViewById(R.id.editTextValue2);
        result = (EditText) findViewById(R.id.editTextResult);

        initiateService();
    }

    /** This is our function which binds our activity(MainActivity) to our service(MathService). */
    private void initiateService() {
        Log.i(TAG, "initiateService()" );
        serviceConnection = new MathServiceConnection();
        Intent intent = new Intent();
        intent.setClassName("com.vipulkanade.cmpe275.deepdive.androidbinderexample",
                com.vipulkanade.cmpe275.deepdive.androidbinderexample.MathService.class.getName());
        return_value = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "initiateService() bound value: " + return_value);
    }

    public void onResult(View view) {
        number_1 = Double.parseDouble(value1.getText().toString());
        number_2 = Double.parseDouble(value2.getText().toString());
        try {
            math_result = mathService.Addition(number_1, number_2);
        } catch (RemoteException e) {
            Log.i(TAG, "Data fetch failed with: " + e);
            e.printStackTrace();
        }
        result.setText(new Double(math_result).toString());
    }

    /** This is our function to un-binds this activity from our service. */
    private void releaseService() {
        unbindService(serviceConnection);
        serviceConnection = null;
        Log.d(TAG, "releaseService(): unbound.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }
}
