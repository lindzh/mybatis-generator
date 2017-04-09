create table ${mybatis.table} (
${mybatis.primary.column} ${sqlTypeFunc(mybatis.primary.type,'false')} primary key auto_increment,
<#list mybatis.columns as column>
${column.column} ${sqlTypeFunc(column.type!,'true')},
</#list>
<#list mybatis.uniques as u>
unique key ${u.name}(<#list u.columns as c>${c.column}</#list>),
</#list>
);