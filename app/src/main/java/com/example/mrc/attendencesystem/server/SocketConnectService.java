package com.example.mrc.attendencesystem.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.example.mrc.attendencesystem.AttendenceSystem;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketConnectService extends Service {
    private static Socket s;
    public static final String hostname = AttendenceSystem.hostname;
    public static final int port = AttendenceSystem.port;
    public static final int timeout = AttendenceSystem.timeout;


    public SocketConnectService() {
    }

    public static Socket getSocket(){
        if(s == null){
            s = new Socket();
            try{
                s.connect(new InetSocketAddress(hostname ,port),timeout);
            }catch(SocketTimeoutException e){

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    @Override
    public IBinder onBind(Intent intent) {
        try {
            s = new Socket();
            try{
                s.connect(new InetSocketAddress(hostname ,5469),2000);
            }catch(SocketTimeoutException e){
                Handler handlerThree=new Handler(Looper.getMainLooper());
                handlerThree.post(new Runnable(){
                    public void run(){
                        Toast.makeText(getApplicationContext(),
                                "与远程服务器连接超时，请检查网络是否可用！", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (IOException e) {
            Handler handlerThree=new Handler(Looper.getMainLooper());
            handlerThree.post(new Runnable(){
                public void run(){
                    Toast.makeText(getApplicationContext(),
                            "与远程服务器连接中断，请检查网络是否可用！", Toast.LENGTH_LONG).show();
                }
            });
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
