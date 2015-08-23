package com.example.antonnazareth.keeper.data;

import com.example.antonnazareth.keeper.EntityClasses.UserEntity;
import com.example.antonnazareth.keeper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * A class to help represent UserEntity objects in list views.
 */
public class UserAdapter extends BaseAdapter {

    Context context;

    protected List<UserEntity> listUsers;
    LayoutInflater inflater;

    public UserAdapter(Context context, List<UserEntity> listUsers) {
        this.listUsers = listUsers;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void add(UserEntity user){
        listUsers.add(user);
    }

    public void remove(int position){
        listUsers.remove(position);
        notifyDataSetChanged();
    }

    public int getId(int position) {
        UserEntity user = listUsers.get(position);
        return user.id;
    }

    public int getCount() {
        return listUsers.size();
    }

    public UserEntity getItem(int position) {
        return listUsers.get(position);
    }

    public long getItemId(int position) {
        return listUsers.get(position).id;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.generic_list_item,
                    parent, false);

            holder.txtNickname = (TextView) convertView.findViewById(R.id
                    .textView1);
            holder.btn = (Button) convertView.findViewById(R.id.delete_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserEntity user = listUsers.get(position);
        holder.txtNickname.setText(user.nickname);

        return convertView;
    }

    private class ViewHolder {
        TextView txtNickname;
        Button btn;
    }

}
