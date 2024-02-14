package com.gxz.test;


import com.gxz.pojo.book;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MongoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    //根据配置文件测试数据库连接
    public void test1(){
         if(mongoTemplate.collectionExists("emp")){
             mongoTemplate.dropCollection("emp");
         }
        mongoTemplate.createCollection("emp"); //创建集合
    }

    @Test
    //文档的插入
    public void test2(){
        book b = new book("0", "book-60", "sociality","nosql", 66, "xxx60");
//        mongoTemplate.save();//插入数据，id重复则覆盖
        mongoTemplate.insert(b);//插入数据，id重复则报错

        List<book> list = Arrays.asList(
                new book("1", "book-61", "technology", "popular",67, "xxx61"),
                new book("2", "book-62", "novel","document", 68, "xxx62"),
                new book("3", "book-63", "literature","mongodb", 96, "xxx63"));
        mongoTemplate.insert(list,book.class);//可以直接插入集合
        //通过Spring MongoDB还会给集合中增加_class的属性，存储Java中类的全限定路径
        //这么做为了查询时能把Document转换为Java类型
    }

    @Test
    //文档的查询
    public void test3(){
        //1.根据id查询
        book b = mongoTemplate.findById(1, book.class);
        System.out.println(b);
        //2.条件查询
        //构造条件表达式
        Query query1 = new Query(Criteria.where("favCount").gt(60));//点击数大于50
//        query1.with(Sort.by(Sort.Order.desc("favCount")));        //排序
        query1.with(Sort.by(Sort.Order.desc("favCount")))           //分页
                .skip(2) //指定跳过记录数
                .limit(4); //每页显示记录数
        Query query2 = new Query(Criteria.where("title").regex("43"));//正则查询
        //多条件
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("title").is("book-62"),Criteria.where("type").is("novel"));
        Query query3 = new Query(criteria);
        //查询
        List<book> book = mongoTemplate.find(query3, book.class);
        System.out.println("book = " + book);
    }

    @Test
    //文档的更新
    public void test4(){
        //设置更新的条件
        Query query = new Query(Criteria.where("title").regex("book-6."));
        //设置更新的属性
        Update update = new Update();
        update.set("favCount",60);
        //更新
//        UpdateResult result = mongoTemplate.updateFirst(query, update, book.class);//只更新符合条件的第一条
        UpdateResult result = mongoTemplate.updateMulti(query, update, book.class);//更新所有符合的数据
//        UpdateResult upsert = mongoTemplate.upsert(query, update, book.class);//条件不符合则插入数据
        System.out.println("更新的条数: "+result.getModifiedCount());
    }

    @Test
    //文档的删除
    public void test5(){
        //删除所有文档
//        mongoTemplate.dropCollection(book.class);
        //条件删除
//        Query query = new Query(Criteria.where("title").regex("book-6."));
//        mongoTemplate.remove(query, book.class);
    }
}
