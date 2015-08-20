/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.antonnazareth.keeper.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.*;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONException;
import java.sql.*;
import com.google.appengine.api.utils.SystemProperty;

import javax.inject.Named;

public class ResultSetConverter {
    public static JSONArray convert( ResultSet rs )
            throws SQLException, JSONException
    {
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();

        while(rs.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();

            for (int i=1; i<numColumns+1; i++) {
                String column_name = rsmd.getColumnName(i);

                if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                    obj.put(column_name, rs.getArray(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                    obj.put(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                    obj.put(column_name, rs.getBoolean(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                    obj.put(column_name, rs.getBlob(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                    obj.put(column_name, rs.getDouble(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                    obj.put(column_name, rs.getFloat(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                    obj.put(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                    obj.put(column_name, rs.getNString(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                    obj.put(column_name, rs.getString(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                    obj.put(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                    obj.put(column_name, rs.getInt(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                    obj.put(column_name, rs.getDate(column_name));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                    obj.put(column_name, rs.getTimestamp(column_name));
                }
                else{
                    obj.put(column_name, rs.getObject(column_name));
                }
            }

            json.put(obj);
        }

        return json;
    }
}

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.keeper.antonnazareth.example.com", ownerName = "backend.keeper.antonnazareth.example.com", packagePath = ""))
public class MyEndpoint {



    private String resetDb() {
        String result = "Aint nothing wrong!";
        return result;
    }

    private String geturl() {
        String url = null;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://keeper-1337:test" +
                        "/testDb?user=root";

            } else {
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql:///2001:4860:4864:1:b80f:1744:757:dd24:3306" +
                        "/testDb?user=root";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private Connection getConn() {
        Connection conn = null;
        try{
            String url = geturl();
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
        }
        return conn;
    }

    private String runStmt(Connection conn, PreparedStatement ps) {
        ResultSet rs = null;
        String response = "";
        int updateCount;
        try {
            boolean returnsRs = ps.execute();
            if (returnsRs) {
                rs = ps.getResultSet();
                while(rs.next()){
                    int val = rs.getInt(1);
                    response = "Result 1: " + Integer.toString(val) + ", ";
                }
            } else {
                updateCount = ps.getUpdateCount();
                response = "Update Count: " + Integer.toString(updateCount);
            }
        } catch (SQLException e) {
            response = e.getLocalizedMessage() + ", ";
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e) {
                response = response +
                    "Rs: " + e.getLocalizedMessage() + ", "; }
            if (ps != null) try { ps.close(); } catch (Exception e) { response
                    = response +
                    "Stmt: " + e.getLocalizedMessage() + ", "; }
            if (conn != null) try { conn.close(); } catch (Exception e) {
                response = response +
                    "Conn: " + e.getLocalizedMessage(); }
        }
        return response;
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        return addUser(name);
    }


    @ApiMethod(name = "addUser")
    public MyBean addUser(@Named("name") String name) {
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO users (name) VALUES ( ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, name);

            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "countUsers")
    public MyBean countUsers() {
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM users;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "buildUsersTable")
    public MyBean buildUsersTable() {
        MyBean bean = new MyBean();

        String response = "";
        String stmt;

        try {
            Connection conn;
            PreparedStatement ps;

            conn = getConn();
            stmt = "DROP TABLE IF EXISTS users ";
            ps = conn.prepareStatement(stmt);
            response = response + runStmt(conn, ps);

            conn = getConn();
            stmt = "CREATE TABLE users ( "
                    + "id INT  AUTO_INCREMENT NOT NULL,"
                    + "name TEXT NOT NULL,"
                    + "PRIMARY KEY(id)) ENGINE=INNODB;";
            ps = conn.prepareStatement(stmt);
            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "addTeam")
         public MyBean addTeam(@Named("name") String name) {
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO teams (name) VALUES ( ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, name);

            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "countTeams")
    public MyBean countTeams() {
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM teams;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "buildTeamsTable")
    public MyBean buildTeamsTable() {
        MyBean bean = new MyBean();

        String response = "";
        String stmt;

        try {
            Connection conn;
            PreparedStatement ps;

            conn = getConn();
            stmt = "DROP TABLE IF EXISTS teams ";
            ps = conn.prepareStatement(stmt);
            response = response + runStmt(conn, ps);

            conn = getConn();
            stmt = "CREATE TABLE teams ( "
                    + "id INT  AUTO_INCREMENT NOT NULL,"
                    + "name TEXT NOT NULL,"
                    + "PRIMARY KEY(id)) ENGINE=INNODB;";
            ps = conn.prepareStatement(stmt);
            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "addTeamUser")
    public MyBean addTeamUser(@Named("teamid") String teamid, @Named("userid")
    String userid) {
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO teamusers (teamid, userid) VALUES (" +
                    " ?, ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, teamid);
            ps.setString(2, userid);

            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "countTeamUsers")
    public MyBean countTeamUsers() {
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM teamusers;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "buildTeamUsersTable")
    public MyBean buildTeamUsersTable() {
        MyBean bean = new MyBean();

        String response = "";
        String stmt;

        try {
            Connection conn;
            PreparedStatement ps;

            conn = getConn();
            stmt = "DROP TABLE IF EXISTS teamusers ";
            ps = conn.prepareStatement(stmt);
            response = response + runStmt(conn, ps);

            conn = getConn();
            stmt = "CREATE TABLE teamusers ( " +
                    "teamid INT NOT NULL," +
                    "userid INT NOT NULL," +
                    "PRIMARY KEY(teamid, userid)," +
                    "INDEX (teamid)," +
                    "INDEX (userid)," +
                    "FOREIGN KEY (teamid) REFERENCES teams(id)," +
                    "FOREIGN KEY (userid) REFERENCES users(id)) ENGINE=INNODB;";
            ps = conn.prepareStatement(stmt);
            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }

    @ApiMethod(name = "findError")
    public MyBean findError() {
        MyBean bean = new MyBean();

        String response = "";
        String stmt;

        try {
            Connection conn;
            PreparedStatement ps;

            conn = getConn();
            stmt = "SHOW ENGINE INNODB STATUS";
            ps = conn.prepareStatement(stmt);
            response = response + runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.setData(response);
        return bean;
    }
}