package com.valko.actividad4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDemo {
    @Autowired
    private ConnectionDemo connectionDemo;

    public void performQuery(){
        connectionDemo.connectAndQuery();
    }
}
