package org.zhaoyu.test.redis.dealobject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 在redis存入对象时，序列化和反序列化对象的工具。
 * @author Administrator
 *
 */
public class SerializeUtil {
	public static byte[] serialize(Object object) throws IOException{
		ObjectOutputStream oos=null;
		ByteArrayOutputStream baos=null;
		byte[] bytes;
		try {
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes=baos.toByteArray();
		} catch (IOException e) {
			throw e;
		}
		return bytes;
	}
	
	public static Object unserialize(byte[] bytes) throws Exception{
		ByteArrayInputStream bais=null;
		ObjectInputStream ois=null;
		Object object=null;
		try {
			bais=new ByteArrayInputStream(bytes);
			ois=new ObjectInputStream(bais);
			object= ois.readObject();
		} catch (Exception e) {
			throw e;
		}
		return object;
	}
	
}
