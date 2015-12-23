package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ShiTiLei.note;
import note.mynote.R;


public class MyAdapter extends BaseAdapter{
    private List<note> noteList;
    private LayoutInflater inflater;
    private Context context=null;
    public MyAdapter(Context context,List<note> noteList){
        this.context=context;
        this.noteList=noteList;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount(){
        return noteList.size();
    }
    @Override
    public Object getItem(int position){
        return noteList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;//创建对象，将控件实例放在viewHolder里
        if(convertView==null){
            convertView=inflater.inflate(R.layout.layout,null);
            holder=new ViewHolder();
            holder.title=(TextView)convertView.findViewById(R.id.title_view);
            holder.time=(TextView)convertView.findViewById(R.id.time_view);

            holder.title.setTextColor(Color.BLACK);
            holder.title.setTextSize(30);
            holder.time.setTextColor(Color.BLACK);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.title.setText(noteList.get(position).getTitle());//显示标题
        holder.time.setText(noteList.get(position).getTime());//显示时间

        return convertView;//返回布局
    }
//内部类，用于对控件的实例进行缓存
    class ViewHolder{
        TextView title,time;
    }

}
