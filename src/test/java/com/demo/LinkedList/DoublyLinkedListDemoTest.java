package com.demo.LinkedList;

import org.junit.jupiter.api.Test;

public class DoublyLinkedListDemoTest {
    DoublyLinkedListDemo list = new DoublyLinkedListDemo();



    @Test
    void testAddFirst() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);// 3 2 1
        list.display();
    }

    @Test
    void testAddLast() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);// 1 2 3
        list.addFirst(0);// 0 1 2 3
        list.display();
    }

    @Test
    void testGet(){
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.display();
        System.out.println();
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
    }

    @Test
    void testSet(){
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.display();
        System.out.println();
        list.set(100,0);
        list.set(200,1);
        list.set(300,2);
        list.set(400,3);
        list.display();
    }

    @Test
    void testInsert(){
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.display();
        System.out.println();
        list.insert(0,0);
        list.insert(8,1);
        list.display();
    }

    @Test
    void testRemove(){
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.display();
        System.out.println();
        list.remove(1);
        list.remove(100);
        list.display();
    }

    @Test
    void testInversion(){
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.display();
        System.out.println();
        list.Inversion();
        list.display();
    }


}
