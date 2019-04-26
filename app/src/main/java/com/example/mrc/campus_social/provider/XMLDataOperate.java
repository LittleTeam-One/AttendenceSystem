package com.example.mrc.campus_social.provider;

import android.os.Environment;
import android.util.Xml;

import com.example.mrc.campus_social.entity.MessageItem;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 新建xml文件以及xml文件数据存入和读取的方法
 */
public class XMLDataOperate {


    /**
     * xml文件的写入方法
     * @param type  标识好友或者群聊，1表示好友，2表示群聊
     * @param id    标识好友或者群聊的id
     * @param messageItems  聊天的内容
     */
    public void writeData(int type , int id , List<MessageItem> messageItems){
        String fileName = null;
        if (type == 1){
            fileName = "friend_";
        }else if (type == 2){
            fileName = "group_";
        }
        try {
            /* 文件名是fileName + id */
            File file = new File(Environment.getExternalStorageDirectory(),
                    fileName + id);
            FileOutputStream fos = new FileOutputStream(file);
            // 获得一个序列化工具
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "utf-8");
            // 设置文件头
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "persons");
            for (int i = 0; i < 10; i++) {
                serializer.startTag(null, "person");
                serializer.attribute(null, "id", String.valueOf(i));
                // 写姓名
                serializer.startTag(null, "name");
                serializer.text("张三" + i);
                serializer.endTag(null, "name");
                // 写性别
                serializer.startTag(null, "gender");
                serializer.text("男" + i);
                serializer.endTag(null, "gender");
                // 写年龄
                serializer.startTag(null, "age");
                serializer.text("1" + i);
                serializer.endTag(null, "age");

                serializer.endTag(null, "person");
            }
            serializer.endTag(null, "persons");
            serializer.endDocument();
            fos.close();
            /*Toast.makeText(MainActivity.this, "写入成功", 0).show();*/
        } catch (Exception e) {
            e.printStackTrace();
            /*Toast.makeText(MainActivity.this, "写入失败", 0).show();*/
        }
    }

    /**
     * @param type
     * @param id
     * @return
     */
    public List<MessageItem> readData(int type ,int id){
        List<MessageItem> messageItems = null;
        return messageItems;
    }
}
