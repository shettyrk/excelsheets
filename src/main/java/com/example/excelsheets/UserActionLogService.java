package com.example.excelsheets;


import com.alibaba.fastjson.JSONObject;
import com.example.excelsheets.utils.ExcelExportUtil;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserActionLogService {

    @Autowired
    UserActionLogRepositoryImpRedis userActionLogRepository;
    public ResponseEntity<byte[]> getAllProductsUsingNativeQuery(String username, String vdmsid, JSONObject filterObject, Integer pageno, Integer pagesize, String searchkey) throws ParseException {

        String email = filterObject.getString("email");
        String type = filterObject.getString("type");
        String action = filterObject.getString("action");
        String d1 = filterObject.getString("start_date");
        String d2 = filterObject.getString("end_date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = dateFormat.parse(d1);
        Date date2 = dateFormat.parse(d2);
        BigInteger start_date = BigInteger.valueOf(date1.getTime());
        BigInteger end_date = BigInteger.valueOf(date2.getTime());
        String status = filterObject.getString("status");
        Integer offset = pagesize * (pageno - 1);
        String message = "all";

        if(type.equals("Device")){
            if(action.equals("ADD")) {
                message = "is added";
            }else if(action.equals("UPDATE")){
                message = "is updated for network";
            }else if(action.equals("DELETE")){
                message = "is deleted";
            }
        }

        if(type.equals("Geolocation")) {

            if(action.equals("UPDATE")){
                type =  "Device";
                message = "Position details";
            }
        }
        System.out.println("Step 1");



        List<UserActionLogDTO> userActionLogsList = userActionLogRepository.getUserActionLogs(email, type, action, start_date, end_date, status, pagesize, offset, searchkey, message);
        System.out.println("Step 2");


        ByteArrayOutputStream byteArrayOutputStream = ExcelExportUtil.createExcel(userActionLogsList);
        System.out.println("Step 3");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "products.xlsx");
        System.out.println("Step 4");
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }

    public void addUserAction(String email, String type, String action, String message, String status) {
        String id = Generators.timeBasedGenerator().generate().toString();
        BigInteger createdTimestamp = BigInteger.valueOf(System.currentTimeMillis());

        if (email == null || email.equals("null") || email.equals("")) {
            email = "admin";
        }
        userActionLogRepository.addUserAction(id, email, type, action, createdTimestamp, message, status);
        System.out.println("added");
    }

    public List<UserActionLogDTO> getUserActionLogs(String username, String vdmsid, JSONObject filterObject, Integer pageno, Integer pagesize, String searchkey) {

        String email = filterObject.getString("email");
        String type = filterObject.getString("type");
        String action = filterObject.getString("action");
        BigInteger start_date = filterObject.getBigInteger("start_date");
        BigInteger end_date = filterObject.getBigInteger("end_date");
        String status = filterObject.getString("status");
        Integer offset = pagesize * (pageno - 1);
        String message = "all";

        if(type.equals("Device")){
            if(action.equals("ADD")) {
                message = "is added";
            }else if(action.equals("UPDATE")){
                message = "is updated for network";
            }else if(action.equals("DELETE")){
                message = "is deleted";
            }
        }

        if(type.equals("Geolocation")) {
            if(action.equals("UPDATE")){
                type =  "Device";
                message = "Position details";
            }
        }

        List<UserActionLogDTO> userActionLogDTOS = userActionLogRepository.getUserActionLogs(email, type, action, start_date, end_date, status, pagesize, offset, searchkey, message);
        return userActionLogDTOS;
    }

    public void deleteUserActionLogRecords() {

        Integer keepRecords = 25000;
        Integer limit = 25000;

        Integer newCount;

        Integer count = userActionLogRepository.getTotalUserActionLogRecords();

        while (count > keepRecords) {

            newCount = count - limit;

            if (newCount < keepRecords) {
                limit = count - keepRecords;
            }
            count = newCount;
            userActionLogRepository.deleteUserActionLogRecords(limit);
        }
    }

    public Map<String, Integer> getUserActionLogsCount(String username, String vdmsid, JSONObject filterObject, String searchkey) {
        String email = filterObject.getString("email");
        String type = filterObject.getString("type");
        String action = filterObject.getString("action");
        BigInteger start_date = filterObject.getBigInteger("start_date");
        BigInteger end_date = filterObject.getBigInteger("end_date");
        String status = filterObject.getString("status");
        String message = "all";

        if(type.equals("Device")){
            if(action.equals("ADD")) {
                message = "is added";
            }else if(action.equals("UPDATE")){
                message = "is updated for network";
            }else if(action.equals("DELETE")){
                message = "is deleted";
            }
        }

        if(type.equals("Geolocation")) {
            if(action.equals("UPDATE")){
                type =  "Device";
                message = "Position details";
            }
        }

        Map<String,Integer> logsCount = new HashMap<>();
        Integer userActionLogsCount = userActionLogRepository.getUserActionLogsCount(email, type, action, start_date, end_date, status, searchkey, message);

        if(filterObject.getString("type") != null) {
            logsCount.put(filterObject.getString("type").toLowerCase() + "_count", userActionLogsCount);
        }
        return logsCount;
    }
}
