package com.example.baselib.room.bean;

import android.app.IntentService;
import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author wsbai
 * @date 2019-06-21
 * @Entity表示实体类
 * @Entity注解包含的属性有： tableName：设置表名字。默认是类的名字。
 * indices：设置索引，Room里面可以通过@Entity的indices属性来给表格添加索引。
 * inheritSuperIndices：父类的索引是否会自动被当前类继承。
 * primaryKeys：设置主键。
 * foreignKeys：设置外键。
 */
//设置表名为user
@Entity(tableName = "user")
public class User {
    /**
     *主键，也可以通过 @Entity(primaryKeys = {"firstName","lastName"})设置主键
     * 如果你希望Room给entity设置一个自增的字段，可以设置@PrimaryKey的autoGenerate属性。
     */
    @PrimaryKey
    public int id;
    //表名
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    //Room中你就可以使用@Embedded注解来表示嵌入
    public String lastName;
    @Embedded
    public Address address;

    /**
     * 如果需要制定某个字段不作为表中的一列需要添加@Ignore注解。
     */
    @Ignore
    Bitmap picture;

    private class Address{
        public String street;
        public String state;
        public String city;

        @ColumnInfo(name = "post_code")
        public int postCode;
    }
}