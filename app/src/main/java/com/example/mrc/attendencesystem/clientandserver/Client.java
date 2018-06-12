package com.example.mrc.attendencesystem.clientandserver;

/**
 * Created by mqh on 2017/10/28.
 */

import android.util.Log;

import java.net.Socket;

public class Client {
    private int port;
    private String address = null;
    private Socket mSocket;
    private boolean mIsConnected = false;
    private ClientThread clientThread;


    public void setSocket(Socket socket) {
        this.mSocket = socket;
    }
    public Client()
    {}

    public Client(String ip,int port)
    {
        this.address = ip;
        this.port = port;
    }
    public boolean start() {
        try {
            mSocket = new Socket(address,port);
            Log.d("testServer","client start,ip:"+address+",port:"+port);
            //mSocket.connect(new InetSocketAddress(address, port), 3000);
            if (mSocket.isConnected()) {
                System.out.println("Connected..");
                clientThread = new ClientThread(mSocket);
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 直接通过client得到读线程
    public ClientInputThread getClientInputThread() {
        return clientThread.getIn();
    }

    // 直接通过client得到写线程
    public ClientOutputThread getClientOutputThread() {
        return clientThread.getOut();
    }

    // 直接通过client停止读写消息
    public void setIsStart(boolean isStart) {
        clientThread.getIn().setStart(isStart);
        clientThread.getOut().setStart(isStart);
    }


    public void sendData(String s){

    }
    public String getData()
    {
        String s = "s";
        return s;
    }
    // 直接通过client得到读线程




   public class ClientThread extends Thread {

       private ClientInputThread in;
       private ClientOutputThread out;

       public ClientThread(Socket socket) {
           in = new ClientInputThread(socket);
           out = new ClientOutputThread(socket);
       }

       public void run() {
           in.setStart(true);
           out.setStart(true);
           in.start();
           out.start();
       }

       // 得到读消息线程
       public ClientInputThread getIn() {
           return in;
       }

       // 得到写消息线程
       public ClientOutputThread getOut() {
           return out;
       }
   }

}
