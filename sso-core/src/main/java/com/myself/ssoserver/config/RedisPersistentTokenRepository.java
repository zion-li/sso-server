package com.myself.ssoserver.config;

import com.google.common.collect.Sets;
import com.myself.ssoserver.properties.SecurityConstants;
import com.myself.ssoserver.properties.SecurityProperties;
import com.myself.ssoserver.support.RedisPersistentRememberMeToken;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 记住我实现
 *
 * @author Created by zion
 * @Date 2019/3/25.
 */
@Component
public class RedisPersistentTokenRepository implements PersistentTokenRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SecurityProperties securityProperties;

    public RedisPersistentTokenRepository() {
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        RedisPersistentRememberMeToken serToken = new RedisPersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
        redisTemplate.opsForValue().set(blockKey(serToken.getSeries()), serToken, securityProperties.getBrowser().getRememberMeSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = getTokenForSeries(series);
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), series, tokenValue, new Date());
        this.createNewToken(newToken);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        RedisPersistentRememberMeToken token = (RedisPersistentRememberMeToken) redisTemplate.opsForValue().get(blockKey(seriesId));
        if (token == null) {
            return null;
        }
        return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    //TODO 改为SCAN方法
    @Override
    public synchronized void removeUserTokens(String username) {
        Set<String> delKeys = Sets.newHashSet();
        Set<String> rememberMeTokenSet = redisTemplate.keys(SecurityConstants.DEFAULT_REMEMBER_ME_KEY + "*");
        if (CollectionUtils.isEmpty(rememberMeTokenSet)) {
            return;
        }
        List<RedisPersistentRememberMeToken> rememberMeTokens = redisTemplate.opsForValue().multiGet(rememberMeTokenSet);
        if (CollectionUtils.isEmpty(rememberMeTokens)) {
            return;
        }
        for (RedisPersistentRememberMeToken e : rememberMeTokens) {
            if (username.equals(e.getUsername())) {
                delKeys.add(blockKey(e.getSeries()));
            }
        }
        if (CollectionUtils.isNotEmpty(delKeys)) {
            redisTemplate.delete(delKeys);
        }
    }

    private String blockKey(String seriesId) {
        return SecurityConstants.DEFAULT_REMEMBER_ME_KEY + seriesId;
    }
}
