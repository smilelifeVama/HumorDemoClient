package fun.xilei.humordemoclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.humor.debugactivity.BaseDebugActivity;
import com.humor.debugactivity.DemoButton;

import java.util.ArrayList;
import java.util.List;

import fun.xilei.humordemo.IMyAidlInterface;
import fun.xilei.humordemo.Student;

public class MainActivity extends BaseDebugActivity {
    private IMyAidlInterface mBinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDemoButtons(new DemoButton(this, "bindService", new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                bindService();
            }
        }), new DemoButton(this, "addStudent", new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                addStudent(1, "humor");
                showTips("addHumor");
            }
        }));
        addDemoButtons(new DemoButton(this, "addYCY", new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                addStudent(2, "ycy");
                showTips("addYCY");
            }
        }), new DemoButton(this, "getStudents", new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    getStudents();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void getStudents() throws RemoteException {
        if (mBinder != null) {
            ArrayList<Student> students = (ArrayList<Student>) mBinder.getCurrentStudents();
            if (students != null) {
                showTips(students.toString());
            }
        }
    }

    private void addStudent(int id, String name) {
        if (mBinder != null) {
            try {
                mBinder.addStudent(new Student(id, name));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            showTips("onServiceConnected");
            if (service != null) {
                mBinder = IMyAidlInterface.Stub.asInterface(service);
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            showTips("onServiceDisconnected");
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("fun.xilei.humordemo.StudentService");
        intent.setPackage("fun.xilei.humordemo");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        showTips("bindService");
    }
}
