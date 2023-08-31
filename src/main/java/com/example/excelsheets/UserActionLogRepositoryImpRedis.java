package com.example.excelsheets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Repository
public class UserActionLogRepositoryImpRedis implements UserActionLogRepository{
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "USER";
    @Override
    public void addUserAction(String id, String email, String type, String action, BigInteger createdTimestamp, String message, String status) {
    }
    @Override
    public List<UserActionLogDTO> getUserActionLogs(String email, String type, String action, BigInteger start_date, BigInteger end_date, String status, Integer pagesize, Integer offset, String searchkey, String message) {
        List<UserActionLogDTO> users;
        users = redisTemplate.opsForHash().values(KEY);
        System.out.println("from cache");
        return  users;
    }
    @Override
    public Integer getTotalUserActionLogRecords() {
        return null;
    }
    @Override
    public void deleteUserActionLogRecords(Integer limit) {
    }
    @Override
    public Integer getUserActionLogsCount(String email, String type, String action, BigInteger start_date, BigInteger end_date, String status, String searchkey, String message) {
        return null;
    }

}