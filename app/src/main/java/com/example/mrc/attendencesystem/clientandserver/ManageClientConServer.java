package com.example.mrc.attendencesystem.clientandserver;

import java.util.HashMap;

/**
 * Created by Mr.C on 2018/4/18.
 */

public class ManageClientConServer {

    private static HashMap hm=new HashMap<Integer,ClientConServerThread>();
    public static void addClientConServerThread(String account, ClientConServerThread ccst){
        hm.put(account, ccst);
    }

    public static ClientConServerThread getClientConServerThread(int i){
        return (ClientConServerThread)hm.get(i);
    }
}
