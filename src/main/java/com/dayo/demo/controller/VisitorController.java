package com.dayo.demo.controller;


import com.dayo.demo.Service.VisitorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VisitorController {


    @Autowired
    private VisitorService geoLocationService;

    @GetMapping("/api")
    public String sayHello(){

        return "SayHello";
    }

    @GetMapping("/api/hello")
    public Map<String, String> greetVisitor(@RequestParam String visitor_name, HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();
        String location = getLocationFromIp(clientIp);

        Map<String, String> response = new HashMap<>();
        response.put("client_ip", clientIp);
        response.put("location", location);
        response.put("greeting", "Hello, " + visitor_name + "!" + location);

        return response;
    }

    private String getLocationFromIp(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            return geoLocationService.getCity(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

}

