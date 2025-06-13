package org.dante.springboot.springbootjwtserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serial;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

	@Serial
    private static final long serialVersionUID = 3350589677347231421L;
	private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // 预计算签名密钥，避免重复解码
    private transient javax.crypto.SecretKey signingKey;

    // 初始化签名密钥
    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        signingKey = new SecretKeySpec(decodedKey, Jwts.SIG.HS512.getId());
    }

    /**
     * 从指定的令牌中获取用户名
     *
     * @param token 用户令牌，用于验证用户身份和获取用户信息
     * @return 用户名，即令牌的主体部分
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 获取 Token 的签发时间。
     *
     * @param token JWT Token
     * @return 签发时间，如果解析失败或不存在则返回 null
     */
    public Date getCreatedDateFromToken(String token) {
        return getClaimFromToken(token, claims -> new Date((Long) claims.get(CLAIM_KEY_CREATED)));
    }

    /**
     * 获取 Token 的过期时间。
     *
     * @param token JWT Token
     * @return 过期时间，如果解析失败或不存在则返回 null
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 根据用户信息生成 JWT Token。
     *
     * @param userDetails 用户详细信息
     * @return JWT Token 字符串
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    /**
     * 从 Token 中获取 Claims 信息。
     * @param token JWT Token 字符串
     * @return  Claims 信息，如果解析失败或不存在则返回 null
     */
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith( signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
        	log.error("getClaimsFromToken {} error.", token, e);
            return null;
        }
    }

    /**
     * 生成 Token 的过期时间。
     *
     * @return 过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 判断 Token 是否已过期。
     *
     * @param token 要验证的 Token
     * @return 如果 Token 已过期则返回 true，否则 false
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    /**
     * 判断 Token 是否在用户最后密码重置时间之前创建。
     *
     * @param created          Token 的创建时间
     * @param lastPasswordReset 用户最后密码重置时间
     * @return 如果 Token 在用户最后密码重置时间之前创建则返回 true，否则 false
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created != null && created.before(lastPasswordReset));
    }

     /**  生成 Token。
      * @param claims 要包含在 Token 中的信息
      * @return 生成的 Token 字符串
      */
    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .expiration(generateExpirationDate())
                .signWith(signingKey)
                .compact();
    }

    /**
     * 验证 Token 是否可以刷新。
     *
     * @param token               要验证的 Token
     * @param lastPasswordReset   用户最后密码重置时间
     * @return 如果可以刷新则返回 true，否则 false
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    /**
     * 刷新 Token，更新签发时间。
     *
     * @param token 旧 Token
     * @return 新 Token，如果刷新失败则返回 null
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
        return null;
    }

    /**
     * 验证 Token 是否有效。
     *
     * @param token      要验证的 Token
     * @param userDetails 用户详细信息
     * @return 如果 Token 有效则返回 true，否则 false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (userDetails instanceof JwtUserDetails user) {
            final String username = getUsernameFromToken(token);
            final Date created = getCreatedDateFromToken(token);
            return username != null && username.equals(user.getUsername())
                    && !isTokenExpired(token)
                    && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
        }
        return false;
    }
}
