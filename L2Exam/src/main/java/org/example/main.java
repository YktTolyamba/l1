package org.example;
import java.util.*;
public class main {
    public static void main(String[] args) {
        Formatter fmt = new Formatter();
        fmt.format("% d", -100);
        System.out.println(fmt);
        fmt.close();
        fmt = new Formatter();
        fmt.format("% d", 100);
        System.out.println(fmt);
        fmt.close();
        fmt = new Formatter();
        fmt.format("%d", -200);
        System.out.println(fmt);
        fmt.close();
        fmt = new Formatter();
        fmt.format("%d", 200);
        System.out.println(fmt);
        fmt.close();
    }
}
