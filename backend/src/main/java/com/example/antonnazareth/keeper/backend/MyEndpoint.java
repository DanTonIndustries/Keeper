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

    private Connection getConn()
            throws SQLException {
        logger.info("Calling getConn method");
        Connection conn = null;
        String url = geturl();
        conn = DriverManager.getConnection(url);
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
    @ApiMethod(name = "addUser", path="add/user")
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

    @ApiMethod(name = "addTeam", path="add/team")
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

    @ApiMethod(name = "addTeamUser", path="add/teamUser")
    public MyBean addTeamUser(@Named("teamid") int teamid,
                              @Named("userid") int userid) {
        logger.info("Calling addTeamUser method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO teamusers (teamid, userid) VALUES (" +
                    " ?, ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, teamid);
            ps.setInt(2, userid);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "addGame", path="add/game")
    public MyBean addGame(@Named("name") String name) {
        logger.info("Calling addGame method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO games (name) VALUES ( ? )";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, name);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "addMatch", path="add/match")
    public MyBean addMatch(@Named("gameid") int gameid) {
        logger.info("Calling addMatch method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO matches (gameid) VALUES ( ? );";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, gameid);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "addScore", path="add/score")
    public MyBean addScore(@Named("matchid") int matchid,
                           @Named("teamid") int teamid,
                           @Named("score") int score,
                           @Named("winpts") int winpts) {
        logger.info("Calling addScore method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "INSERT INTO scores (matchid, teamid, score) VALUES" +
                    " ( ?, ?, ? );";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, matchid);
            ps.setInt(2, teamid);
            ps.setInt(3, score);
            ps.setInt(4, winpts);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "countUsers", path="count/user")
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

    @ApiMethod(name = "countTeams", path="count/team")
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

    @ApiMethod(name = "countTeamUsers", path="count/teamUser")
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

    @ApiMethod(name = "countScores", path="count/score")
    public MyBean countScores() {
        logger.info("Calling countScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM scores;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "countMatches", path="count/match")
    public MyBean countMatches() {
        logger.info("Calling countMatches method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM matches;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "countGames", path="count/game")
    public MyBean countGames() {
        logger.info("Calling countTeamUsers method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT COUNT(*) FROM games;";
            PreparedStatement ps = conn.prepareStatement(stmt);

            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getUsers", path="query/user")
    public MyBean getUsers() {
        logger.info("Calling getUsers method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT u.* FROM users u;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getTeams", path="query/team")
    public MyBean getTeams() {
        logger.info("Calling getTeams method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT t.* FROM teams t;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getMatches", path="query/match")
    public MyBean getMatches() {
        logger.info("Calling getMatches method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT m.* FROM matches m;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getGames", path="query/game")
         public MyBean getGames() {
        logger.info("Calling getGames method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT g.* FROM games g;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getScores", path="query/score")
    public MyBean getScores() {
        logger.info("Calling getScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT s.* FROM scores s;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getTeamUsers", path="query/teamUser")
    public MyBean getTeamUsers() {
        logger.info("Calling getTeamScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT tu.* FROM teamusers tu;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getTeamUsersByTeam", path="query/teamUsersByTeam")
    public MyBean getTeamUsersByTeam(@Named("teamid") int teamid) {
        logger.info("Calling getTeamScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT u.* FROM users u " +
                    "INNER JOIN teamusers tu ON tu.userid = u.id " +
                    "WHERE tu.teamid = ?;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, teamid);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getUserTeams", path="query/userTeam")
    public MyBean getUserTeams(@Named("userid") int userid) {
        logger.info("Calling getTeamScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT t.* FROM teams t " +
                    "INNER JOIN teamusers tu ON tu.teamid = t.id " +
                    "WHERE tu.userid = ?;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, userid);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getTeamScores", path="query/teamScore")
    public MyBean getTeamScores(@Named("teamid") int teamid) {
        logger.info("Calling getTeamScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT s.* FROM scores s " +
                    "WHERE s.teamid = ?;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, teamid);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getMatchScores", path="query/matchScore")
    public MyBean getMatchScores(@Named("matchid") int matchid) {
        logger.info("Calling getMatchScores method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT s.* FROM scores s " +
                    "WHERE s.matchid = ?;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, matchid);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

    @ApiMethod(name = "getGameMatches", path="query/gameMatch")
    public MyBean getGameMatches(@Named("gameid") int gameid) {
        logger.info("Calling getGameMatches method");
        MyBean bean = new MyBean();

        String response = "";

        try {
            Connection conn = getConn();

            String stmt = "SELECT m.* FROM matches m " +
                    "WHERE m.gameid = ?;";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setInt(1, gameid);
            bean = runStmt(conn, ps);

        } catch (SQLException e){
            response = e.getLocalizedMessage() + ", ";
        }

        bean.addToStringData(response);
        return bean;
    }

}