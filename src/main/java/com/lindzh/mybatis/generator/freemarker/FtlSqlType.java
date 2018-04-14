package com.lindzh.mybatis.generator.freemarker;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public class FtlSqlType  implements TemplateMethodModel {

    @Override
    public Object exec(List args) throws TemplateModelException {
        String type = (String)args.get(0);
        boolean default0 = Boolean.valueOf((String)args.get(1));
        if(type.equalsIgnoreCase("long")){
            if(default0){
                return "bigint default 0";
            }else{
                return "bigint";
            }
        }else if(type.equalsIgnoreCase("int")){
            if(default0){
                return "int default 0";
            }else{
                return "int";
            }
        }else if(type.equalsIgnoreCase("short")){
            if(default0){
                return "int default 0";
            }else{
                return "int";
            }
        }else if(type.equalsIgnoreCase("byte")){
            if(default0){
                return "tinyint default 0";
            }else{
                return "tinyint";
            }
        }else if(type.equalsIgnoreCase("string")){
            if(default0){
                return "varchar(100) default null";
            }else{
                return "varchar(100)";
            }
        }else if(type.equalsIgnoreCase("float")){
            if(default0){
                return "float default 0";
            }else{
                return "float";
            }
        }else if(type.equalsIgnoreCase("double")){
            if(default0){
                return "double default 0";
            }else{
                return "double";
            }
        }else if(type.equalsIgnoreCase("decimal")){
            if(default0){
                return "decimal default 0";
            }else{
                return "decimal";
            }
        }else{
            if(default0){
                return "blob(100) default null";
            }else{
                return "blob(100)";
            }
        }

    }
}
