package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ShiTiLei.note;

/**
 * Created by zyf on 2015/10/30.
 */
public class NoteManager {
    private MyNoteHelper helper;
    private SQLiteDatabase db;
    private Context context;
    public NoteManager(Context context){
        //super(context,name,factory,version);
        helper= new MyNoteHelper(context);
        db=helper.getWritableDatabase();//打开一个读写的数据库
        this.context=context;
    }
    //插入
    public boolean insert(note note){
    db.beginTransaction();//开始事务锁
        try {
            db.execSQL("INSERT INTO MyNotes3 VALUES(?,?,?,?,?)",new Object[]{note.getId(),note.getTopId(),note.getTitle(),note.getTime(),note.getContent()});
            db.setTransactionSuccessful();//完成事务锁
            Log.d("Dog", "插入功能");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.endTransaction();//结束事务锁
            db.close();
        }
    }
    //删除
    public void delete(note note){
        db.delete("MyNotes3", "id=?", new String[]{String.valueOf(note.getId())});
        Log.d("Dog", "删除功能");
    }

    //查询
    public List<note> query() {
        List<note> notes = new ArrayList<>();
        Cursor c = queryItem();
        while (c.moveToNext()) {
            note note = new note();
            note.setId(c.getInt(c.getColumnIndex("id")));
            //note.setTopId(c.getInt(c.getColumnIndex("TopId")));
            note.setTitle(c.getString(c.getColumnIndex("title")));
            note.setContent(c.getString(c.getColumnIndex("content")));
            note.setTime(c.getString(c.getColumnIndex("time")));
            notes.add(note);
        }
        c.close();
        return notes;
    }

    //更新
    public boolean update(note note){
        db.beginTransaction();
        try {
            ContentValues values=new ContentValues();
            values.put("title",note.getTitle());
            values.put("content",note.getContent());
            values.put("time", note.getTime());
            //values.put("TopId",note.getTopId());
            values.put("id",note.getId());
            db.update("MyNotes3", values, "id=?", new String[]{String.valueOf(note.getId())});
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){
                e.printStackTrace();
            return false;
        }finally {
            db.endTransaction();
            db.close();
        }
    }

public Cursor queryItem(){

    return db.rawQuery("SELECT * FROM MyNotes3 ORDER BY time DESC,TopId ASC",null);

}
    //关闭数据库
    public void closeDB(){
        db.close();
    }
}
