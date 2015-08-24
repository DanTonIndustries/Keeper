package com.example.antonnazareth.keeper.EntityClasses;

import android.database.Cursor;

import com.example.antonnazareth.keeper.data.KeeperContract;

/**
 * Store a row from the Teams table.
 */
public class TeamEntity extends BaseEntity {

    public int id;
    public String name;

    public TeamEntity(String jsonRow){

        this.id = gson.fromJson("id", int.class);
        this.name = gson.fromJson("name", String.class);

    }

    public TeamEntity(int id, String name){

        this.id = id;
        this.name = name;

    }

    public TeamEntity(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.name = cursor.getString(1);
    }

}
