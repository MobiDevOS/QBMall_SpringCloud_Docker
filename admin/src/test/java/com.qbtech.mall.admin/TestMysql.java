package com.qbtech.mall.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = AdminApplication.class) /*指定Springboot启动类启动*/
public class TestMysql {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }



    //如果连接成功会有这样的提示
 /*
   {
        CreateTime:"2019-06-12 16:16:31",
                ActiveCount:0,
            PoolingCount:5,
            CreateCount:5,
            DestroyCount:0,
            CloseCount:0,
            ConnectCount:0,
            Connections:[
        {ID:508269970, ConnectTime:"2019-06-12 16:16:32", UseCount:0, LastActiveTime:"2019-06-12 16:16:32"},
        {ID:1133143463, ConnectTime:"2019-06-12 16:16:32", UseCount:0, LastActiveTime:"2019-06-12 16:16:32"},
        {ID:88328327, ConnectTime:"2019-06-12 16:16:32", UseCount:0, LastActiveTime:"2019-06-12 16:16:32"},
        {ID:1978943199, ConnectTime:"2019-06-12 16:16:32", UseCount:0, LastActiveTime:"2019-06-12 16:16:32"},
        {ID:928103158, ConnectTime:"2019-06-12 16:16:32", UseCount:0, LastActiveTime:"2019-06-12 16:16:32"}
	]
    }*/
}