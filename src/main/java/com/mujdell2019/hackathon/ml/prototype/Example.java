import java.util.*;

class Example {
    public static void main(String[] args) {

        Query query = new Query();
        String searchString = "3gb ram i9";
        HashMap<String, String> extraFilter = new HashMap<>();

        HashMap<String, String> res =  query.parseQuery(searchString, extraFilter);
        System.out.println(res);
    }
}