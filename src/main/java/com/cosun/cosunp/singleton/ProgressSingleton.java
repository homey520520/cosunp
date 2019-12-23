package com.cosun.cosunp.singleton;

import java.util.Hashtable;

/**
 * @author:homey Wong
 * @date:2019/3/2 0002 下午 5:01
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ProgressSingleton {

      private static Hashtable<Object, Object> table = new Hashtable<>();

              public static void put(Object key, Object value){
                 table.put(key, value);
             }

             public static Object get(Object key){
                 return table.get(key);
             }

             public static Object remove(Object key){
                 return table.remove(key);
             }

}
