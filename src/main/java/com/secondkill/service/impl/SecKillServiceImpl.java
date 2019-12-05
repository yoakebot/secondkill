package com.secondkill.service.impl;

import com.secondkill.dao.SecKillDao;
import com.secondkill.dao.SecKillDetailDao;
import com.secondkill.dto.Exposed;
import com.secondkill.entity.SecKill;
import com.secondkill.enums.SecKillStatusEnum;
import com.secondkill.exceptions.IllegalSecKillException;
import com.secondkill.exceptions.RepeatSecKillException;
import com.secondkill.exceptions.StockLackException;
import com.secondkill.redis.RedisUtils;
import com.secondkill.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service("secKillService")
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SecKillDetailDao secKillDetailDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<SecKill> getAll() {
        List<SecKill> list = (List<SecKill>) redisUtils.getCache("secKillList");
        if (list == null) {
            list = secKillDao.getAll(0, 5);
            redisUtils.setCache("secKillList", list, 60);
        }
        return list;
    }

    @Override
    public SecKill get(int secKillId) {
        SecKill secKill = (SecKill) redisUtils.getCache(secKillId + "");
        if (secKill == null) {
            secKill = secKillDao.get(secKillId);
            redisUtils.setCache(secKillId + "", secKill, 30);
        }
        return secKill;
    }

    /**
     * 暴露秒杀地址接口
     *
     * @param secKillId 商品id
     * @return 秒杀接口dto
     */
    @Override
    public Exposed exposed(int secKillId) {
        Exposed exposed = new Exposed();
        SecKill secKill = (SecKill) redisUtils.getCache(secKillId + "");
        if (secKill == null) {
            secKill = secKillDao.get(secKillId);
            redisUtils.setCache(secKillId + "", secKill, 30);
        }
        Date now = new Date();
        Date start = secKill.getStartTime();
        Date end = secKill.getEndTime();
        exposed.setNowTime(now);
        exposed.setStartTime(start);
        exposed.setEndTime(end);
        if (now.getTime() >= start.getTime() && now.getTime() <= end.getTime()) {
            //开始了
            String md5 = getMd5(secKillId);
            exposed.setMd5(md5);
            exposed.setFlag(true);
            return exposed;
        }
        //未开始
        exposed.setFlag(false);
        return exposed;
    }

    /**
     * 执行秒杀程序
     *
     * @param secKillId 商品id
     * @param userPhone 用户手机号
     * @param md5       秒杀接口md5
     * @throws SecurityException 秒杀失败结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void execute(int secKillId, String userPhone, String md5) throws SecurityException {
        String verifyMd5 = getMd5(secKillId);
        if (verifyMd5.equals(md5)) {
            //验证通过 开始秒杀
            int result;
            try {
                result = secKillDao.subStock(secKillId, new Date());
            } catch (Exception e) {
                //系统异常
                throw new SecurityException("异常");
            }
            if (result <= 0) {
                //  库存不足或秒杀时间已过
                throw new StockLackException(SecKillStatusEnum.STOCK_LACKING.getInfo());
            } else {
                try {
                    secKillDetailDao.insert(secKillId, userPhone, SecKillStatusEnum.SUCCESS.getStatus());
                } catch (Exception e) {
                    //重复秒杀
                    throw new RepeatSecKillException(SecKillStatusEnum.REPEAT.getInfo());
                }
            }
        } else {
            //md5非法
            throw new IllegalSecKillException("非法操作");
        }
    }

    private String getMd5(int secKillId) {
        final String encrypted = "fdf37894789(&*(&3894*(^*(3jkl";
        try {
            String str = secKillId + "/" + encrypted;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
