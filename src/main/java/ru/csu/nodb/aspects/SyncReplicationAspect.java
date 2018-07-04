package ru.csu.nodb.aspects;

import com.google.gson.*;
import org.apache.http.client.methods.HttpPost;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.csu.nodb.http.*;
import ru.csu.nodb.prop.PropertiesStorage;

import java.util.Date;

@Aspect
public class SyncReplicationAspect {

    @Pointcut("execution(* ru.csu.nodb.data.DataStorageImpl.write (..))")
    public void defineEntryPoint() {
    }

    @Before("defineEntryPoint()")
    public void log() {
        logger.info("Writing new object into storage " + new Date());
    }

    @Around("defineEntryPoint()")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        syncValue = pjp.getArgs()[0].toString();
        return pjp.proceed(new Object[] {syncValue});
    }

    @After("defineEntryPoint()")
    public void sync() {

        PropertiesStorage storage = PropertiesStorage.getInstance();

        if (storage.isMaster()) {
            logger.info("Syncing new record with slaves " + syncValue + new Date());

            HttpClientSender httpSender;

            if(storage.isAsyncMode()) {
                httpSender = new HttpClientSenderImplAsync();
            } else {
                httpSender = new HttpClientSenderImpl();
            }

            HttpPostGenerator httpPostGenerator = new HttpPostGeneratorImpl();
            HttpPost[] generatedPosts =
                    httpPostGenerator.generate(storage.getFirstSlaveUrl(), storage.getSecondSlaveUrl());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value", syncValue);

            for (HttpPost httpPost: generatedPosts) {
                try {
                    httpSender.sendPost(httpPost, jsonObject.toString());
                } catch (Exception e) {
                    logger.error("Data " + syncValue + " not synced!!!");
                    break;
                }
            }
        }
    }

    private Logger logger = LoggerFactory.getLogger(SyncReplicationAspect.class);
    private String syncValue;
}
