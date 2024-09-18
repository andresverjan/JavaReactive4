package com.reactive.crud;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.*;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.r2dbc")
public class ConnectionConfig {
    private String url;

    private String username;

    private String password;

    private String query = "SELECT * FROM person";

    public void connectAndQuery(){
        try(Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSets = statement.executeQuery(query)){
            while (resultSets.next()) {
                String titulo = resultSets.getString("titulo");
                System.out.printf(titulo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}