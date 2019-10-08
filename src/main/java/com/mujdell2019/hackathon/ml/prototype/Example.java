import java.util.*;

class Example {
    public static void main(String[] args) {

        Recommender recommender = new Recommender();
        String search = "ram 32gb i9";
        HashMap<String, String> extraFilter = new HashMap<>();
        int count = 5;
        List<String> list = recommender.searchRecommendations(search, extraFilter, count);
        System.out.println(list);


        
    }
}