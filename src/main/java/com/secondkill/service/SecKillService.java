package com.secondkill.service;

import com.secondkill.dto.Exposed;
import com.secondkill.entity.SecKill;

import java.util.List;

public interface SecKillService {
    List<SecKill> getAll();

    SecKill get(int secKillId);

    Exposed exposed(int secKillId);

    void execute(int secKillId, String userPhone,String md5) throws SecurityException;

}
