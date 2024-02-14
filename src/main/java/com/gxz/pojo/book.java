package com.gxz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("books")//映射mongodb的文档（表）
public class book {
    @Id //映射_id值
    private String id;
    @Field()//映射key:value对
    private String title;
    @Field
    private String type;
    @Field
    private String tag;
    @Field("favCount")//指定数据库的名字
    private Integer count;
    @Field
    private String author;
}
