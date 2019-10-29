package cn.com.syzg.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static final Logger logger= LoggerFactory.getLogger(RedisCacheConfig.class);

    //自定义一个key的生成器
    @Bean
    public KeyGenerator keyGenerator(){
        return (o, method, params) ->{
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()); // 类目
            sb.append(method.getName()); // 方法名
            for(Object param: params){
                sb.append(param.toString()); // 参数名
            }
            return sb.toString();
        };
    }

    //配置缓存管理器
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofDays(1))  //1天 缓存失效
        //设置key的序列化方式
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializable()))
        //设置value的序列化方式
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializable()))
        //不缓存null值
        .disableCachingNullValues();

        RedisCacheManager redisCacheManager= RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(configuration)
                .transactionAware()
                .build();
        logger.info("自定义RedisCacheManager加载完成");
        return redisCacheManager;
    }

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
           RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<>();
           redisTemplate.setConnectionFactory(redisConnectionFactory);
           redisTemplate.setKeySerializer(keySerializable());
           redisTemplate.setHashKeySerializer(keySerializable());
           redisTemplate.setValueSerializer(valueSerializable());
           redisTemplate.setHashValueSerializer(valueSerializable());
           logger.info("序列化完成！");
           return redisTemplate;
    }

    //key键序列化方式
    private RedisSerializer<String> keySerializable(){
        return new StringRedisSerializer();
    }

    //value值序列化方式
    private GenericJackson2JsonRedisSerializer valueSerializable(){
        return new GenericJackson2JsonRedisSerializer();
    }
}
