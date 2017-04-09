package com.linda.common.mybatis.generator;

import com.linda.common.mybatis.generator.bean.MybatisPojo;
import com.linda.common.mybatis.generator.bean.UserInfo;
import com.linda.common.mybatis.generator.code.JavaCodeGenerator;
import com.linda.common.mybatis.generator.code.SqlCodeGenerator;
import com.linda.common.mybatis.generator.code.XmlCodeGenerator;
import com.linda.common.mybatis.generator.freemarker.FreemarkerService;
import com.linda.common.mybatis.generator.utils.MybatisPojoGenerator;

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
