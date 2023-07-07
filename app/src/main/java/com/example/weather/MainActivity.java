package com.example.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    private List<Integer> idList = new ArrayList<>();//id列表
    private List<Integer> pidList = new ArrayList<>();//pid列表（父id列表）
    private List<String> city_nameList = new ArrayList<>();//城市名字列表
    private List<String> city_codeList = new ArrayList<>();//城市代码列表
    ArrayAdapter simpleAdapter;//适配器，用于ListView
    Button MyConcern;//关注
    ListView ProvinceList;//显示省份列表

//解析省列表  getJson方法获取String
    private void parseJSONWithJSONObject(String jsonData){

        try{
            JSONArray jsonArray = new JSONArray(jsonData);//获取JsonArray
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);//从JsonArray获取JSONObject
                Integer id = jsonObject.getInt("id");
                Integer pid = jsonObject.getInt("pid");
                String city_code = jsonObject.getString("city_code");
                String city_name = jsonObject.getString("city_name");
                //父id表明是一个省份，把对应的数据添加上去
                if(pid == 0 ) {
                    idList.add(id);
                    pidList.add(pid);
                    city_codeList.add(city_code);
                    city_nameList.add(city_name);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//读取data.json文件，获取String数据
    public static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);//打开assets/data.json文件
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProvinceList = findViewById(R.id.provincelist);
        MyConcern = findViewById(R.id.myconcern);
        MyConcern.setOnClickListener(this);

        String responseData = getJson("data.json",this);
        parseJSONWithJSONObject(responseData);


        simpleAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,city_nameList);

        ProvinceList.setAdapter(simpleAdapter);
        ProvinceList = findViewById(R.id.provincelist);
        ProvinceList.setOnItemClickListener(new OnItemClickListener(){      //配置ArrayList中的每一个Item的响应事件
            @Override
            public void  onItemClick(AdapterView<?> parent, View view , int position , long id){
                int tran = idList.get(position);//获取点击按钮对应的的城市ID
                Intent intent = new Intent(MainActivity.this, com.example.weather.SecondActivity.class);
                intent.putExtra("tran",tran);//通过intent传送给下一个页面SecondActivity
                startActivity(intent);
            }
        });




    }
    @Override
    public void onClick(View v){
        if (v.getId() == R.id.myconcern) {
Intent intent = new Intent(MainActivity.this, MyConcernList.class);
            startActivity(intent);
        }//关注按钮的响应事件
    }



}
