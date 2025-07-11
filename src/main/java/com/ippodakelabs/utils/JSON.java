package com.ippodakelabs.utils;


import com.ippodakelabs.anotations.ColumnInfo;
import com.ippodakelabs.anotations.Entity;
import com.ippodakelabs.types.SqlTypes;

public class JSON {
    public static <T> String stringifyEntity(T obj) {
        if(!obj.getClass().isAnnotationPresent(Entity.class)) throw new RuntimeException(obj.getClass().getName() + " can not be parsed because is not an entity");
        try {
            var fields = obj.getClass().getDeclaredFields();
            StringBuilder jsonTxt = new StringBuilder();
            jsonTxt.append("{");
            for(var field : fields)
            {
                if(field.isAnnotationPresent(ColumnInfo.class))
                field.setAccessible(true);
                if(field.get(obj) == null) continue;
                var data = String.format(SqlTypes.get(field.getType()).stringifyType(), field.get(obj));
                jsonTxt.append(String.format("%s: %s,",field.getAnnotation(ColumnInfo.class).name(),data));
            }
            jsonTxt = new StringBuilder(jsonTxt.substring(0, jsonTxt.length()-1));
            jsonTxt.append("}");
            return jsonTxt.toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        throw new RuntimeException("Error  in JSON class xddd xd");
    }
}
