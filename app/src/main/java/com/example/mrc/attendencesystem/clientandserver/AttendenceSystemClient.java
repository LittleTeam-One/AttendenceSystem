package com.example.mrc.attendencesystem.clientandserver;

import android.content.Context;

import com.example.mrc.attendencesystem.activity.LaunchActivity;
import com.example.mrc.attendencesystem.entity.Message;
import com.example.mrc.attendencesystem.entity.MessageType;
import com.example.mrc.attendencesystem.entity.User;
import com.example.mrc.attendencesystem.server.SocketConnectService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.mob.tools.utils.DeviceHelper.getApplication;

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
            s = SocketConnectService.getSocket();
            if(s == null){
                return false;
            }
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            //Log.d("sendLoginInfo" ,"here");
            Message ms=(Message)ois.readObject();
            //Log.d("messagefff" ,ms.getContent().toString());

            if(ms.getType().equals(MessageType.SUCCESS)){
                LaunchActivity.myInfo=ms.getContent();
                ClientConServerThread ccst=new ClientConServerThread(context,s);
                ccst.start();
                ManageClientConServer.addClientConServerThread(((User)obj).getPhoneNumber(), ccst);
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
            s = SocketConnectService.getSocket();
            if(s == null){
                return false;
            }
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(obj);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Message ms=(Message)ois.readObject();
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
