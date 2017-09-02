package HashMap;

/*
    FixedSizeHashMap is a reimplementation of a Java HashMap implementing
    a restricted size HashMap from scratch. This implementation uses N buckets
    to manage the number of entries available.

    Example: For a HashMap Containing KPCB,
    HashMap Bucket 0: (B,4), (C,3)
    HashMap Bucket 1: (K,1)
    HashMap Bucket 2: (P,2)
*/
public class FixedSizeHashMap {
    Entry[] data; // Array of LinkedLists (Entry is essentially a Key,Value Node with a next value)
    int mapSize, maxSize; // integer containing the size of the HashMap that has been filled so far.

    /*
        FixedSizeHashMap(int size) constructs a new FixedSizeHashMap restricted
        to a size of the input variable size.

        @param  size    a size to restrict the HashMap to.
    */
    public FixedSizeHashMap(int size){
        maxSize = size;
        mapSize = 0;
        data = new Entry[size < 0 ? 0 : size];
    }

    /*
         calculateIndex(String key) calculates the index / bucket to fetch or
         insert an entry into given the key of the value. It returns a value within
         the [0, n-1] arraySize.

         @param  key    the key of the Entry we are searching / inserting.
    */
    private int calculateIndex(String key){
        return (key.hashCode() >>> (maxSize - 1)) & (maxSize - 1);
    }

    /*
          set(String key, Object value) inserts a value into the HashMap so
          long as there is space for the entry (key,value).

          @param  key    the key of the Entry we are searching / inserting.
          @param  value  the value of the entry mentioned above
    */
    public boolean set(String key, Object value) {
        int index = calculateIndex(key); // calculate the bucket index for the given key
        if (maxSize == 0 || index > maxSize) return false; // check if there is no space or index OOR

        for (Entry entry = data[index]; entry != null; entry = entry.next){
            if (entry.keyMatch(key)) {
                entry.v = value;
                return true;
            }
        }

        if (mapSize == maxSize) {
            return false; // return false if we are at capacity
        }

        Entry e = new Entry(key, value, data[index]);
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
        if (index > maxSize) return null;

        if (maxSize == 0 || data[index] == null)
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
        if (data[index].keyMatch(key)) { // check if the first value matches the key we're looking for
            Object val = data[index].v;
            data[index] = data[index].next;
            mapSize -= 1;
            return val;
        }
        // If the first value is not the key we are looking for, we iterate through all nodes until we find it or
        // return null if it's not available.
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
        if (maxSize == 0) return 0;
        // convert mapSize to float and conduct float based division rather than integer division
        return (mapSize * 1.0f) / maxSize;
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
        for (int i = 0; i < maxSize; i++) {
            if (data[i] != null) { // iterate and check if data[i] contains any values
                System.out.print("HashMap Bucket " + i + ": ");
                Entry e = data[i];
                while (e != null) { // iterate across all Entries in the linked list for a given HashMap bucket.
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