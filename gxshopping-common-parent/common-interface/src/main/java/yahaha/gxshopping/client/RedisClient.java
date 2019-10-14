package yahaha.gxshopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yahaha.gxshopping.client.impl.RedisClientImpl;

@FeignClient(value = "GXSHOPPING-COMMON", fallback = RedisClientImpl.class)
public interface RedisClient {

    /**
     * 获取缓存数据
     * @param key
     * @return
     */
    @GetMapping("/redis")
    String get(@RequestParam("key") String key);

    /**
     * 设置缓存数据
     * @param key
     * @param value
     */
    @PostMapping("/redis")
    void set(@RequestParam("key") String key,@RequestParam("value") String value);
}
