package com.study.community.Algorithm.designModel.singleton;

/**
 * 这里的懒汉式单例有问题，当多线程同时操作时，都可以得到lazysingleton==null；实例化多个对象。
 */
public class lazySingleton {
    //这里不进行实例化
    private static lazySingleton lazysingleton;

    private lazySingleton(){

    }

    public static lazySingleton getInstance(){
        if(lazysingleton==null){
            lazysingleton = new lazySingleton();
        }
        return lazysingleton;
    }

}
