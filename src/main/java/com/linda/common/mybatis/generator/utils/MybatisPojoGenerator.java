package com.linda.common.mybatis.generator.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.linda.common.mybatis.generator.GeneratorException;
import com.linda.common.mybatis.generator.annotation.Column;
import com.linda.common.mybatis.generator.annotation.Index;
import com.linda.common.mybatis.generator.annotation.PrimaryKey;
import com.linda.common.mybatis.generator.annotation.Table;
import com.linda.common.mybatis.generator.annotation.UniqueKey;
import com.linda.common.mybatis.generator.bean.ColumnBean;
import com.linda.common.mybatis.generator.bean.IndexBean;
import com.linda.common.mybatis.generator.bean.MybatisPojo;
import com.linda.common.mybatis.generator.bean.UniqueBean;

/**
 * 注解分析器
 * @author lindezhi
 * 2015年11月14日 下午5:13:14
 */
public class MybatisPojoGenerator {
	
	private static String genName(String column,String property){
		if(StringUtils.isBlank(column)){
			return property;
		}
		return column;
	}
	
	private static String firstToUpperCase(String str){
		String first = str.charAt(0)+"";
		return first.toUpperCase()+str.substring(1);
	}
	
	@SuppressWarnings("unchecked")
	public static MybatisPojo genPojo(Class clazz,String daoPackage){
		MybatisPojo pojo = new MybatisPojo();
		
		pojo.setClassSimpleName(clazz.getSimpleName());
		
		String beanPackage = clazz.getPackage().getName();
		if(StringUtils.isNotBlank(beanPackage)){
			pojo.setBeanPackage(beanPackage);
		}
		if(StringUtils.isNotBlank(daoPackage)){
			pojo.setDaoPackage(daoPackage);
			pojo.setNamespace(daoPackage+"."+clazz.getSimpleName()+"Dao");
		}else{
			pojo.setNamespace(clazz.getSimpleName()+"Dao");
		}
		pojo.setDaoClassName(clazz.getSimpleName()+"Dao");
		pojo.setClassName(clazz.getName());
		pojo.setBeanMapper(clazz.getSimpleName()+"ResultMap");
		
		//生成表定义
		Table table = (Table)clazz.getAnnotation(Table.class);
		if(table==null||StringUtils.isBlank(table.name())){
			throw new GeneratorException(clazz.getName()+" require table name");
		}
		pojo.setTable(table.name());
		pojo.setAutoGenerate(table.autoGeneratePrimaryKey());
		
		Field[] fields = clazz.getDeclaredFields();
		if(fields==null||fields.length<1){
			throw new GeneratorException(clazz.getName()+" fields can't be null");
		}
		
		//cache for merge unique and index
		Map<String,UniqueBean> uniqueCache = new HashMap<String,UniqueBean>(); 
		Map<String,IndexBean> indexCache = new HashMap<String,IndexBean>();
		
		for(Field field:fields){
			PrimaryKey primary = (PrimaryKey)field.getAnnotation(PrimaryKey.class);
			if(primary!=null){
				//主键
				String column = primary.column();
				ColumnBean cp = new ColumnBean(field.getName(),genName(column,field.getName()),field.getType().getSimpleName());
				if(pojo.getPrimary()!=null){
					throw new GeneratorException(clazz.getName()+" can't define multi primary key");
				}
				pojo.setPrimary(cp);
				//生成唯一索引
				UniqueBean bean = genUniqueBean(firstToUpperCase(cp.getColumn()), true, true, true, cp);
				pojo.getUniques().add(bean);
			}else{
				//非主键列
				Column column = field.getAnnotation(Column.class);
				if(column!=null){
					//添加到列中
					String tcolumn = column.column();
					ColumnBean cp = new ColumnBean(field.getName(),genName(tcolumn,field.getName()),field.getType().getSimpleName());
					pojo.getColumns().add(cp);
					
					UniqueKey unique = (UniqueKey)field.getAnnotation(UniqueKey.class);
					if(unique!=null){//唯一索引
						UniqueBean bean = uniqueCache.get(unique.name());
						if(bean!=null){
							bean.getColumns().add(cp);
							bean.setDelete(unique.delete()&&bean.isDelete());
							bean.setSelect(unique.select()&&bean.isSelect());
							bean.setUpdate(unique.update()&&bean.isUpdate());
						}else{
							UniqueBean uniqueBean = genUniqueBean(firstToUpperCase(unique.name()), unique.select(),unique.update(), unique.delete(), cp);
							uniqueCache.put(unique.name(), uniqueBean);
							pojo.getUniques().add(uniqueBean);
						}
					}
					
					Index index = (Index)field.getAnnotation(Index.class);
					if(index!=null){//一般索引
						IndexBean bean = indexCache.get(index.name());
						if(bean!=null){
							bean.getColumns().add(cp);
							bean.setCount(bean.isCount()&&index.count());
							bean.setLimitOffset(bean.isLimitOffset()&&index.limitOffset());
							bean.setSelectOne(bean.isSelectOne()&&index.selectOne());
						}else{
							bean = new IndexBean();
							bean.getColumns().add(cp);
							bean.setName(firstToUpperCase(index.name()));
							bean.setCount(index.count());
							bean.setLimitOffset(index.limitOffset());
							bean.setSelectOne(index.selectOne());
							indexCache.put(index.name(), bean);
							pojo.getIndexes().add(bean);
						}
					}
				}
			}
		}
		
		setUpdateUniqueColumns(pojo);
		pojo.setTime(genTime());
		return pojo;
	}
	
	private static void setUpdateUniqueColumns(MybatisPojo pojo){
		List<ColumnBean> columns = pojo.getColumns();
		List<UniqueBean> uniques = pojo.getUniques();
		for(UniqueBean bean:uniques){
			HashMap<String, ColumnBean> columnCache = new HashMap<String,ColumnBean>();
			List<ColumnBean> idxColumns = bean.getColumns();
			for(ColumnBean idx:idxColumns){
				columnCache.put(idx.getColumn(), idx);
			}
			
			for(ColumnBean c:columns){
				if(!columnCache.containsKey(c.getColumn())){
					bean.getUpdateColumns().add(c);
				}
			}
		}
	}
	
	private static String genTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return format.format(new Date());
	}
	
	private static UniqueBean genUniqueBean(String name,boolean select,boolean update,boolean delete,ColumnBean ...beans){
		UniqueBean bean = new UniqueBean();
		bean.setDelete(delete);
		bean.setName(name);
		bean.setSelect(select);
		bean.setUpdate(update);
		for(ColumnBean b:beans){
			bean.getColumns().add(b);
		}
		return bean;
	}
}
