package PartA;

import PartA.GroupByPair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String everything = null;
        try(BufferedReader br = new BufferedReader(new FileReader("./src/PartA/testDataForW1D1.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
//            System.out.println(everything);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        everything = everything.replace("\n", " ");


        String[] words = everything.split(" |-");

        List<GroupByPair<String,Integer>> pairs = Arrays.stream(words)
                .sorted()
                .map(e -> e.endsWith(".") || e.endsWith(",") ? e.substring(0, e.length() - 1) : e).map(e -> {
                    char chars[] = e.toCharArray();
                    for (char c : chars) {
                        if (!Character.isAlphabetic(c)) {
                            return null;
                        }
                    }
                    return e;
                })
                .filter(e -> e != null)
                .filter(e -> !e.isEmpty())
                .map(e -> e.toLowerCase())
                .map(e -> new GroupByPair<String, Integer>(e, 1))
                .sorted((o1,o2)->o1.getK().compareTo(o2.getK()))
                .collect(Collectors.toList());

        System.out.println("\n\nMapper Output\n");
        pairs.forEach(e->System.out.println(e));


        List<GroupByPair<String, List<Integer>>> groupByPair = new ArrayList<GroupByPair<String, List<Integer>>>();

        outer:
        for(GroupByPair<String,Integer> p : pairs) {
            for(GroupByPair<String, List<Integer>> gp: groupByPair) {
                if(gp.getK().equals(p.getK())) {
                    gp.getV().add(1);
                    continue outer;
                }
            }
            List<Integer> l = new ArrayList<Integer>();
            l.add(1);
            groupByPair.add(new GroupByPair<String, List<Integer>>(p.getK(),l));
        }
        System.out.println("\n\nReducer Input\n");
        groupByPair.forEach(e->System.out.println(e));


        System.out.println("\n\nReducer Output\n");

        List<GroupByPair<String,Integer>> pairsSum = groupByPair.stream()
                .map(e-> new GroupByPair<String,Integer>(e.getK(),e.getV().stream().mapToInt(i->i.intValue()).sum()))
                .collect(Collectors.toList());

        pairsSum.forEach(e->System.out.println(e));
    }
}
