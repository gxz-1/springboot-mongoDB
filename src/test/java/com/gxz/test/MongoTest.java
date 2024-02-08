package com.gxz.test;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class MongoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test1(){
         if(mongoTemplate.collectionExists("emp")){
             mongoTemplate.dropCollection("emp");
         }
        mongoTemplate.createCollection("emp"); //创建集合
    }

}
