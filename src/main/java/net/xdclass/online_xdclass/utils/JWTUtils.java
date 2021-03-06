package net.xdclass.online_xdclass.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.xdclass.online_xdclass.model.entity.User;

import java.util.Date;

/**
 * JWT工具类
 */
public class JWTUtils {
    /**
     * 过期时间，一周
     */
    private static final long EXPIRE = 6000*60*24*7;
    /**
     * 加密秘钥
     */
    private static final String SECRET = "xdclass.net";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_FREFIX = "xdclass";
    /**
     * subject
     */
    private static final String SUBJECT = "xdclass";

    /**
     * 根据用户信息，生成令牌
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user){
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getHeadImg())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();

        token = TOKEN_FREFIX + token;
        return token;
    }

    /**
     * 效验token的方法
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_FREFIX, "")).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }

    }

}
