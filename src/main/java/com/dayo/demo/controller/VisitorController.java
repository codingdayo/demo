package com.dayo.demo.controller;


import com.dayo.demo.Service.VisitorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/api")
    public String sayHello(){

        return "SayHello";
    }

    @GetMapping("/api/hello")
    public Map<String, String> greetVisitor(@RequestParam String visitor_name, HttpServletRequest request) {


        String clientIp = request.getRemoteAddr();
        //String clientIp = "82.46.243.246";
        String location = getLocationFromIp(clientIp);
        String weather = getTemperatureForLocation(location);

        Map<String, String> response = new HashMap<>();
        response.put("client_ip", clientIp);
        response.put("location", location);
        //response.put("weather", weather);
        response.put("greeting", "Hello, " + visitor_name + "!," + " the temperature is " + weather + " in " + location);

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

    public String getTemperatureForLocation(String location) {
        //String weatherApiKey = "cc669a1f17d45d8186e972313812a2e2";

        try {
            //String url = "http://api.openweathermap.org/data/2.5/weather?q=" + "102.89.23.11" + "&appid=" + weatherApiKey + "&units=metric";
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=cc669a1f17d45d8186e972313812a2e2&units=metric";
            Map<String, Object> weatherResponse = restTemplate.getForObject(url, Map.class);
            Map<String, Object> main = (Map<String, Object>) weatherResponse.get("main");
            return main != null ? main.get("temp") + " degrees Celsius" : "N/A";
        } catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }
    }

}

