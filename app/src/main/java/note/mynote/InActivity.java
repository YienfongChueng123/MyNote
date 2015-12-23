package note.mynote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

import ShiTiLei.note;
import sqlite.NoteManager;

public class InActivity extends AppCompatActivity {
    private NoteManager noteManager;
    private note note=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        noteManager=new NoteManager(this);
        init();
        click1();//修改
        click2();//保存
    }
    private void click1(){
        Button updateBtn=(Button)findViewById(R.id.upgradeBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) findViewById(R.id.title_ined);
                EditText content = (EditText) findViewById(R.id.content_ined);
                title.setEnabled(true);
                content.setEnabled(true);
                title.setTextColor(Color.BLACK);
                content.setTextColor(Color.BLACK);

            }
        });
    }
    private void click2(){
        Button saveBtn=(Button)findViewById(R.id.save_inBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail();

            }
        });
    }
    private void init(){
        EditText title=(EditText)findViewById(R.id.title_ined);
        EditText time=(EditText)findViewById(R.id.time_tvin);
        EditText content=(EditText)findViewById(R.id.content_ined);

        Intent intent=this.getIntent();
        note=(note)intent.getSerializableExtra("inDetail");
        title.setText(note.getTitle());
        content.setText(note.getContent());
        title.setEnabled(false);
        content.setEnabled(false);
        time.setEnabled(false);
        title.setTextColor(Color.GRAY);
        content.setTextColor(Color.GRAY);
    }
    private void updateDetail(){
        EditText title=(EditText)findViewById(R.id.title_ined);
        //EditText time=(EditText)findViewById(R.id.time_tvin);
        EditText content=(EditText)findViewById(R.id.content_ined);

        String title1=title.getText().toString();
        String content1=content.getText().toString();
        //String time2=time.getText(time1.get(position).getTime()).toString();
        SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
        Date date=new Date(System.currentTimeMillis());
        String time1=format.format(date);
        note note1=new note();
        note1.setId(note.getId());
        note1.setTitle(title1);
        note1.setContent(content1);
        note1.setTime(time1);


        if(noteManager.update(note1)){
            Toast.makeText(this,"更改成功！",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(InActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"更新失败，保存不成功！",Toast.LENGTH_LONG).show();
        }
            noteManager.closeDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in, menu);
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
