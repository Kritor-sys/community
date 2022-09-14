package com.study.community.Algorithm.designModel.singleton;

public class testSingleton {
    public static void main(String[] args) {
        hungrySingleton singleton = hungrySingleton.getInstance();
        hungrySingleton instance = hungrySingleton.getInstance();
        System.out.println(singleton==instance);
    }
}
