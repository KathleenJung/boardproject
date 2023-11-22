package com.example.board.controller;

import com.example.board.dto.LocationDto;
import com.example.board.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/getWeatherInfo")
    @ResponseBody
    public String getWeatherInfo(
            @RequestParam String step1,
            @RequestParam(required = false) String step2,
            @RequestParam(required = false) String step3
    ) {
        try {
            LocationDto locationInfo = locationService.getLocationInfo(step1, step2, step3);
            StringBuilder sb = new StringBuilder();

            if (locationInfo != null) {
                String weatherInfo = locationService.callShortWeatherAPI(locationInfo.getX(), locationInfo.getY());
                String weatherMidStatus = locationService.callMidStatusWeatherAPI(locationInfo.getMid_land_code());
                String weatherMidTem = locationService.callMidTemWeatherAPI(locationInfo.getMid_temperature_code());

                sb.append(step1).append(" ").append(step2).append(" ").append(step3).append("의 날씨입니다.\n");
                sb.append("단기 예보 : ").append(weatherInfo).append("\n");
                sb.append("중기 예보 : ").append(locationService.parseMidWeatherInfo(weatherMidTem, weatherMidStatus).toString()).append("\n");
                return sb.toString();
            } else {
                return "Location not found.";
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}
