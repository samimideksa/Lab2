package PartB;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    private String input;

    public Mapper(InputSplit inputSplit){
        this.input=inputSplit.getInput();
    }

    public List<Pair<String, Integer>> map() {
        input = input.replace("\n", " ");
        input = input.replace("\"", "");
        input = input.replace("'", "");
        String[] words = input.split(" |-");
        return Arrays.stream(words)
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
                .map(e -> new Pair<String, Integer>(e, 1))
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .collect(Collectors.toList());
    }
}
