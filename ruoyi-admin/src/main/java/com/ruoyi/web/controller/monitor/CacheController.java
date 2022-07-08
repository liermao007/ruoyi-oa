package com.ruoyi.web.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {

    private final RedisTemplate<String, String> redisTemplate;

    private final static List<SysCache> CACHES = new ArrayList<>();

    static {
        CACHES.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        CACHES.add(new SysCache(CacheConstants.ONLINE_TOKEN_KEY, "在线用户"));
        CACHES.add(new SysCache(CacheConstants.LOGIN_ERROR, "登陆错误"));
        CACHES.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        CACHES.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        CACHES.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        CACHES.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        CACHES.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
    }

    /**
     * 获取缓存监控列表
     */
    @SaCheckPermission("monitor:cache:list")
    @GetMapping()
    public R<Map<String, Object>> getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        if (commandStats != null) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        result.put("commandStats", pieList);
        return R.ok(result);
    }

    /**
     * 获取缓存监控缓存名列表
     */
    @SaCheckPermission("monitor:cache:list")
    @GetMapping("/getNames")
    public R<List<SysCache>> cache() {
        return R.ok(CACHES);
    }

    /**
     * 获取缓存监控Key列表
     *
     * @param cacheName 缓存名
     */
    @SaCheckPermission("monitor:cache:list")
    @GetMapping("/getKeys/{cacheName}")
    public R<Set<String>> getCacheKeys(@PathVariable String cacheName) {
        Set<String> cacheKyes = redisTemplate.keys(cacheName + "*");
        return R.ok(cacheKyes);
    }

    /**
     * 获取缓存监控缓存值详情
     *
     * @param cacheName 缓存名
     * @param cacheKey  缓存key
     */
    @SaCheckPermission("monitor:cache:list")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public R<SysCache> getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return R.ok(sysCache);
    }

    /**
     * 清理缓存监控缓存名
     *
     * @param cacheName 缓存名
     */
    @SaCheckPermission("monitor:cache:list")
    @DeleteMapping("/clearCacheName/{cacheName}")
    public R<Void> clearCacheName(@PathVariable String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        redisTemplate.delete(cacheKeys);
        return R.ok();
    }

    /**
     * 清理缓存监控Key
     *
     * @param cacheKey key名
     */
    @SaCheckPermission("monitor:cache:list")
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public R<Void> clearCacheKey(@PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return R.ok();
    }

    /**
     * 清理全部缓存监控
     */
    @SaCheckPermission("monitor:cache:list")
    @DeleteMapping("/clearCacheAll")
    public R<Void> clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        redisTemplate.delete(cacheKeys);
        return R.ok();
    }

}
