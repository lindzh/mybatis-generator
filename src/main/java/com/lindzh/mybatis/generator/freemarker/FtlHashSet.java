package com.lindzh.mybatis.generator.freemarker;

import com.lindzh.mybatis.generator.ThreadLocalCache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author lindezhi
 * 2015年11月14日 下午5:13:46
 */
public class FtlHashSet implements TemplateMethodModel{

	@Override
	public Object exec(List args) throws TemplateModelException {
		String setKey = (String)args.get(0);//set key
		String operate = (String)args.get(1);//operation
		String value = (String)args.get(2);//set value
		Set<String> set = (Set<String>) ThreadLocalCache.get(setKey);
		if(set==null){
			set = new HashSet<String>(); 
			ThreadLocalCache.put(setKey, set);
		}
		if(operate.equals("add")){
			return set.add(value.trim());
		}
		if(operate.equals("contains")){
			return set.contains(value.trim());
		}
		if(operate.equals("list")){
			List<String> list = new ArrayList<String>();
			list.addAll(set);
			return list;
		}
		if(operate.equals("size")){
			return set.size();
		}
		if(operate.equals("clear")){
			set.clear();
		}
		return null;
	}

}
