package PartB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reducer {
    List<Pair<String, List<Integer>>> input = new ArrayList<Pair<String, List<Integer>>>();


    public List<Pair<String, List<Integer>>> getInput() {
        return input;
    }

    public void setInput(List<Pair<String, List<Integer>>> input) {
        this.input = input;
    }

    public List<Pair<String, Integer>> reduce() {
        return input.stream()
                .map(e-> new Pair<String,Integer>(e.getKey(),e.getValue().stream().mapToInt(i->i.intValue()).sum()))
                .collect(Collectors.toList());
    }
}
