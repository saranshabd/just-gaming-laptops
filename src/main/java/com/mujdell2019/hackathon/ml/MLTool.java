import java.util.*;

class MLTool {

    class ScoredList implements Comparator<ScoredList>{
        private List<Integer> list;
        private double score;
        ScoredList(List<Integer> list, double score) {
            this.list = list;
            this.score = score;
        }

        protected List<Integer> getList() { return list; }
        protected double getScore() { return score; }

        @Override public int compare(ScoredList x, ScoredList y) {
            if(x.getScore() == y.getScore()) return 0;
            if(x.getScore() < y.getScore()) return -1;
            return 1;
        }
    }
   
    /*
        Method: cosineSimilarList
        returns a list of integer lists that are most similar
        to a given integer list using Cosine Similarity Ranking

        @param List<Integer> key
        @param List<List<Integer>> dataset
        int count : the number of similar lists expected
    */
    public List<List<Integer>> cosineSimilarList(
        List<Integer> key,
        List<List<Integer>> dataset,
        int count
    ) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        PriorityQueue<ScoredList> q = new PriorityQueue<>(count);
        
        Iterator datasetIterator = dataset.iterator();
        while(datasetIterator.hasNext()) {
            List<Integer> list = (List<Integer>) datasetIterator.next();
            double score = cosineSimilarity(key, list);
            q.add( new ScoredList(list, score) );
            if(q.size() > count)
                q.poll();
        }

        while(q.size()>0) {
            ScoredList scoredList = (ScoredList) q.poll();
            res.add(scoredList.getList());
        }

        return res;
    }

    public double cosineSimilarity(List<Integer> list1, List<Integer> list2) {
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


}