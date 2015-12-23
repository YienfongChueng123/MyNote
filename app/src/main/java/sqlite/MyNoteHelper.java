package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zyf on 2015/10/30.
 */
public class MyNoteHelper extends SQLiteOpenHelper{
    //private Context context;
    //数据库名称
    private static final String DATABASE_NAME="noteDB.db";
    //数据库版本
    private static int DATABASE_VERSION=1;

    //构造函数
    public MyNoteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //创建表
    public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS MyNotes3("+"id integer primary key autoincrement,"+"title TEXT,"+"time TEXT ,"+"content TEXT,"+"TopId Integer autoincrement default '1')";

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        db.execSQL("drop table if exists MyNote");
        db.execSQL("drop table if exists MyNote1");
        db.execSQL("drop table if exists Note");
        Log.d("Dog","创建表成功");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}

