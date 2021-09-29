package PartB;

import java.util.ArrayList;
import java.util.List;

public class WordCount {
    InputSplit inputSplits[] = {
            new InputSplit("\"cat bat\" mat-pat mum.edu sat.\nfat 'rat eat cat' mum_cs mat"),
            new InputSplit("bat-hat mat pat \"oat\nhat rat mum_cs eat oat-pat"),
            new InputSplit("zat lat-cat pat jat.\nhat rat. kat sat wat")
    };
    Reducer reducers[]= {
            new Reducer(),
            new Reducer(),
            new Reducer(),
            new Reducer()
    };
    Mapper mappers[] = new Mapper[inputSplits.length];

    public void count() {

        System.out.println("Number of Input-Splits: "+inputSplits.length);


        System.out.println("Number of Reducers: "+reducers.length);



        for(int i=0;i<inputSplits.length;i++) {
            mappers[i]=new Mapper(inputSplits[i]);
            System.out.println("Mapper "+i+" Input");
            System.out.println(inputSplits[i].getInput());
        }


        for(int i=0;i<inputSplits.length;i++) {
            System.out.println("Mapper "+i+" Output");
            mappers[i].map().stream().forEach(e->System.out.println(e));
        }

        for(int i=0;i<mappers.length;i++) {

            for(int j=0;j<reducers.length;j++) {

                List<Pair<String, Integer>> mapperOutputs = mappers[i].map();
                System.out.println("Pairs send from Mapper "+i+" Reducer "+j);


                outer:
                for(Pair<String, Integer> mapperOutput:mapperOutputs) {
                    if(j==this.getPartition(mapperOutput.getKey())) {
                        System.out.println(mapperOutput);

                        for(Pair<String, List<Integer>> reduceInput: reducers[j].input) {
                            if(reduceInput.getKey().equals(mapperOutput.getKey())) {
                                reduceInput.getValue().add(1);
                                continue outer;
                            }
                        }
                        List<Integer> l = new ArrayList<Integer>();
                        l.add(1);
                        reducers[j].input.add(new Pair<String, List<Integer>>(mapperOutput.getKey(),l));

                    }
                }
            }
        }



        for(int j=0;j<reducers.length;j++) {
            System.out.println("Reducer "+j+" input");
            reducers[j].getInput()
                    .stream()
                    .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                    .forEach(e->System.out.println(e));
        }


        for(int j=0;j<reducers.length;j++) {
            System.out.println("Reducer "+j+" output");
            reducers[j].reduce()
                    .stream()
                    .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                    .forEach(e->System.out.println(e));
        }

    }

    public int getPartition(String key){
        return (int) key.hashCode() % reducers.length;
    }

    public static void main(String args[]) {
        WordCount wc = new WordCount();
        wc.count();
    }
}
