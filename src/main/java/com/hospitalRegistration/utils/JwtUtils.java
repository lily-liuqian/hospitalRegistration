package com.hospitalRegistration.utils;

import com.hospitalRegistration.config.JwtProperties;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 生成Token
     */
    public String generateToken(Long userId, Integer roleId) {
        // 计算过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + jwtProperties.getExpiration());

        // 构建JWT
        return Jwts.builder()
                // 设置主题(用户ID)
                .setSubject(userId.toString())
                // 设置角色ID
                .claim("roleId", roleId)
                // 设置签发时间
                .setIssuedAt(new Date())
                // 设置过期时间
                .setExpiration(expirationDate)
                // 使用HS256算法签名，密钥从配置文件获取
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                // 压缩
                .compact();
    }

    /**
     * 从Token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 从Token中获取角色ID
     */
    public Integer getRoleIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roleId", Integer.class);
    }

    /**
     * 验证Token是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            // 各种异常都表示Token无效
            return false;
        }
    }
}
