package cn.leijiba;

import jdk.nashorn.internal.ir.IfNode;

import java.util.Arrays;

public enum Gene {
    
    G(9),
    Y(9),
    H(9),
    X(10),
    W(10);

    private final int weight; 

    
    Gene(int weight) {
        this.weight = weight;
    }

    public int weight() {
        return this.weight;
    }

    public static void main(String[] args) {
        System.out.println();
        Gene[] values = Gene.values();
        Arrays.stream(values).forEach(System.out::println);
        
    }
}
