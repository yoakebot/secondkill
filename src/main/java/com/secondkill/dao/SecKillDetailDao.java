package com.secondkill.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecKillDetailDao {
    int insert(@Param("secKillId") int secKillId, @Param("userPhone") String userPhone, @Param("status") int status);

}
