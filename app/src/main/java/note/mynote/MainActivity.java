package note.mynote;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ShiTiLei.note;
import adapter.MyAdapter;
import me.drakeet.materialdialog.MaterialDialog;
import sqlite.NoteManager;


public class MainActivity extends AppCompatActivity {
    private NoteManager noteManager;
    private MyAdapter adapter;
    private List<note> notes=new ArrayList<>();
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteManager=new NoteManager(this);
        initView();
    }

    @Override
    public void onResume(){
        super.onResume();
        initView();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        noteManager.closeDB();
    }

    public void initView(){
        notes=noteManager.query();
        adapter=new MyAdapter(this,notes);
        final ListView listView=(ListView)findViewById(R.id.lv);
        listView.setAdapter(adapter);

        Button btn=(Button)findViewById(R.id.newBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

        //长按
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                final String[] items = {"删除", "消息置顶"};
                final ArrayAdapter<String> mAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, items);
                ListView mListD = new ListView(MainActivity.this);
                mListD.setDividerHeight(0);
                mListD.setAdapter(mAdapter);
                final MaterialDialog mMaterialDialog = new MaterialDialog(MainActivity.this);
                mMaterialDialog.setTitle("请选择操作:");
                mMaterialDialog.setContentView(mListD);
                mListD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        switch (pos) {
                            case 0: {
                                mMaterialDialog.dismiss();
                                noteManager.delete(notes.get(position));//做删除操作
                                //Toast.makeText(MainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(MainActivity.this, "id" + id, Toast.LENGTH_SHORT).show();
                                notes.remove(position);//移除当前记事
                                adapter.notifyDataSetChanged();
                                break;
                            }
                            case 1: {
                                mMaterialDialog.dismiss();
                                noteManager.update(notes.get(position));
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                });
                adapter.notifyDataSetChanged();
                mMaterialDialog.setCanceledOnTouchOutside(true);
                mMaterialDialog.show();
                return true;
            }

        });

        //单击编辑
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, InActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("inDetail", notes.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(MainActivity.this,AddActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
