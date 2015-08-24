package com.example.antonnazareth.keeper.EntityClasses;

import android.database.Cursor;

import com.example.antonnazareth.keeper.data.KeeperContract;

/**
 * Store a row from the Users table.
 */
public class UserEntity extends BaseEntity {

    public int id;
    public String forename;
    public String surname;
    public String nickname;

    public UserEntity(String jsonRow){

        this.id = gson.fromJson("id", int.class);
        this.forename = gson.fromJson("forename", String.class);
        this.surname = gson.fromJson("forename", String.class);
        this.nickname = gson.fromJson("nickname", String.class);

    }

    public UserEntity(int id, String forename, String surname, String
            nickname){

        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.nickname = nickname;

    }

    public UserEntity(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.forename = cursor.getString(1);
        this.surname = cursor.getString(2);
        this.nickname = cursor.getString(3);

    }

}
