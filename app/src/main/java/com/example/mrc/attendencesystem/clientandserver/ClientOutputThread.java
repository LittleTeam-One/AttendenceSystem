package com.example.mrc.attendencesystem.clientandserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
/**
 * Created by mqh on 2017/10/30.
 */
public class ClientOutputThread extends Thread {
    private Socket socket;
    //OutputStreamWriter oWriter;
    private DataOutputStream dataOutputStream;
    private OutputStreamWriter oStreamWriter;
    private boolean isStart = true;
    private String msg;

    public ClientOutputThread(Socket socket) {
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());// 在构造器里面实例化对象输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    // 这里处理跟服务器是一样的
    public void setMsg(String msg) {
        this.msg = msg;
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {

        try {

            while (isStart) {
                if (msg != null) {
                    if (socket == null)
                        return;

	                   //Log.d("OutputThreadmsg:",msg);
                    oStreamWriter = new OutputStreamWriter(dataOutputStream, "UTF-8");
                    StringBuffer sBuilder = new StringBuffer();
                    sBuilder.append(msg);
                    oStreamWriter.write(sBuilder.toString());
                    oStreamWriter.flush();
                    synchronized (this) {
                        wait();// 发送完消息后，线程进入等待状态
                    }

                }
            }
            if(oStreamWriter != null)
            {
                oStreamWriter.close();
            }
            if (dataOutputStream != null)// 循环结束后，关闭流，释放资源
                dataOutputStream.close();
            // 循环结束后，关闭输出流和socket
            if (socket != null)
                socket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
