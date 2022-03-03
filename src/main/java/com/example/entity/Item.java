package com.example.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Document(collection = "item4")
public class Item {

    @Id
    private long code = 0L;

    @Field(name = "itemname")
    private String name = null;

    @Field(name = "itemprice")
    private long price = 0L;

    @Field(name = "quantity")
    private long quantity = 0L;

    // import java.util
    @Field(name = "regdate")
    private Date regdate = null;

    // byte 배열 이미지는 네개로 쪼개서 들어가야 되기 때문에
    // 수동으로 넣어줘야해서 name 값을 다르게 해줘야함
    private byte[] filedata = null;
    private String filetype = null;
    private String filename = null;
    private long filesize = 0L;

}
