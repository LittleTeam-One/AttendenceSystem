package com.example.mrc.attendencesystem.clientandserver;

import com.example.mrc.attendencesystem.entity.Message;

import java.io.ObjectOutputStream;

/**
 * Created by Mr.C on 2018/4/18.
 */

public class SendMessage {
    /*public static void sendMes(int dfAccount,String content,String type){
        try{
            String myAccount=MoreActivity.me.getAccount();
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConServer.getClientConServerThread(myAccount).getS().getOutputStream());
            Message m=new Message();
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
    }*/

    public static void sendADbuddy(String myAccount,String dfAccount ,String type){
        try{
            ObjectOutputStream oos = new ObjectOutputStream
                    (ManageClientConServer.getClientConServerThread(myAccount).getS().getOutputStream());
            Message m=new Message();
            m.setType(type);
            m.setSender(myAccount);
            m.setReceiver(dfAccount);
            oos.writeObject(m);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
