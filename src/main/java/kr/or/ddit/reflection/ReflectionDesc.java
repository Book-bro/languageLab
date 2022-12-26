package kr.or.ddit.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import kr.or.ddit.reflect.ReflectionTest;
import kr.or.ddit.vo.MemberVO;
/**
 * Reflection (java.lang.reflect)
 * 	: 객체의 타입, 객체의 속성(상태, 행동) 들을 역으로 추적하는 작업.
 * 
 */
public class ReflectionDesc {
	public static void main(String[] args) {
		Object dataObj = ReflectionTest.getObject();
		System.out.println(dataObj);
		Class<?> objType = dataObj.getClass();
		System.out.println(objType.getName());
		
		Field[] fields = objType.getDeclaredFields();
//		Arrays.stream(fields)
//				.forEach(System.out::println);
		Method[] methods =  objType.getDeclaredMethods();
//		Arrays.stream(methods)
//				.forEach(System.out::println);
		
		try {
			Object newObj = objType.newInstance();
			Arrays.stream(fields)
				.forEach(fld->{
//						fld.setAccessible(true);
					String fldName = fld.getName(); //mem_id, getMem_id, setMem_id
					try {
						PropertyDescriptor pd = new PropertyDescriptor(fldName, objType);
						Method getter = pd.getReadMethod(); //getter
						Method setter = pd.getWriteMethod(); //setter
						//getter
//							Object fldValue = fld.get(dataObj);
						Object fldValue = getter.invoke(dataObj);
						//setter
//							fld.set(newObj, fldValue);
						setter.invoke(newObj,fldValue);
						
						
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IntrospectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			System.out.println(newObj);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
