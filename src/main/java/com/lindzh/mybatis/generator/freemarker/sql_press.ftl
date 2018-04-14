create table ${mybatis.table} (
<#assign csize=mybatis.columns?size>
<#assign usize=mybatis.uniques?size>
<#assign total=csize+usize>
${mybatis.primary.column} ${sqlTypeFunc(mybatis.primary.type,'false')} primary key auto_increment<#if total&gt;0>,</#if>
<#assign idx=1>
<#list mybatis.columns as column>
${column.column} ${sqlTypeFunc(column.type!,'true')}<#if idx&lt;total>,</#if><#assign idx=idx+1>
</#list>
<#list mybatis.uniques as u>
<#if u.columns[0].column!=mybatis.primary.column>
unique key ${u.name}(<#assign f=0 ><#list u.columns as c><#if f&gt;0>,</#if>${c.column}<#assign f=f+1 ></#list>)<#if idx&lt;total>,</#if>
</#if>
<#assign idx=idx+1>
</#list>
) comment '${mybatis.table}';