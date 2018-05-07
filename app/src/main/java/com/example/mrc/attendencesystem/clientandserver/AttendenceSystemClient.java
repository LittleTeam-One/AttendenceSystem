package com.example.mrc.attendencesystem.clientandserver;

import android.content.Context;

import com.example.mrc.attendencesystem.activity.LaunchActivity;
import com.example.mrc.attendencesystem.entity.MessageInfo;
import com.example.mrc.attendencesystem.entity.MessageType;
import com.example.mrc.attendencesystem.entity.UserInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Mr.C on 2018/4/18.
 */

public class AttendenceSystemClient {

    private Context context;
    public Socket s;
    public AttendenceSystemClient(Context context){
        this.context=context;
    }
    public boolean sendLoginInfo(Object obj){
        boolean b=false;
        try {
            s = new Socket();
            try{
                s.connect(new InetSocketAddress("192.168.1.128",5469),2000);
            }catch(SocketTimeoutException e){
                return false;
            }
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            MessageInfo ms=(MessageInfo)ois.readObject();
            if(ms.getType().equals(MessageType.SUCCESS)){
                LaunchActivity.myInfo=ms.getContent();
                ClientConServerThread ccst=new ClientConServerThread(context,s);
                ccst.start();
                ManageClientConServer.addClientConServerThread(((UserInfo)obj).getAccount(), ccst);
                b=true;
            }else if(ms.getType().equals(MessageType.FAIL)){
                b=false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public boolean sendRegisterInfo(Object obj){
        boolean b=false;
        try {
            s=new Socket();
            try{
                s.connect(new InetSocketAddress("192.168.1.101",5469),2000);
            }catch(SocketTimeoutException e){
                return false;
            }
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            MessageInfo ms=(MessageInfo)ois.readObject();
            if(ms.getType().equals(MessageType.SUCCESS)){
                b=true;
            }else if(ms.getType().equals(MessageType.FAIL)){
                b=false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
}
