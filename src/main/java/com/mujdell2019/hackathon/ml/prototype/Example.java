import java.util.*;

class Example {
    public static void main(String[] args) {

        Recommender recommender = new Recommender();
        String search = "ram 64 i9";
        HashMap<String, String> extraFilter = new HashMap<>();
        int count = 40;
        List<String> list = recommender.searchRecommendations(search, extraFilter, count);
        System.out.println(list);

        String[] cartAddedArr = new String[] {"3", "5", "7", "11"};
        String[] clickedArr = new String[] {"5", "11", "20", "39"};
        String[] cartDeletedArr = new String[] { "33", "25", "20", "24"};

        List<String> cartAdded = new ArrayList<>(Arrays.asList(cartAddedArr));
        List<String> clicked = new ArrayList<>(Arrays.asList(clickedArr));
        List<String> cartDeleted = new ArrayList<>(Arrays.asList(cartDeletedArr));
        list = recommender.generalRecommendations(cartAdded, cartDeleted, clicked, count);
        System.out.println(list);
        
    }
}