package com.rabbiter.hrm.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Jwt工具类
 *
 * @Author : 
 * @Date : 2024/1/30
 */
@Component
public class JWTUtil {

    /**
     * 生成token
     * @param id
     * @param password
     * @return
     */
    public static String generateToken(Integer id,String password) {
        return JWT.create().withAudience(id.toString()) // 设置载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 设置签名过期的时间
                .sign(Algorithm.HMAC256(password)); // 签名 Signature
    }


}
