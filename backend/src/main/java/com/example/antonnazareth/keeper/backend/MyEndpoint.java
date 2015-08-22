/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.antonnazareth.keeper.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.sql.*;
import com.google.appengine.api.utils.SystemProperty;

import java.util.logging.Logger;

import javax.inject.Named;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.keeper.antonnazareth.example.com",
                ownerName = "backend.keeper.antonnazareth.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    private static final Logger logger = Logger.getLogger(MyEndpoint.class.getName());

    private String geturl() {
        logger.info("Calling getUrl method");
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
        logger.info("Calling getConn method");
        Connection conn = null;
        try{
            String url = geturl();
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
        }
        return conn;
    }

    private MyBean runStmt(Connection conn, PreparedStatement ps) {
        logger.info("Calling runStmt method");
        MyBean bean = new MyBean();

        ResultSet rs = null;
        String response = "";
        int updateCount;
        try {
            boolean returnsRs = ps.execute();
            if (returnsRs) {

                ResultSetConverter converter = new ResultSetConverter();

                rs = ps.getResultSet();
                bean.setResultSetData(rs);
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
        bean.setStringData(response);
        return bean;
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
        logger.info("Calling addUser method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO users (name) VALUES ( ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, name);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "countUsers")
    public MyBean countUsers() {
        logger.info("Calling countUsers method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM users;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "buildUsersTable")
    public MyBean buildUsersTable() {
        logger.info("Calling buildUsersTable method");
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
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "addTeam")
    public MyBean addTeam(@Named("name") String name) {
        logger.info("Calling addTeam method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO teams (name) VALUES ( ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, name);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "countTeams")
    public MyBean countTeams() {
        logger.info("Calling countTeams method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM teams;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "buildTeamsTable")
    public MyBean buildTeamsTable() {
        logger.info("Calling buildTeamsTable method");
        MyBean bean = null;
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
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "addTeamUser")
    public MyBean addTeamUser(@Named("teamid") String teamid, @Named("userid")
    String userid) {
        logger.info("Calling addTeamUser method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO teamusers (teamid, userid) VALUES (" +
                    " ?, ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, teamid);
            ps.setString(2, userid);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "countTeamUsers")
    public MyBean countTeamUsers() {
        logger.info("Calling countTeamUsers method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM teamusers;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "buildTeamUsersTable")
    public MyBean buildTeamUsersTable() {
        logger.info("Calling buildTeamUsersTable method");
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
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }


    @ApiMethod(name = "getTeamScores")
    public MyBean getTeamScores(@Named("teamname") String teamname) {
        logger.info("Calling getTeamScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT s.* FROM scores s " +
                    "INNER JOIN teams t ON s.teamid = t.id " +
                    "WHERE t.name = ?;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, teamname);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }
}