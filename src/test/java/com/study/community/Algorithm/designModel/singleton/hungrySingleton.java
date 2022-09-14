package com.study.community.Algorithm.designModel.singleton;

public class hungrySingleton {

    //在类加载时直接创建实例
    private static final hungrySingleton hungrysingleton = new hungrySingleton();

    //构造器私有化，外界不可以使用构造器实例化对象
    private hungrySingleton() {
    }

    //提供一个方法获取对象
    public static hungrySingleton getInstance(){
        return hungrysingleton;
    }
}
