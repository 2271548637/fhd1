package com.example.weather;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper{     //对数据库进行创建
    public static  final String CREATE_NOTE = "create table Weather("
            + "id integer primary key not null,"
            + "data String not null )";
    //创建一个weather数据库的语句，用于存放从服务器get到的json数据
    //避免重复调用接口
    public static final String CREATE_CONCERN = "create table Concern("
            +"city_code String primary key not null,"
            +"city_name String not null)";
    //创建一个concern关注的城市数据库的语句
    private Context mContext;
    public MyDataBaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }
    @Override
    public void  onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_NOTE);
        db.execSQL(CREATE_CONCERN);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
