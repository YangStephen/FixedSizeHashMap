import HashMap.FixedSizeHashMap;

// This import is only for the user to be able to input & Test the implementation of a FixedSizeHashMap
import java.util.Scanner;

/*
    HashMapSimulator contains tests for the FixedSizeHashMap and then a function that run a simulation in which the user
    can enter commands and interact with the HashMap.
*/
public class HashMapSimulator{
    /*
        testSet(FixedSizeHashMap hm) takes in a HashMap reference and applies tests on the set method for the HashMap

        @param  hm  FixedSizeHashMap reference
    */
    public static boolean testSet(FixedSizeHashMap hm) {
        hm.set("A", 1);
        hm.set("B", 2);
        hm.set("C", 3);
        hm.set("D", 4);
        hm.set("E",5);
        hm.set("F", 6);
        if(hm.load() != 1.0)
            return false;
        hm.set("F", 7);
        if ((int)hm.get("F") != 7)
            return false;
        hm.set("G", 8);
        if(hm.load() != 1.0)
            return false;
        return true;
    }
    /*
        testDelete(FixedSizeHashMap hm) takes in a HashMap reference and applies tests on the delete method for the
        HashMap

        @param  hm  FixedSizeHashMap reference
    */
    public static boolean testDelete(FixedSizeHashMap hm) {
        hm.delete("A");
        if(hm.load() == 1.0)
            return false;
        hm.set("A", 2);
        if(hm.load() != 1.0)
            return false;
        if (hm.delete("H") != null)
            return false;
        return true;
    }

    /*
        testGet(FixedSizeHashMap hm) takes in a HashMap reference and applies tests on the get method for the HashMap

        @param  hm  FixedSizeHashMap reference
    */
    public static boolean testGet(FixedSizeHashMap hm) {
        if(hm.get("H") != null)
            return false;
        hm.delete("A");
        if(hm.get("A") != null)
            return false;
        hm.set("A", 1);
        return true;
    }

    /*
        testLoad() applies tests on the load method for the HashMap
    */
    public static boolean testLoad() {
        FixedSizeHashMap hm = new FixedSizeHashMap(2);
        if (hm.load() != 0.0)
            return false;
        hm.set("A", 1);
        if (hm.load() != 0.5)
            return false;
        hm.set("B", 1);
        if (hm.load() != 1.0)
            return false;
        hm.delete("B");
        if (hm.load() != 0.5)
            return false;
        return true;
    }

    /*
        testNegativeSize() applies tests to ensure that the HashMap can deal with negative size inputs from the user.
    */
    public static boolean testNegativeSize() {
        FixedSizeHashMap hm = new FixedSizeHashMap(-2);
        if (hm.load() != 0)
            return false;
        hm.set("A", 1);
        if (hm.get("A") != null)
            return false;
        return true;
    }

    /*
        runAllTests() is a function that executes all the above tests and outputs the status of the tests
    */
    public static void runAllTests() {
        FixedSizeHashMap hm = new FixedSizeHashMap(6);
        System.out.println("RUNNING ALL TESTS:");
        System.out.println(testSet(hm) ? "TestSet Passed" : "TestSet Failed");
        System.out.println(testDelete(hm) ? "TestDelete Passed" : "TestDelete Failed");
        System.out.println(testGet(hm) ? "TestGet Passed" : "TestGet Failed");
        System.out.println(testLoad() ? "TestLoad Passed" : "TestLoad Failed");
        System.out.println(testNegativeSize() ? "TestNegativeSize Passed" : "TestNegativeSize Failed");
        System.out.println("-------------------\n");
    }

    /*
        simulateHM() is a function that simulates the HashMap as if the commands are equivalent to a user coding up a
        usage of the HashMap. The input would just have to be one of, SET, GET, DELETE, PRINT, LOAD or RESIZE and
        it will execute those commands on the given HashMap hm instance. By using Print, the user can print out the
        Buckets as well as the objects inside of those buckets.
    */
    public static void simulateHM() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the HashMap size (integer value): ");
        int size = sc.nextInt();
        FixedSizeHashMap hm = new FixedSizeHashMap(size);
        if (size < 0) System.out.println("The FixedSize entered is negative, we are initializing to 0 by default\n");
        System.out.println("This function is executable and a live demo of the use of the FixedSizeHashMap.");
        System.out.println("The Following are the commands that can be used, please match the format exactly");
        System.out.println("'SET $KEY $VALUE' Creates or overwrites the value associated with a given key");
        System.out.println("'GET $KEY' fetches the value available for a given key or outputs an error message");
        System.out.println("'DELETE $KEY' Deletes the value at a given key");
        System.out.println("'PRINT' Prints out the available buckets and entries in those buckets in the HashMap");
        System.out.println("'LOAD' Prints the current load of the HashMap");
        System.out.println("'RESIZE $newSize' This will resize the HashMap to a new blank one of size $newSize");
        while (sc.hasNext()){
            String entry = sc.next();
            String key;
            switch(entry){
                case "SET":
                    key = sc.next();
                    Object obj = sc.next();
                    if (hm.set(key, obj)) {
                        System.out.println("Successful entry: (" + key + ","+obj+")");
                    } else {
                        System.out.println("Failed Entry");
                    }
                    break;
                case "GET":
                    key = sc.next();
                    Object retrievedValue = hm.get(key);
                    if (retrievedValue == null) {
                        System.out.println("No Available Value for that key");
                    } else {
                        System.out.println(key+" has value of " + retrievedValue);
                    }
                    break;
                case "DELETE":
                    key = sc.next();
                    if (hm.delete(key) == null) {
                        System.out.println("No value matching that key to remove");
                    } else {
                        System.out.println(key + " removed");
                    }
                    break;
                case "LOAD":
                    System.out.println("LOAD: " + hm.load());
                    break;
                case "RESIZE":
                    int newSize = sc.nextInt();
                    hm = new FixedSizeHashMap(newSize);
                    System.out.println("Reinitialized with blank HashMap of size " + newSize);
                    break;
                case "PRINT":
                    hm.print();
                    break;
                default:
                    System.out.println("Not a valid Entry");
                    break;
            }
        }
    }

    /*
        main(String[] args) is the main function for the HashMapSimulator class and executes all tests before simulating
        the HashMap usage.
     */
    public static void main(String[] args){
        runAllTests();
        simulateHM();
    }
}