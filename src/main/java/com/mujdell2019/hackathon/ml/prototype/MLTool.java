import java.util.*;

class ScoredProduct implements Comparable<ScoredProduct>{
    private String id;
    private double score;
    private boolean isSimilarRanked;
    ScoredProduct(String id, double score, boolean isSimilarRanked) {
        this.id = id;
        this.score = score;
        this.isSimilarRanked = !isSimilarRanked;
    }

    protected String getID() { return id; }
    protected double getScore() { return score; }

    @Override public int compareTo(ScoredProduct otherProduct) {
        int diff = (int) (this.getScore()*1000 - otherProduct.getScore()*1000);
        return isSimilarRanked ? -diff : diff;
    }
}

class MLTool {

    /*
        Method: featureRankedList
        returns a list of productIDs that are most similar to a given feature-vector
        based on exactSimilarity

        @param List<Integer> key: target feature vector
        @param HashMap<String, List<Integer>> dataset: (PRODUCT_ID, FEATURE_VECTOR) map
        @param int count: number of recommendations required
        @param boolean isSimilarRanked: true if similar recommendations are needed
    */
    public List<String> featureRankedList(
        List<Integer> key,
        HashMap<String, List<Integer>> dataset,
        int count,
        boolean isSimilarRanked
    ) {
        ArrayList<ScoredProduct> orderedList = new ArrayList<ScoredProduct>();
        ArrayList<String> res = new ArrayList<String>();

        Iterator datasetIterator = dataset.entrySet().iterator();
        while(datasetIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) datasetIterator.next();
            String pid = (String) pair.getKey();
            List<Integer> list = (List<Integer>) pair.getValue();
            double score = exactSimilarity(key, list);
            ScoredProduct scoredProduct = new ScoredProduct(pid, score, isSimilarRanked);
            orderedList.add(scoredProduct);
        }

        Collections.sort(orderedList);
        for(int i=orderedList.size()-1, c=0; i>=0 && c<count;i--, c++)
            res.add(orderedList.get(i).getID());
        
