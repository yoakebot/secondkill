package com.secondkill.dao;

import com.secondkill.entity.SecKill;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SecKillDao {
    SecKill get(int secKillId);

    List<SecKill> getAll(int offset, int nums);

    int subStock(int secKillId, Date now);

}
