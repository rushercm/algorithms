package com.huawei.l00379880.algs4.chapter1javabasic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/***********************************************************
 * @Description : 表示基于数组的-容量可调地-泛型数据类型栈,
 *                可以支持任何数据类型,数组大小会自动调整地,
 *                当快满了,数组就自动翻倍扩容(通过复制现有元素到
 *                一个2倍大的数组);如果背包大小小于数组的1/4,数组
 *                就自动缩为原来的1/2.总体来说就是保持背包处于半满
 *                到全满的状态
 *                页面范围:P85~P88
 * @author      : 梁山广
 * @date        : 2017/12/31 14:22
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class P87ResizingArrayBag<Item> implements Iterable<Item> {
    /**
     * 泛型元素数组,用于存储外面来的Item.
     */
    private Item[] a;
    /**
     * 数组的实时大小
     */
    private int n;

    public P87ResizingArrayBag() {
        // 因为可以自动扩容,所以初始化为任意大小都可以
        this.a = (Item[]) new Object[2];
        // 初始化数组大小
        n = 0;
    }

    /**
     * 数组为空等效实数组大小为0
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * 返回背包的大小
     */
    public int size() {
        return n;
    }

    /**
     * 动态调整数组大小:当快满是就把元素移到一个更大的数组中
     */
    public void resize(int newCapacity) {
        // 新建个新的数组,具体大小有业务处判断
        Item[] temp = (Item[]) new Object[newCapacity];
        // 挨个复制元素
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        // 改变a数组,使其指向temp.原来的a指向的内存空间被释放.现在temp成了新的a
        a = temp;
    }

    /**
     * 推入元素,a[n++]=item用地很巧,等效于a[n]=item,n=n+1
     */
    public void add(Item item) {
        //先来看看是不是满了,满了就扩容2倍
        if (n == a.length) {
            resize(2 * a.length);
        }
        // 推入元素
        a[n++] = item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    /**
     * 实现背包的可迭代访问,原理讲解在P86,要实现可迭代的集合数据类型,应该满足以下两点:
     * 1.集合数据必须继承Iterator然后implements其中的iterator()方法并返回一个Iterator对象
     * 2.实现Iterator的类必须有hasNext(返回一个boolean值)和next(返回集合中的一个泛型元素)方法
     * 因为当前的背包是基于数组实现地而且是先进后出,所以下面的next函数应该是逆序访问数组
     */
    private class ArrayIterator implements Iterator<Item> {

        private int i;

        public ArrayIterator() {
            // 背包的访问顺序无所谓
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public Item next() {
            // 如果没有下一个元素了就要及时报出异常
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // 返回下一个元素然后指针移动到再下一个元素
            return a[i++];
        }

        @Override
        public void remove() {
            // 这个方法现在先不实现,不影响
            throw new UnsupportedOperationException();
        }
    }
}
