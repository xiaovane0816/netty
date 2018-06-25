package com.vane.netty;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Class<?> classTest = Class.forName("com.vane.netty.Test");
////        Test classTest = new Test();
//        Annotation[] annotations = classTest.getAnnotations();
//        for(Annotation annotation: annotations) {
//            User u= (User) annotation;
//            System.out.println(u.name());
//        }


        ArrayList test = new ArrayList<String>();
        test.add(123);
        test.getClass().getMethod("add",Object.class).invoke(test,"asd");
        for(int i = 0; i < test.size(); i ++) {
            System.out.println(test.get(i));
        }

//        int i = add(1,2);
//        Number number = add(1,2.3);
//        Object c = add(1, "asd");
    }

    public static <T> T add(T a, T b ){
        return b;
    }
}
