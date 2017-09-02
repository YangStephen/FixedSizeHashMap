package HashMap;

/*
    FixedSizeHashMap is a reimplementation of a Java HashMap implementing
    a restricted size HashMap from scratch. This implementation uses N buckets
    to manage the number of entries available.
*/
public class FixedSizeHashMap {
    Entry[] data;
    int mapSize;
    /*
        FixedSizeHashMap(int size) constructs a new FixedSizeHashMap restricted
        to a size of the input variable size.

        @param  size    a size to restrict the HashMap to.
    */
    public FixedSizeHashMap(int size){
        data = new Entry[size < 0 ? 0 : size];
        mapSize = 0;
    }

    /*
         calculateIndex(String key) calculates the index / bucket to fetch or
         insert an entry into given the key of the value.

         @param  key    the key of the Entry we are searching / inserting.
    */
    private int calculateIndex(String key){
        return (key.hashCode() >>> (data.length - 1)) & (data.length - 1);
    }
    /*
          set(String key, Object value) inserts a value into the HashMap so
          long as there is space for the entry (key,value).

          @param  key    the key of the Entry we are searching / inserting.
          @param  value  the value of the entry mentioned above
    */

    public boolean set(String key, Object value) {
        int index = calculateIndex(key);
        if (data.length == 0 || index > data.length) return false;

        for (Entry entry = data[index]; entry != null; entry = entry.next){
            if (entry.keyMatch(key)) {
                entry.v = value;
                return true;
            }
        }

        if (mapSize == data.length) {
            return false;
        }

        Entry e = new Entry(key, value);
        e.next = data[index];
        data[index] = e;
        mapSize += 1;
        return true;
    }

    /*
           set(String key, Object value) fetches the value associated with
           a key, returns null if it does not find one.

           @param  key    the key of the Entry we are searching for
    */
    public Object get(String key) {
        int index = calculateIndex(key);
        if (index > data.length) return null;

        if (data.length == 0 || data[index] == null)
            return null;
        for (Entry entry = data[index]; entry != null; entry = entry.next){
            if (entry.keyMatch(key))
                return entry.v;
        }
        return null;
    }

    /*
            delete(String key) deletes the key and returns the object / value
            associated with it or null if nothing is deleted

            @param  key    the key of the Entry we are searching / inserting.
    */
    public Object delete(String key) {
        int index = calculateIndex(key);
        if (data[index] == null)
            return null;
        if (data[index].keyMatch(key)) {
            Object val = data[index].v;
            data[index] = data[index].next;
            mapSize -= 1;
            return val;
        }
        for (Entry e = data[index]; e != null; e = e.next){
            if (e.next != null && e.next.keyMatch(key)) {
                Object val = e.next.v;
                e.next = e.next.next;
                mapSize -= 1;
                return val;
            }
        }
        return null;
    }

    /*
            load() calculated the float value of the load for the HashMap
    */
    public float load(){
        if (data.length == 0) return 0;
        return (mapSize * 1.0f) / data.length;
    }

    /*
             print() is a function that I have personally included. It prints out
             the entirety of all entries as well as which bucket they are in, what
             the key,value pair is, etc.
    */
    public void print(){
        if (mapSize == 0) {
            System.out.println("No Data in HashMap");
            return;
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                System.out.print("HashMap Bucket " + i + ": ");
                Entry e = data[i];
                while (e != null) {
                    System.out.print("("+e.k+","+e.v+")");
                    if (e.next != null){
                        System.out.print(", ");
                    }
                    e = e.next;
                }
                System.out.println();
            }
        }
    }
}