package HashMap;

/*
    Entry is a class created to closely represent a Key-Value pair object with the ability to extend into a LinkedList
    through the next variable. This is the head of each bucket in which the buckets can become a linked list of all
    available keys and their corresponding values.
*/
public class Entry {
    protected String k;
    protected Object v;
    protected Entry next;

    /*
        Entry(String key, Object value, Entry nextEntry) is a constructor for the Entry object such that we can
        pass in a next value (this is used during insertion of values in FixedSizeHashMap.set());
     */
    public Entry(String key, Object value, Entry nextEntry){
        k = key;
        v = value;
        next = nextEntry;
    }

    /*
        keyMatch(String s) is a function returning a boolean value if a key equates to the key of this Entry object.

        @param  key key to the key of this object too
     */
    public boolean keyMatch(String key){
        return key.equals(k);
    }
}