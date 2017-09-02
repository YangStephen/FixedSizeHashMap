package HashMap;

public class Entry {
    protected String k;
    protected Object v;
    protected Entry next;
    public Entry(String key, Object value){
        k = key;
        v = value;
    }
    public boolean keyMatch(String s){
        return s.equals(k);
    }
}