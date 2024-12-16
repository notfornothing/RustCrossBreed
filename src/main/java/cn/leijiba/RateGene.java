package cn.leijiba;

import java.util.Arrays;

public enum RateGene {
    
    G("G",2),
    Y("Y",2),
    H("H",1),
    X("X",0),
    W("W",0);

    private String str = "";
    private  final int weight; 

    
    RateGene(String str, int weight) 
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
        RateGene[] values = RateGene.values();
        System.out.println(G.weight);
        System.out.println("===========");
        System.out.println(G.weight());
        Arrays.stream(values).forEach(System.out::println);
        
    }
}
