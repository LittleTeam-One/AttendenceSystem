package com.example.mrc.attendencesystem;

import com.example.mrc.attendencesystem.entity.GroupMessage;
import com.example.mrc.attendencesystem.entity.TranObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    String json = "{\n" +
            "  \"type\": \"GET_GROUP_MESSAGE\",\n" +
            "  \"isSuccess\": true,\n" +
            "  \"groupMessageArrayList\": [\n" +
            "    {\n" +
            "      \"messageId\": 41,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 40,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"12\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 39,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 38,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 37,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"12\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 36,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 35,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 34,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 33,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 32,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 31,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 30,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 29,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 28,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 27,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 26,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 25,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 24,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 23,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"messageId\": 22,\n" +
            "      \"groupId\": 2,\n" +
            "      \"fromId\": \"1\",\n" +
            "      \"contentType\": 1,\n" +
            "      \"content\": \"123546\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";
    @Test
    public void test(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        // Log.d("client", "onStartCommand:msg " +msg);
        JsonReader jsonReader = new JsonReader(new StringReader(json));//其中jsonContext为String类型的Json数据
        jsonReader.setLenient(true);
        TranObject responseObject = gson.fromJson(jsonReader,TranObject.class);
        ArrayList<GroupMessage> groupMessageList = responseObject.getGroupMessageArrayList();
        for(GroupMessage e : groupMessageList){
            System.out.println("contentType:"+e.getContentType()
                    +"content:"+e.getContent()+"groupId:"+e.getGroupId()
                    +"fromId:"+e.getFromId()+"messageId:"+e.getMessageId());
        }
        System.out.println(responseObject.getType());
    }
    @Test
    public void addition_isCorrect() throws Exception {

    }
}