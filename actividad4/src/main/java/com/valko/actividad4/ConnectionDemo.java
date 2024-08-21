package com.valko.actividad4;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.*;

@Configuration
@ConfigurationProperties(prefix = "spring.r2dbc")
public class ConnectionDemo {
    private String url;

    private String username;

    private String password;

    private String query = "SELECT titulo FROM demo";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    public void connectAndQuery(){
        try(Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSets = statement.executeQuery("SELECT titulo FROM demo")){
                while (resultSets.next()) {
                    String titulo = resultSets.getString("titulo");
                    System.out.printf("Título: %s%n", titulo);
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
