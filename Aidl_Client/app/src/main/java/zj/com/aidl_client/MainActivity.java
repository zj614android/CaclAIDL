package zj.com.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zj.com.aidl_server.CalcAidl;

public class MainActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private TextView result;
    private Button calcbtn;
    private CalcAidl mCalcAidl;

    private ServiceConnection con = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCalcAidl = CalcAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        result = findViewById(R.id.res);
        calcbtn = findViewById(R.id.calcbtn);


        prepareAidl();

        calcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = Integer.parseInt(MainActivity.this.num1.getText().toString());
                int num2 = Integer.parseInt(MainActivity.this.num2.getText().toString());


                //调远程服务接口
                try {
                    int calc = mCalcAidl.calc(num1, num2);
                    result.setText("" + calc);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void prepareAidl() {

        Intent intent = new Intent();

        intent.setComponent(new ComponentName("zj.com.aidl_server","zj.com.aidl_server.CalcService"));

        bindService(intent,con, Context.BIND_AUTO_CREATE);
    }
}
