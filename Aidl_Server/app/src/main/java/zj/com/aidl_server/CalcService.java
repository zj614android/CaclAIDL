package zj.com.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class CalcService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    IBinder mIBinder = new CalcAidl.Stub() {
        @Override
        public int calc(int a, int b) throws RemoteException {
            return a + b;
        }
    };

}
