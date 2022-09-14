package com.study.community.Algorithm.designModel.singleton;

public class lazySingeleton2 {
    //volatile:保证变量值在各个线程访问时的同步性和唯一性。
    private volatile static lazySingeleton2 lazysingleton2;

    private lazySingeleton2(){

    }

    /**
     * 注意：
     * synchronized：锁是可以加载方法上的public static  synchronized lazySingeleton2 getInstance()；
     *              但是这样，如果多线程访问这个方法，会造成线程堵塞。
     *              这里加在了代码块上，进行两次判空，第一次，是多线程进入这个方法，判空，然后加锁，第二次判空，只要有一人判空
     *              就可以得到实例，下面的所有线程判空失败直接得到实例。
     * @return
     */
    public static  lazySingeleton2 getInstance() {
            if(lazysingleton2==null){
                synchronized (lazySingeleton2.class){
                    if(lazysingleton2==null){
                        lazysingleton2 = new lazySingeleton2();
                    }
                }
            }
            return lazysingleton2;
    }
}
