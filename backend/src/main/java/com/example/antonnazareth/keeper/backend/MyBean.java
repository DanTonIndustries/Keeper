package com.example.antonnazareth.keeper.backend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.logging.Logger;

/**
 * The object model for the data we are sending through endpoints
 */
class ResultSetConverter {
    public static JsonArray convert( ResultSet rs )
            throws SQLException
    {
        Gson gson = new Gson();
        JsonArray json = new JsonArray();
        ResultSetMetaData rsmd = rs.getMetaData();

        while(rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JsonObject obj = new JsonObject();

            for (int i=1; i<numColumns+1; i++) {
                String column_name = rsmd.getColumnName(i);

                if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                    obj.addProperty(column_name, gson.toJson(rs.getArray
                            (column_name)));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                    obj.addProperty(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                    obj.addProperty(column_name, rs.getBoolean(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                    obj.addProperty(column_name, rs.getString(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                    obj.addProperty(column_name, rs.getDouble(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                    obj.addProperty(column_name, rs.getFloat(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                    obj.addProperty(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                    obj.addProperty(column_name, rs.getNString(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                    obj.addProperty(column_name, rs.getString(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                    obj.addProperty(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                    obj.addProperty(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                    obj.addProperty(column_name, gson.toJson(rs.getDate(column_name)));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                    obj.addProperty(column_name, gson.toJson(rs.getTimestamp(column_name)));
                }
                else{
                    obj.addProperty(column_name, rs.getString(column_name));
                }
            }

            json.add(obj);
        }

        return json;
    }
}

public class MyBean {

    private static final Logger logger = Logger.getLogger(MyBean.class.getName
            ());

    private String json = "";
    private String string_extra = "";

    public String getResultsSetData() {
        logger.info("Calling getResultsSetData method");
        return json;
    }

    public String getStringData() {
        logger.info("Calling getStringData method");
        return string_extra;
    }

    public void addToStringData(String extra_string) {
        logger.info("Calling addToStringData method");
        string_extra = string_extra + extra_string;
    }

    public void setResultSetData(ResultSet rs)
            throws SQLException {
        logger.info("Calling setResultSetData method");
        JsonArray array = ResultSetConverter.convert(rs);
        json = array.toString();
    }

    public void setStringData(String data) {
        logger.info("Calling setStringData method");
        string_extra = data;
    }

    public void setData(String stringData, ResultSet rs)
            throws SQLException {
        logger.info("Calling setData method");
        string_extra = stringData;
        JsonArray array = ResultSetConverter.convert(rs);
        json = array.toString();
    }
}