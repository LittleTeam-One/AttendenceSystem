package com.example.mrc.campus_social.clientandserver;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * Created by mqh on 2017/10/30.
 */
public class ClientInputThread extends Thread {
    private Socket mSocket;
    private boolean isStart = true;
    private DataInputStream inputStream;// 对象输入流
    private InputStreamReader iReader;
    private String msg;
    private MessageListener mMessageListener;//消息监听接口

    public ClientInputThread(Socket socket)
    {
        this.mSocket = socket;
        try{
            inputStream = new DataInputStream(socket.getInputStream());// 实例化数据输入流
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void setMessageListener(MessageListener messageListener)
    {
        this.mMessageListener = messageListener;
    }
    public void setStart(Boolean isStart)
    {
        this.isStart = isStart;
    }
    @Override
    public void run() {
        try {
            System.out.println("Input isStart: " + isStart);
            while (isStart) {
                iReader = new InputStreamReader(inputStream, "UTF-8");
                char[] buffer = new char[1024];
                int count = 0;
                StringBuilder sBuilder = new StringBuilder();
                while ((count = iReader.read(buffer,0,buffer.length))>-1) {
                    sBuilder.append(buffer,0,count);
                    if(sBuilder.charAt(sBuilder.length() - 1) == '}')
                        break;
                }
                mMessageListener.Message(sBuilder.toString());
            }
            if(inputStream !=null)
            {
                inputStream.close();
            }
            if (mSocket != null)
                mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
