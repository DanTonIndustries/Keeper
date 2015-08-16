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
import java.sql.*;
import com.google.appengine.api.utils.SystemProperty;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.keeper.antonnazareth.example.com", ownerName = "backend.keeper.antonnazareth.example.com", packagePath = ""))
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();

        String url = null;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://keeper-1337:test" +
                        "/testDb?user=root";
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://127.0.0.1:3306/testDb?user=root";

                // Alternatively, connect to a Google Cloud SQL instance using:
                // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setData("It didn't work! " + name);
            return response;
        }

        try {
            Connection conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setData("Hi, " + name);

        return response;
    }
    /*    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }*/
}