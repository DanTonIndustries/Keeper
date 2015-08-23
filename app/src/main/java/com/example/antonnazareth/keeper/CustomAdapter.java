package com.example.antonnazareth.keeper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by antonnazareth on 22/08/2015.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<String> result;
    Context context;
    //OnClickListener clickListener;
    int [] imageId;

    private static LayoutInflater inflater=null;

//    public CustomAdapter(Activity activity, String[] itemStrings, int[] itemImages) {
    public CustomAdapter(Activity activity, ArrayList<String> itemStrings){//}, OnClickListener clickListen) {

        result=itemStrings;
        context=activity;
        //clickListener = clickListen;
        //imageId=itemImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

//    private void setClickListeners(View view) {
//        view.setOnClickListener(clickListener);
//    }

    public class Holder
    {
        TextView tv;
        Button btn;
        //ImageView img;
    }

    public void add(String text){
        result.add(text);
    }

    public void remove(int position){
        result.remove(position);
        notifyDataSetChanged();
    }

    public String getText(int position) {
        // TODO Auto-generated method stub
        return result.get(position);
    }

    public void clearData() {
        // clear the data
        result.clear();
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        Holder holder=new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.generic_list_item, null);

        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        String res = result.get(position);
        holder.tv.setText(res);

        holder.btn=(Button) rowView.findViewById(R.id.delete_btn);

        holder.btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });




//        Button deleteBtn = (Button)rowView.findViewById(R.id.delete_btn);
//        deleteBtn.setTag(res);
        //setClickListeners(deleteBtn);


        //setClickListeners(rowView);

//        deleteBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                result.remove(position); //or some other task
//                notifyDataSetChanged();
//            }
//        });

//        deleteBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
//            }
//        });

        //holder.img.setImageResource(imageId[position]);
//        rowView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked " + result.get(position), Toast.LENGTH_LONG).show();
//            }
//        });
        return rowView;
    }

}