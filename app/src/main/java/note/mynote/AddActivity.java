package note.mynote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ShiTiLei.note;
import sqlite.NoteManager;


public class AddActivity extends AppCompatActivity {
    private NoteManager noteManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        noteManager=new NoteManager(this);
        click();
        Log.d("Dog","添加成功");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        noteManager.closeDB();
    }

    public void add(){
        EditText ed_title=(EditText)findViewById(R.id.title_ed);
        //EditText ed_time=(EditText)findViewById(R.id.time_tv);
        EditText ed_content=(EditText)findViewById(R.id.content_ed);

        String title=ed_title.getText().toString().trim();
        String content=ed_content.getText().toString();

        SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
        Date date=new Date(System.currentTimeMillis());
        String time=format.format(date);

        int id=getPreferences();

        note note=new note();
        note.setId(id);
        //note.setTopId(id);
        note.setTitle(title);
        note.setContent(content);
        note.setTime(time);
        if(title.equals("")||content.equals("")){
            Toast.makeText(this,"请填写标题和内容",Toast.LENGTH_LONG).show();
            return;
        }
        if(noteManager.insert(note)){
            getIdEditor(id);
            Toast.makeText(this,"保存成功！",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"id"+id,Toast.LENGTH_LONG).show();
            Intent intent=new Intent(AddActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"保存不成功！",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"id"+id,Toast.LENGTH_LONG).show();
        }
        noteManager.closeDB();

    }
    private int getPreferences(){
        sharedPreferences=getSharedPreferences("id",Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id",1);

    }
    private int getIdEditor(int id){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("id",++id);
        editor.commit();
        return id;
    }
    private void click(){
        Button saveBtn=(Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
