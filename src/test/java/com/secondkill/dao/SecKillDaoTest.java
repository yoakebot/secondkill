package com.secondkill.dao;

import com.secondkill.entity.SecKill;
import com.secondkill.enums.SecKillStatusEnum;
import com.secondkill.redis.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@SpringBootTest
class SecKillDaoTest {

    @Autowired
    private SecKillDao secKillDao;

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private SecKillDetailDao secKillDetailDao;

    @Test
    void get() {
        SecKill secKill = secKillDao.get(1001);
        System.out.println(secKill);
    }

    @Test
    void getAll() {
        List<SecKill> list = secKillDao.getAll(0, 5);
        for (SecKill secKill : list) {
            System.out.println(secKill);
        }
    }

    @Test
    void subStock() {
        Date now = new Date();
        int i = secKillDao.subStock(1002, now);
        System.out.println(i);
    }

    @Test
    void buy() {
        Date now = new Date();
        int stock = secKillDao.subStock(1002, now);
        if (stock == 0) {
            secKillDetailDao.insert(1002, "14300009990", SecKillStatusEnum.STOCK_LACKING.getStatus());
            System.out.println(SecKillStatusEnum.STOCK_LACKING.getInfo());
        } else {
            try {
                secKillDetailDao.insert(1002, "14300009990", SecKillStatusEnum.SUCCESS.getStatus());
                System.out.println(SecKillStatusEnum.SUCCESS.getInfo());
            } catch (Exception e) {
                secKillDetailDao.insert(1002, "14300009990", SecKillStatusEnum.REPEAT.getStatus());
                System.out.println(SecKillStatusEnum.REPEAT.getInfo());
            }
        }
    }

    @Test
    public void redisSet() {
        SecKill sk = new SecKill();
        sk.setStock(1);
        sk.setStartTime(new Date());
        sk.setSecKillId(213123);
        sk.setName("ni hao");
        redisUtils.setCache("123123", sk, 2);
    }

    @Test
    public void redisGet() {
        List<SecKill> list = (List<SecKill>) redisUtils.getCache("secKillList");
        System.out.println(list);
        if (list == null) {
            list = secKillDao.getAll(0, 5);
            redisUtils.setCache("secKillList", list, 60);
        }
        System.out.println(list);
        for (SecKill s : list) {
            System.out.println(s);
        }
    }
}