package com.reactive.crud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceConfig {
    @Autowired
    private ConnectionConfig connectionConfig;

    public void performQuery(){
        connectionConfig.connectAndQuery();
    }
}