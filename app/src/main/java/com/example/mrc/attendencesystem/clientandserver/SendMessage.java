package com.example.mrc.attendencesystem.clientandserver;

import com.example.mrc.attendencesystem.entity.MessageInfo;
import com.example.mrc.attendencesystem.entity.TimeInfo;

import java.io.ObjectOutputStream;

/**
 * Created by Mr.C on 2018/4/18.
 */

public class SendMessage {
    public static void sendMes(int dfAccount,String content,String type){
        try{
            int myAccount=MoreActivity.me.getAccount();
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConServer.getClientConServerThread(myAccount).getS().getOutputStream());
            MessageInfo m=new MessageInfo();
            m.setType(type);
            m.setSender(myAccount);
            m.setSenderNick(MoreActivity.me.getNick());
            m.setSenderAvatar(MoreActivity.me.getAvatar());
            m.setReceiver(dfAccount);
            m.setContent(content);
            m.setSendTime(TimeInfo.geTimeNoS());
            oos.writeObject(m);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sendADbuddy(int myAccount,int dfAccount ,String type){
        try{
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConServer.getClientConServerThread(myAccount).getS().getOutputStream());
            MessageInfo m=new MessageInfo();
            m.setType(type);
            m.setSender(myAccount);
            m.setReceiver(dfAccount);
            oos.writeObject(m);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
