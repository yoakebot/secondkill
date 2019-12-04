package com.secondkill.dao;

import com.secondkill.entity.SecKill;
import com.secondkill.enums.SecKillStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


@SpringBootTest
class SecKillDaoTest {

    @Autowired
    private SecKillDao secKillDao;

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
}