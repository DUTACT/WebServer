package com.dutact.web.data.repository.auth;

import com.dutact.web.service.auth.exception.OtpException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for generating OTP.
 */
@Service
public class OtpRepository {
    private LoadingCache<String, Long> otpCache;
    private Random random = new Random();
    private ConcurrentHashMap<String, Long> expiryTimes;

    /**
     * Constructor configuration.
     */
    public OtpRepository() {
        super();
        expiryTimes = new ConcurrentHashMap<>();
        otpCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, Long>() {
                    @Override
                    public Long load(String s) throws Exception {
                        return 0L;
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public Long generateOTP(String key, Long expireMs) {
        Long OTP = 100000L + random.nextLong(900000);
        otpCache.put(key, OTP);
        expiryTimes.put(key, System.currentTimeMillis() + expireMs);

        return OTP;
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public Long getOPTByKey(String key) throws OtpException {
        Long expiryTime = expiryTimes.get(key);
        if (System.currentTimeMillis() > expiryTime) {
            clearOTPFromCache(key);
            throw new OtpException();
        }
        return otpCache.getUnchecked(key);
    }

    /**
     * Method for removing key from cache.
     *
     * @param key - target key
     */
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
        expiryTimes.remove(key);
    }
}