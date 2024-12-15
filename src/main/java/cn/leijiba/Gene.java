package cn.leijiba;

import jdk.nashorn.internal.ir.IfNode;

import java.util.Arrays;

public enum Gene {
    
    G("G",9),
    Y("Y",9),
    H("H",9),
    X("X",10),
    W("W",10);

    private String str = "";
    private  final int weight; 

    
    Gene(String str,int weight) 
    {
        this.str = str;
        this.weight = weight;
    }

    public int weight() {
        return this.weight;
    }

    public String str() {
        return this.str;
    }

    public static void main(String[] args) {
        System.out.println();
        Gene[] values = Gene.values();
        System.out.println(G.weight);
        System.out.println("===========");
        System.out.println(G.weight());
        Arrays.stream(values).forEach(System.out::println);
        
    }
}
