package com.lindzh.mybatis.generator;

import com.lindzh.mybatis.generator.bean.MybatisPojo;
import com.lindzh.mybatis.generator.bean.UserInfo;
import com.lindzh.mybatis.generator.code.SqlCodeGenerator;
import com.lindzh.mybatis.generator.freemarker.FreemarkerService;
import com.lindzh.mybatis.generator.utils.MybatisPojoGenerator;

/**
 * Created by Administrator on 2017/4/8.
 */
public class SqlTest {

    public static void main(String[] args) {
        FreemarkerService freemarkerService = new FreemarkerService();
        freemarkerService.setLocation("");
        freemarkerService.setSuffix("ftl");
        freemarkerService.startService();

        MybatisPojo mybatis = MybatisPojoGenerator.genPojo(UserInfo.class, "aaa");

        SqlCodeGenerator sqlCodeGenerator = new SqlCodeGenerator(freemarkerService,mybatis);

        String sqlCode = sqlCodeGenerator.parse();

        System.out.println(sqlCode);
    }
}
