package com.sixfold.flightplanner.service;

import com.google.gson.Gson;
import com.sixfold.flightplanner.conf.DistanceApiProperties;
import com.sixfold.flightplanner.dto.DistanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistanceService {

    private final DistanceApiProperties distanceApiProperties;

    public Double getDistance(String from, String to) throws IOException {
        String apiResponse = getApiResponse(from, to);
        apiResponse = apiResponse.substring(apiResponse.indexOf("{"));
        apiResponse = apiResponse.substring(0, apiResponse.indexOf("}") + 1);
        Gson gson = new Gson();
        DistanceResponse distanceResponse = gson.fromJson(apiResponse, DistanceResponse.class);
        if (distanceResponse.getSuccess() == "false") {
            return Double.parseDouble(distanceApiProperties.getDistanceToMoon());
        }
        return Double.parseDouble(distanceResponse.getDistance().replaceAll(",", ""));
    }

    public String getApiResponse(String from, String to) throws IOException {
        String url = String.format(distanceApiProperties.getUrl(), from, to, distanceApiProperties.getUserKey());
        HttpURLConnection con = getConnection(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public HttpURLConnection getConnection(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        return con;
    }
}
