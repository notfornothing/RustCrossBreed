package cn.leijiba;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import sun.nio.cs.ext.EUC_CN;

import javax.sound.midi.Soundbank;

import static cn.leijiba.Gene.*;

/**
 * 权重分布：
 * G 9
 * Y 9
 * H 9
 * X 10
 * W 10
 */

/**
 * yyxw -> y
 * yyxx -> x
 */
public class App {
    public static void main(String[] args) {
        List<String> all = new ArrayList<String>();
        all.add("GGGGGG");
        all.add("GGGGGG");
        all.add("YGGGGG");
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
            for (String geneStr : splits) {
                Gene gene = toGene(geneStr);
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


    // 先算4个杂交后的 再去做前置入口
    public static List<List<String>> cross4(List<List<Gene>> allGenesRows) {
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
        //calculate the col each weight
        List<Gene> col1Genes = allGenesRows.stream().map(l -> l.get(0)).collect(Collectors.toList());
        System.out.println(col1Genes);
        
//        ====
        List<Col> cols = new ArrayList<>();
        for (Gene gene : col1Genes) {
            Col col = new Col();
            switch (gene) {
                case G:
                    col.setG(col.getG() + G.weight());
                    break;
                case Y:
                    col.setY(col.getY() + Y.weight());
                    break;
                case H:
                    col.setH(col.getH() + H.weight());
                    break;
                case X:
                    col.setX(col.getX() + X.weight());
                    break;
                case W:
                    col.setW(col.getW() + W.weight());
                    break;
            }
            cols.add(col);
        }


        // Calculate total weights for each gene type
        Integer totalG = cols.stream().mapToInt(Col::getG).sum();
        Integer totalY = cols.stream().mapToInt(Col::getY).sum();
        Integer totalH = cols.stream().mapToInt(Col::getH).sum();
        Integer totalX = cols.stream().mapToInt(Col::getX).sum();
        Integer totalW = cols.stream().mapToInt(Col::getW).sum();

        Map<Gene, Integer> geneWeightMap = new HashMap<>();
        geneWeightMap.put(G, totalG);
        geneWeightMap.put(Y, totalY);
        geneWeightMap.put(H, totalH);
        geneWeightMap.put(X, totalX);
        geneWeightMap.put(W, totalW);

        HashMap<Gene, Integer> geneWeightHashMapBackUp = new HashMap<>(geneWeightMap);
        //去除不存在的基因（相等判断）
        geneWeightMap.entrySet().removeIf(entry -> entry.getValue() == 0);
        
        
//    是否存在权重不相等的情况

        List<Gene> equalGenes = new ArrayList<>();
        for (Gene gene : geneWeightMap.keySet()) {
            int weight = geneWeightMap.get(gene);
            if (geneWeightMap.values().stream().filter(w -> w == weight).count() > 1) {
                equalGenes.add(gene);
            }
        }
        
        if (equalGenes.size() == 0) {
            System.out.println("不存在相等权重基因");
            // 找出权重最大的基因
            Gene maxGene = geneWeightMap.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .orElseThrow(() -> new RuntimeException("无法找到最大权重基因"))
                    .getKey();
            geneWeightHashMapBackUp.forEach((key, value) -> System.out.println(key + " " + value));
            System.out.println("Gene: "+maxGene+" weight:"+geneWeightMap.get(maxGene));
        }
        if (equalGenes.size() != 0) {
            System.out.println("存在相等权重基因");
        }
        
        


        return null;
    }
}

