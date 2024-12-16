package cn.leijiba;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        all.add("yyyyyy");
        all.add("hhhhhh");
        all.add("Gxxxxx");

        all.add("wYwwww");

        // 4个杂交2个基因相同的情况最多两次，3个相同的基因种子杂交没意义。因为权重肯定是3个相同的大
        all.addAll(all);

        // 排列组合，
        // 5组基因，应该怎么做？
        List<String> ready = new ArrayList<>();

        List<Possible> allPossible = new ArrayList<>();


        for (int i = 0; i < all.size(); i++) {
            ready.add(all.get(i));
            for (int j = i + 1; j < all.size(); j++) {
                ready.add(all.get(j));
                for (int k = j + 1; k < all.size(); k++) {
                    ready.add(all.get(k));
                    for (int l = k + 1; l < all.size(); l++) {
                        ready.add(all.get(l));

                        List<List<Gene>> boxed = checkAndLoad(ready);
                        System.out.println("=======");
                        // TODO 每一条基因可以是 set 因为是唯一的 (不一定是set自己与自己杂交也得考虑
                        List<String> lists = cross4(boxed);
                        System.out.print("readyOut ====> ");
                        lists.forEach(e -> System.out.print(e + " "));
                        System.out.println();
                        System.out.println("=======");

                        List<Integer> collect = lists.stream().map(e -> {
                                    int score = 0;
                                    RateGene[] values = RateGene.values();
                                    for (RateGene rate : values) {
                                        if (e.contains(rate.str())) {
                                            score += rate.weight();
                                        } else {
                                            ;
                                        }
                                    }
                                    return score;
                                }
                        ).collect(Collectors.toList());

                        Integer rate = collect.stream().reduce(0, Integer::sum);
                        Possible possible = new Possible();
                        possible.origin(boxed)
                                .rate(rate)
                                .result(lists);
                        allPossible.add(possible);

                        ready.remove(ready.size() - 1);
                    }
                    ready.remove(ready.size() - 1);
                }
                ready.remove(ready.size() - 1);
            }
            ready.remove(ready.size() - 1);
        }


        //rate 最大的集合
        // rate 
        // G Y ==> 2 
        // H   ==> 1 
        // W X ==> 0

        Integer max = allPossible.stream()
                .map(Possible::rate)
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("无法找到最大权重基因"));

        List<Possible> maxPossibleList = allPossible.stream()
                .filter(p -> Objects.equals(p.rate(), max))
                .collect(Collectors.toList());
        // 输出最大 rate 的 Possible 对象集合
        System.out.println("具有最大 rate 的 Possible 对象集合：");
        maxPossibleList.forEach(System.out::println);
        maxPossibleList.forEach(e->
        {
            System.out.println("=======");
            System.out.println("origin:");
            for (List<Gene> genes : e.origin) {
                System.out.println(genes);
            }
            System.out.println("result:");
            System.out.println(e.result);
        });
    }


    public static List<List<Gene>> checkAndLoad(List<String> all) {
        List<List<Gene>> allGenesRows = new ArrayList<List<Gene>>();
        for (String strRow : all) {
            char[] stringList = strRow.toCharArray();
            // 使用 Stream API 将字符数组转换为字符串集合
            List<String> splits = IntStream.range(0, stringList.length)
                    .mapToObj(i -> String.valueOf(stringList[i]))
                    .collect(Collectors.toList());
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
    public static List<String> cross4(List<List<Gene>> allGenesRows) {
        if (allGenesRows.size() != 4) {
            throw new RuntimeException("超出数量限制");
        }
        System.out.println("四条基因分别为：");
        allGenesRows.forEach(System.out::println);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<Gene> colResult = getColResult(allGenesRows, i);
            List<String> collect = colResult.stream().map(e -> e.str()).collect(Collectors.toList());
            String resultOneCol = collect.stream().collect(Collectors.joining("/"));
            result.add(resultOneCol);
        }
        return result;
    }

    //calculate the col each weight
    private static List<Gene> getColResult(List<List<Gene>> colGene, int i) {
        List<Gene> genes = colGene.stream().map(e -> e.get(i)).collect(Collectors.toList());
        List<Col> cols = new ArrayList<>();
        for (Gene gene : genes) {
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

        HashMap<Gene, Integer> geneWeightMapBackUp = new HashMap<>(geneWeightMap);

        //去除不存在的基因（后续有相等判断）
        geneWeightMap.entrySet().removeIf(entry -> entry.getValue() == 0);
        //相等基因权重累加
        List<Gene> equalGenes = new ArrayList<>();
        for (Gene gene : geneWeightMap.keySet()) {
            int weight = geneWeightMap.get(gene);
            if (geneWeightMap.values().stream().filter(w -> w == weight).count() > 1) {
                equalGenes.add(gene);
            }
        }

        System.out.println("原始列基因：");
        geneWeightMapBackUp.forEach((key, value) -> System.out.println(key + " " + value));

        //权重不相等的情况
        //不存在相等权重基因
        if (equalGenes.size() == 0) {
            // 找出权重最大的基因
            Gene maxGene = geneWeightMap.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .orElseThrow(() -> new RuntimeException("无法找到最大权重基因"))
                    .getKey();
            geneWeightMap.entrySet().removeIf(e -> !e.getValue().equals(geneWeightMap.get(maxGene)));
            System.out.println("Gene: " + maxGene + " weight:" + geneWeightMap.get(maxGene));
        }

        //存在相等权重基因
        if (equalGenes.size() != 0) {
            Gene maxGene = geneWeightMap.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .orElseThrow(() -> new RuntimeException("无法找到最大权重基因"))
                    .getKey();
            List<Gene> collect = geneWeightMap.entrySet().stream().filter(e -> e.getValue().equals(geneWeightMap.get(maxGene))).map(e -> e.getKey()).collect(Collectors.toList());
            //如果存在权重相等，只需找到权重最大的就行，故移除不等于最大的
            geneWeightMap.entrySet().removeIf(e -> !e.getValue().equals(geneWeightMap.get(maxGene)));
            System.out.println("Gene: " + collect + " weight:" + geneWeightMap.get(maxGene));
        }
        return new ArrayList<>(geneWeightMap.keySet());
    }

}