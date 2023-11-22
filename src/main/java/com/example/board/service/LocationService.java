package com.example.board.service;

import com.example.board.domain.LocationCode;
import com.example.board.dto.LocationDto;
import com.example.board.dto.MidWeatherDto;
import com.example.board.repository.LocationCodeRepository;
import com.example.board.util.HttpConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class LocationService {
    @Value("${service.key}")
    private String key;

    @Autowired
    private LocationCodeRepository locationCodeRepository;

    public static String getTimeSlot() {
        LocalDateTime now = LocalDateTime.now();
        String currentTime = now.format(DateTimeFormatter.ofPattern("HHmm"));

        if (currentTime.compareTo("0210") >= 0 && currentTime.compareTo("0510") <= 0) {
            return "0200";
        } else if (currentTime.compareTo("0810") <= 0) {
            return "0500";
        } else if (currentTime.compareTo("1110") <= 0) {
            return "0800";
        } else if (currentTime.compareTo("1410") <= 0) {
            return "1100";
        } else if (currentTime.compareTo("1710") <= 0) {
            return "1400";
        } else if (currentTime.compareTo("2010") <= 0) {
            return "1700";
        } else if (currentTime.compareTo("2310") <= 0) {
            return "2000";
        } else {
            return "2300";
        }
    }

    public LocationDto getLocationInfo(String city, String district, String neighborhood) {
        LocationCode locationCode = locationCodeRepository.findByStep1AndStep2AndStep3(city, district, neighborhood);

        if (locationCode != null) {
            return new LocationDto(locationCode.getX(), locationCode.getY(), locationCode.getMid_land_code(), locationCode.getMid_Temperature_code());
        } else {
            return null;
        }
    }

    public String callShortWeatherAPI(int x, int y) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (localDateTime.getHour() < 2 || (localDateTime.getHour() == 2 && localDateTime.getMinute() < 10)) {
            localDateTime = localDateTime.minusDays(1);
        }

        String today = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return HttpConnector.sendHttpRequest("GET", "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?" +
                "serviceKey=" + key +
                "&pageNo=1&numOfRows=1000&dataType=JSON&base_date=" + today + "&base_time=" + getTimeSlot() + "&nx=" + x + "&ny=" + y, null);
    }

    public String callMidWeatherAPI(String regId, String endpoint) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();

        String today = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String time;

        if (localDateTime.getHour() < 5 || (localDateTime.getHour() == 6 && localDateTime.getMinute() < 10)) {
            // 마지막 예보 발표는 어제 18시
            today = localDateTime.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            time = "1800";
        } else {
            // 당일 06:10 ~ 18:10에는 6시 예보, 18:10 이후에는 18시 예보
            if (localDateTime.getHour() >= 6 && (localDateTime.getHour() <= 18 && localDateTime.getMinute() < 10)) {
                time = "0600";
            } else {
                time = "1800";
            }
        }

        return HttpConnector.sendHttpRequest("GET", "https://apis.data.go.kr/1360000/MidFcstInfoService/" + endpoint +
                "?serviceKey=" + key +
                "&pageNo=1&numOfRows=10&dataType=JSON&regId=" + regId + "&tmFc=" + today + time, null);
    }

    public String callMidTemWeatherAPI(String regId) throws IOException, URISyntaxException {
        return callMidWeatherAPI(regId, "getMidTa");
    }

    public String callMidStatusWeatherAPI(String regId) throws IOException, URISyntaxException {
        return callMidWeatherAPI(regId, "getMidLandFcst");
    }

    public Map<Integer, MidWeatherDto> parseMidWeatherInfo(String jsonData1, String jsonData2) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode1 = objectMapper.readTree(jsonData1); // 기온 정보
        JsonNode jsonNode2 = objectMapper.readTree(jsonData2); // 강수량 및 날씨 예보 정보

        Map<Integer, MidWeatherDto> midWeatherDtoMap = new HashMap<>();

        JsonNode itemNode1 = jsonNode1.path("response").path("body").path("items").path("item").get(0);
        mergeDataToMap(itemNode1, midWeatherDtoMap);

        JsonNode itemNode2 = jsonNode2.path("response").path("body").path("items").path("item").get(0);
        mergeDataToMap(itemNode2, midWeatherDtoMap);

        return midWeatherDtoMap;
    }

    private void mergeDataToMap(JsonNode itemNode, Map<Integer, MidWeatherDto> midWeatherDtoMap) {
        Iterator<String> fieldNames = itemNode.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            if (fieldName.matches(".*\\d+.*")) {
                int dayValue = extractDayValue(fieldName);

                MidWeatherDto midWeatherDto = midWeatherDtoMap.computeIfAbsent(dayValue, k -> new MidWeatherDto());

                if (fieldName.startsWith("rnSt")) {
                    if (fieldName.endsWith("Am")) {
                        midWeatherDto.setRnStAm(itemNode.path(fieldName).asInt());
                    } else if (fieldName.endsWith("Pm")) {
                        midWeatherDto.setRnStPm(itemNode.path(fieldName).asInt());
                    } else {
                        // 8~10일의 경우에는 데이터에 Am, Pm이 붙지 않아서 오전, 오후 모두 같은 값으로 넣어줌
                        midWeatherDto.setRnStAm(itemNode.path(fieldName).asInt());
                        midWeatherDto.setRnStPm(itemNode.path(fieldName).asInt());
                    }
                } else if (fieldName.startsWith("wf")) {
                    if (fieldName.endsWith("Am")) {
                        midWeatherDto.setWfAm(itemNode.path(fieldName).asText());
                    } else if (fieldName.endsWith("Pm")) {
                        midWeatherDto.setWfPm(itemNode.path(fieldName).asText());
                    } else {
                        // 8~10일의 경우에는 데이터에 Am, Pm이 붙지 않아서 오전, 오후 모두 같은 값으로 넣어줌
                        midWeatherDto.setWfAm(itemNode.path(fieldName).asText());
                        midWeatherDto.setWfPm(itemNode.path(fieldName).asText());
                    }
                } else if (fieldName.matches("taMin\\d+")) {
                    midWeatherDto.setMin(itemNode.path(fieldName).asInt());
                } else if (fieldName.matches("taMax\\d+")) {
                    midWeatherDto.setMax(itemNode.path(fieldName).asInt());
                }
            }
        }
    }

    private int extractDayValue(String fieldName) {
        // 숫자 빼고 다 지워줌
        String numericalPart = fieldName.replaceAll("\\D", "");
        return Integer.parseInt(numericalPart);
    }
}
