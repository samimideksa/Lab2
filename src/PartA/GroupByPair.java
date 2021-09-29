package PartA;

public class GroupByPair<K, V> {
    private K K;
    private V V;

    public GroupByPair() {

    }

    public GroupByPair(K k, V v) {
        K = k;
        V = v;
    }

    public K getK() {
        return K;
    }

    public void setK(K k) {
        K = k;
    }

    public V getV() {
        return V;
    }

    public void setV(V v) {
        V = v;
    }

    @Override
    public String toString() {
        return "<" + K + ", " + V + '>';
    }
}