package com.demo.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SinglyLinkedListDemoTest {
    private SinglyLinkedListDemo list;

    SinglyLinkedListDemo list1 = new SinglyLinkedListDemo();
    SinglyLinkedListDemo list2 = new SinglyLinkedListDemo();

    @BeforeEach
    void setUp() {
        list = new SinglyLinkedListDemo();
    }

    @Test
    void testAddFirst() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);// 3 2 1
        list.display();

    }

    @Test
    void testRemove() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);// 3 2 1
        list.remove(2);
        list.remove(5);
        list.display();
    }

    @Test
    void testAddLast() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.display();
    }


    @Test
    void testGetSize() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        System.out.println(list.getSize());
        list.remove(2);
        System.out.println(list.getSize());
        list.remove(5);
        System.out.println(list.getSize());
        list.display();

    }

    @Test
    void testFirstGet() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        System.out.println(list.get(5));
    }

    @Test
    void testLastGet() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        System.out.println(list.get(5));
    }

    @Test
    void testInsert() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.insert(5, 1);
        list.display();
    }

    @Test
    void testSet() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.set(1, 5);
        list.display();
    }

    @Test
    void testContains() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        System.out.println(list.contains(2));
        System.out.println(list.contains(5));
    }

    @Test
    void testIndexOf() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        System.out.println(list.indexOf(2));
        System.out.println(list.indexOf(5));
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

    @Test
    void testMergeTwoLists(){
        list1.addLast(1);
        list1.addLast(3);
        list1.addLast(5);
        list1.display();
        System.out.println();
        list2.addLast(1);
        list2.addLast(4);
        list2.addLast(6);
        list2.display();
        System.out.println();
        list = list.mergeTwoLists(list1, list2);
        list.display();
    }
}