        return res;
    }

    /*
        Method: similarToProduct
        returns list of products that are most similar to the product
        with given productID

        @param String key: productID
        @param HashMap<String, List<String>> dataset: source dataset
        @param int count: number of recommendations expected
        @param boolean isSimilarRanked: true if similar recommendations are needed
        @return List<String>: list of product IDs
    */
    public List<String> similarToProduct(
        String key,
        HashMap<String, List<Integer>> dataset,
        int count,
        boolean isSimilarRanked
    ) {
        List<Integer> featureList = dataset.get(key);
        return featureRankedList(featureList, dataset, count, isSimilarRanked);
    }

    /*
        Method: contentFilteredList
        returns a list of product-IDs that are most similar
        to a given list of products using Cosine Similarity Ranking

        @param List<Integer> key
        @param HaspMap<String, List<Integer>> dataset:
                dataset of dell laptops; productID-featureList Map
        @param int count : the number of similar lists to be returned
        @param boolean isSimilarRanked: true if similar recommendations are needed
        @return List<String>: list of product IDs
    */
    public List<String> cosineFilteredList(
        List<Integer> key,
        HashMap<String, List<Integer>> dataset,
        int count,
        boolean isSimilarRanked
    ) {
        ArrayList<ScoredProduct> orderedList = new ArrayList<ScoredProduct>();
        ArrayList<String> res = new ArrayList<String>();

        Iterator datasetIterator = dataset.entrySet().iterator();
        while(datasetIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) datasetIterator.next();
            String pid = (String) pair.getKey();
            List<Integer> list = (List<Integer>) pair.getValue();
            double score = cosineSimilarity(key, list);
            ScoredProduct scoredProduct = new ScoredProduct(pid, score, isSimilarRanked);
            orderedList.add(scoredProduct);
        }

        Collections.sort(orderedList);
        for(int i=orderedList.size()-1, c=0; i>=0 && c<count;i--, c++)
            res.add(orderedList.get(i).getID());
        
        return res;
    }

    /*
        Method: cosineSimilarity
        returns cosine similarity between 2 vectors passed.
        if vectors are of unequal lengths, minimum length is assumed

        @param List<Integer> list1: 1st vector
        @param List<Integer> list2: 2nd vector
        @return double: cosineSimilarity
    */
    private double cosineSimilarity(List<Integer> list1, List<Integer> list2) {
        int minLen = (int)Math.min(list1.size(), list2.size());
        double res = 0;
        double list1Abs = 0, list2Abs = 0;
        int v1, v2;
        for(int i=0; i<minLen; i++) {
            v1 = list1.get(i);
            v2 = list2.get(i);
            res += v1*v2;
            list1Abs += v1*v1;
            list2Abs += v2*v2;            
        }

        list1Abs = Math.sqrt(list1Abs);
        list2Abs = Math.sqrt(list2Abs);
        res = res / (list1Abs*list2Abs);

        return res;
    }

    /*
        Method: collaborativeFilteredList
        returns a list of product-IDs that are most similar
        to a given productID using Pearson Cofficient Ranking;
        returns null if given productID was not found

        @param String productID
        @param HashMap<String, List<Integer>> frequencyMap
                Here, key is anyonymous userID;
                value is the related viewCount vector of dell products
        @param int count: number of recommendations to be returned
        @param boolean isSimilarRanked: true if similar products are needed, otherwise false
        @return List<String> : list of product IDs
    */
    public List<String> collaborativeFilteredList(
        String productID,
        HashMap<String, HashMap<String, Integer>> dataset,
        int count,
        boolean isSimilarRanked
    ) { 

        ArrayList<ScoredProduct> orderedList = new ArrayList<>();
        ArrayList<String> res = new ArrayList<>();

        HashMap<String, Integer> keyVector = dataset.get(productID);
        Iterator datasetIterator = dataset.entrySet().iterator();

        while(datasetIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) datasetIterator.next();
            String pid = (String) pair.getKey();

            if(pid.equals(productID)) continue;

            HashMap<String, Integer> vector = (HashMap) pair.getValue();
            double score = pearsonCofficient(keyVector, vector);
            ScoredProduct scoredProduct = new ScoredProduct(pid, score, isSimilarRanked);
            orderedList.add(scoredProduct);
        }

        Collections.sort(orderedList);
        for(int i=orderedList.size()-1, c=0;i>=0 && c<count;i--, c++)
            res.add(orderedList.get(i).getID());

        return res;
    }

    /*
        Method: pearsonCofficient
        returns pearson cofficient between given 2 vectors;
        assumes vector of equal length

        @param HashMap<String, Integer> vector1: userid-response
        @param HashMap<String, Integer> vector2: userid-response
        @return double: pearson cofficient
    */
    private double pearsonCofficient(HashMap<String, Integer> vector1, HashMap<String, Integer> vector2) {
        double sum1 = 0, sum2 = 0, n = 0,
               mean1, mean2,
               covXY=0, stdDevX=0, stdDevY=0;

        Iterator vec1Iterator = vector1.entrySet().iterator();
        while(vec1Iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) vec1Iterator.next();
            String key = (String) pair.getKey();
            int x = (Integer) pair.getValue();
            
            sum1 += x;
            sum2 += vector2.get(key);
            n++;
        }

        mean1 = sum1 / n;
        mean2 = sum2 / n;

        vec1Iterator = vector1.entrySet().iterator();
        while(vec1Iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) vec1Iterator.next();
            String key = (String) pair.getKey();
            int x = (Integer) pair.getValue();
            int y = vector2.get(key);

            double a = x-mean1;
            double b = y-mean2;
            covXY += a*b;
            stdDevX += a*a;
            stdDevY += b*b;
        }
        if(stdDevX==0 || stdDevY==0)
            return -1;
        return covXY / Math.sqrt(stdDevX*stdDevY) ;
    }

    /*
        Method: exactSimiarity
        returns count of matched values between 2 vectors
        if vectors are of unequal length, minimum length is assumed

        @param List<Integer> list1: vector1
        @param List<Integer> list2: vector2
        @return int: count of matched values
    */
    private int exactSimilarity(List<Integer> list1, List<Integer> list2) {

        int minLen = (int) Math.min(list1.size(), list2.size());
        int score = 0;
        for(int i=0;i<minLen;i++) {
            int v1 = list1.get(i);
            int v2 = list2.get(i);
            if(v1==v2) score++;
        }
        return score;
    }

    private double mean(List<Integer> list) {
        double sum = 0;
        for(Integer val : list) sum += val;
        return sum / list.size();
    }
}