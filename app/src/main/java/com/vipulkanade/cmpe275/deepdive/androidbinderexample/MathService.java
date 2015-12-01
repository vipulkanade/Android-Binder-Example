package com.vipulkanade.cmpe275.deepdive.androidbinderexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.vipulkanade.cmpe275.deepdive.aidl.IMathService;

/**
 * This MathService class exposes the remote service (functions in AIDL file, which we need to expose to other apps) to the client
 */
public class MathService extends Service {

    private static final String TAG = "MathService";

    public MathService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IMathService.Stub() {
            /**
             * In the AIDL file we just add the declaration of the function
             * here is the real implementation of the Addition() function below
             */
            @Override
            public double Addition(double input1, double input2) throws RemoteException {
                Log.i(TAG, "MathService.Addition(" + input1 + ", " + input2 + ")");
                return (input1 + input2);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
