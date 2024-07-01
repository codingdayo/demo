package com.dayo.demo.Service;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

@Service
public class VisitorService {

        private DatabaseReader dbReader;

    @PostConstruct
    public void init() throws IOException {
        InputStream database = getClass().getClassLoader().getResourceAsStream("GeoLite2-City.mmdb");
        if (database == null) {
            throw new IOException("GeoLite2-City.mmdb database file not found");
        }
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public String getCity(InetAddress ipAddress) {
        if (ipAddress.isLoopbackAddress()) {
            return "Localhost";
        }

        try {
            CityResponse response = dbReader.city(ipAddress);
            return response.getCity().getName();
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
    }

