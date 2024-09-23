package com.dutact.web.auth.otp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Class for generating OTP.
 */
@Service
public class OtpGenerator {


    private final Integer EXPIRE_MS;
    private LoadingCache<String, Integer> otpCache;

    /**
     * Constructor configuration.
     */
    public OtpGenerator(@Value("${auth.otp.lifespan}") Integer expireMs)
    {
        super();
        EXPIRE_MS = expireMs;
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MS, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public Integer generateOTP(String key)
    {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);

        return OTP;
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public Integer getOPTByKey(String key)
    {
        return otpCache.getIfPresent(key);
    }

    /**
     * Method for removing key from cache.
     *
     * @param key - target key
     */
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }
}