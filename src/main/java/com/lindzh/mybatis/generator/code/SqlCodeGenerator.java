package com.lindzh.mybatis.generator.code;

import com.lindzh.mybatis.generator.bean.MybatisPojo;
import com.lindzh.mybatis.generator.freemarker.FreemarkerService;

/**
 * Created by Administrator on 2017/4/8.
 */
public class SqlCodeGenerator extends AbstractGenerator {
    private static final String SQL_TEMPLATE = "sql_press";

    public SqlCodeGenerator(FreemarkerService freemarkerService, MybatisPojo mybatisPojo) {
        super(freemarkerService, mybatisPojo);
    }

    @Override
    public String parse() {
        return freemarkerService.merge(SQL_TEMPLATE, params);
    }
}
