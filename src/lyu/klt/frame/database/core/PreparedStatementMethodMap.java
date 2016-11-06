package lyu.klt.frame.database.core;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class PreparedStatementMethodMap {
	
	private static PreparedStatementMethodMap instance = null;
	
	private Map<Class<? extends Object>,Method> map = null;

	private PreparedStatementMethodMap() throws Exception{
		this.map = new HashMap<Class<? extends Object>,Method>();

		this.map.put(Boolean.class, PreparedStatement.class.getMethod("setBoolean", new Class[]{int.class,boolean.class}));
		this.map.put(boolean.class, PreparedStatement.class.getMethod("setBoolean", new Class[]{int.class,boolean.class}));
		this.map.put(Byte.class, PreparedStatement.class.getMethod("setByte", new Class[]{int.class,byte.class}));
		this.map.put(byte.class, PreparedStatement.class.getMethod("setByte", new Class[]{int.class,byte.class}));
		this.map.put(Short.class, PreparedStatement.class.getMethod("setShort", new Class[]{int.class,short.class}));
		this.map.put(short.class, PreparedStatement.class.getMethod("setShort", new Class[]{int.class,short.class}));
		this.map.put(Integer.class, PreparedStatement.class.getMethod("setInt", new Class[]{int.class,int.class}));
		this.map.put(int.class, PreparedStatement.class.getMethod("setInt", new Class[]{int.class,int.class}));
		this.map.put(Long.class, PreparedStatement.class.getMethod("setLong", new Class[]{int.class,long.class}));
		this.map.put(long.class, PreparedStatement.class.getMethod("setLong", new Class[]{int.class,long.class}));
		this.map.put(Float.class, PreparedStatement.class.getMethod("setFloat", new Class[]{int.class,float.class}));
		this.map.put(float.class, PreparedStatement.class.getMethod("setFloat", new Class[]{int.class,float.class}));
		this.map.put(Double.class, PreparedStatement.class.getMethod("setDouble", new Class[]{int.class,double.class}));
		this.map.put(double.class, PreparedStatement.class.getMethod("setDouble", new Class[]{int.class,double.class}));
		this.map.put(BigDecimal.class, PreparedStatement.class.getMethod("setBigDecimal", new Class[]{int.class,BigDecimal.class}));
		this.map.put(String.class, PreparedStatement.class.getMethod("setString", new Class[]{int.class,String.class}));
		this.map.put(Byte[].class, PreparedStatement.class.getMethod("setBytes", new Class[]{int.class,byte[].class}));
		this.map.put(byte[].class, PreparedStatement.class.getMethod("setBytes", new Class[]{int.class,byte[].class}));
		this.map.put(Date.class, PreparedStatement.class.getMethod("setDate", new Class[]{int.class,Date.class}));
		this.map.put(Time.class, PreparedStatement.class.getMethod("setTime", new Class[]{int.class,Time.class}));
		this.map.put(Timestamp.class, PreparedStatement.class.getMethod("setTimestamp", new Class[]{int.class,Timestamp.class}));
		this.map.put(Object.class, PreparedStatement.class.getMethod("setObject", new Class[]{int.class,Object.class}));
		this.map.put(Ref.class, PreparedStatement.class.getMethod("setRef", new Class[]{int.class,Ref.class}));
		this.map.put(Blob.class, PreparedStatement.class.getMethod("setBlob", new Class[]{int.class,Blob.class}));
		this.map.put(Clob.class, PreparedStatement.class.getMethod("setClob", new Class[]{int.class,Clob.class}));
		this.map.put(Array.class, PreparedStatement.class.getMethod("setArray", new Class[]{int.class,Array.class}));
		this.map.put(URL.class, PreparedStatement.class.getMethod("setURL", new Class[]{int.class,URL.class}));
	}

	public static PreparedStatementMethodMap getInstance() throws Exception{
		if(instance==null)
			instance = new PreparedStatementMethodMap();
		return instance;
	}
	
	public Method getMethod(Class<? extends Object> clazz){
		return this.map.get(clazz);
	}
}