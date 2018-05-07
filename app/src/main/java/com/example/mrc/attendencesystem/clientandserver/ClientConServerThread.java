package com.example.mrc.attendencesystem.clientandserver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.mrc.attendencesystem.entity.MessageInfo;
import com.example.mrc.attendencesystem.entity.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Mr.C on 2018/4/18.
 */

public class ClientConServerThread extends Thread {
    private Context context;
    private  Socket s;
    public Socket getS() {return s;}
    public ClientConServerThread(Context context,Socket s){
        this.context=context;
        this.s=s;
    }

    @Override
    public void run() {
        while(true){
            ObjectInputStream ois = null;
            MessageInfo m;
            try {
                ois = new ObjectInputStream(s.getInputStream());
                m=(MessageInfo) ois.readObject();
                if(m.getType().equals(MessageType.COM_MES)
                        || m.getType().equals(MessageType.GROUP_MES)){
                    Intent intent = new Intent("com.example.mrc.attendencesystem");
                    String[] message=new String[]{
                            m.getSender()+"",
                            m.getSenderNick(),
                            m.getSenderAvatar()+"",
                            m.getContent(),
                            m.getSendTime(),
                            m.getType()};
                    Log.d("--", message.toString());
                    intent.putExtra("message", message);
                    context.sendBroadcast(intent);
                }else if(m.getType().equals(MessageType.RET_ONLINE_FRIENDS)){
                    String s[] = m.getContent().split(",");
                    //BuddyActivity.buddyStr=s[0];
                   // GroupActivity.groupStr=s[1];
                }
                if(m.getType().equals(MessageType.SUCCESS)){
                    Toast.makeText(context, "hhhhhh", Toast.LENGTH_SHORT);
                }
            } catch (Exception e) {
                //e.printStackTrace();
                try {
                    if(s!=null){
                        s.close();
                    }
                } catch (IOException e1) {
                    //e1.printStackTrace();
                }
            }
        }
    }
}
