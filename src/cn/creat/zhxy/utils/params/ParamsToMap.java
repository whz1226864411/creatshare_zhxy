package cn.creat.zhxy.utils.params;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ParamsToMap {
	public static Map<String, Object> paramsToMapByUp(Object params,Class cl) throws IllegalArgumentException, IllegalAccessException{
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = cl.getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			String name = field.getName();
			String newName = name.substring(0, 1).toUpperCase() + name.substring(1);
			map.put(newName, field.get(params));
		}
		return map;
	}
}
