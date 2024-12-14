package cn.leijiba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import cn.hutool.core.util.StrUtil;

import static cn.leijiba.Gene.*;

/**
 * 权重分布：
 * G 9
 * Y 9
 * H 9
 * X 10
 * W 10
 */
public class App {
    public static void main(String[] args) {
        List<String> all = new ArrayList<String>();
        all.add("GGGGGG");
        all.add("GGGGGG");
        all.add("GGGGGG");
        all.add("YYYYYY");

        List<List<Gene>> boxed = checkAndLoad(all);
        List<List<String>> lists = cross4(boxed);

    }
    
    public static List<List<Gene>> checkAndLoad(List<String> all) {
        List<List<Gene>> allGenesRows = new ArrayList<List<Gene>>();
        for (String strRow : all) {
            char[] stringList = strRow.toCharArray();
            // 使用 Stream API 将字符数组转换为字符串集合
            List<String> splits = IntStream.range(0, stringList.length)
                    .mapToObj(i -> String.valueOf(stringList[i]))
                    .collect(Collectors.toList());
            System.out.println("=========");
            splits.forEach(System.out::println);
            System.out.println("=========");
            List<Gene> geneRows = new ArrayList<>();
            for (String s : splits) {
                Gene gene = toGene(s);
                geneRows.add(gene);
            }
            allGenesRows.add(geneRows);
        }
        return allGenesRows;
    }


    private static Gene toGene(String s) {
        String upCase = s.toUpperCase();
        switch (upCase) {
            case "G":
                return G;
            case "Y":
                return Y;
            case "H":
                return H;
            case "W":
                return W;
            case "X":
                return X;
            default:
                throw new RuntimeException("未找到匹配基因");
        }
    }


    //TODO
    public static List<List<String>> cross4(List<List<Gene>> allGenesRows)  {
        if (allGenesRows.size() != 4) {
            throw new RuntimeException("超出数量限制");
        }
        System.out.println(allGenesRows);
        Col col1;
        Col col2;
        Col col3;
        Col col4;
        Col col5;
        Col col6;
        List<Gene> col1Genes = allGenesRows.stream().map(l -> l.get(0)).collect(Collectors.toList());
        List<Gene> col2Genes = allGenesRows.stream().map(l -> l.get(1)).collect(Collectors.toList());
        List<Gene> col3Genes = allGenesRows.stream().map(l -> l.get(2)).collect(Collectors.toList());
        
        return null;
    }
    
}

