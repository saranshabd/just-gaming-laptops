import java.util.*;
import java.io.*;

class Recommender {

    private HashMap<String, List<Integer>> hashedDellDataset;
    private Query query;
    private MLTool mlTool;

    Recommender() {
        HashMap<String, List<String>> featureMap = fetchFeatureMap();
        ArrayList<String> attrList2 = new ArrayList<>();
        String[] x = new String[] {"cpu", "gpu", "ram_type", "ram_size", "storage", "display_size", "display_type", "display_fps", "name", "price"};
        for(String str : x) attrList2.add(str);

        // Create hash values for features 
        Query q = new Query(attrList2, featureMap, 0, 20000, 360000);

        // Create hashed feature dataset
        // HashMap<String, Integer>[] dataset = createHashedDataset(q);
        // String hashedOutput = "";
        // for(int i=0;i<dataset.length;i++) {
        //     String set = "";
        //     set = dataset[i].get("index") + ", ";
        //     for(int j=0;j<attrList.size();j++) {
        //         if(ignoreIndex.contains(j)) continue;
        //         set = set + dataset[i].get(attrList.get(j)) + ", ";
        //     }
        //     set = set.substring(0, set.lastIndexOf(","));
        //     hashedOutput += set+"\n";
        // }
        // try { Files.writeString(Paths.get("hashedDell.csv"), hashedOutput); }
        // catch(IOException e) { e.printStackTrace(); }

        // Create hash values for features 
        this.query = new Query(attrList2, featureMap, 0, 20000, 360000);
        
        // Load hashed dataset
        this.hashedDellDataset = loadHashedDellDataset();

        // Initialise ML Object
        this.mlTool = new MLTool();
    }

    public List<String> searchRecommendations(String searchQuery, HashMap<String, String> extraFilter, int count) {
        List<Integer> hashedQuery = query.parseQuery(searchQuery, extraFilter);
        return mlTool.featureRankedList(
            hashedQuery,
            this.hashedDellDataset,
            count,
            true
        );

    }

    HashMap<String, List<Integer>> loadHashedDellDataset() {
        String filename = "hashedDell.csv";
        BufferedReader br = null;
        String line = "";
        HashMap<String, List<Integer>> res = new HashMap<>();

        try {
            br = new BufferedReader(new FileReader(filename));
            while((line=br.readLine()) != null) {
                String[] token = line.split(",");
                ArrayList<Integer> valueList = new ArrayList<>();
                for(int i=1;i<token.length;i++) valueList.add(Integer.parseInt((token[i].trim()).toLowerCase()));

                res.put(token[0], valueList);
            }          
        } catch(Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    HashMap<String, List<String>> fetchFeatureMap() {
        String fileName = "features.csv";
        BufferedReader br = null;
        String line = "";
        HashMap<String, List<String>> valueMap = new HashMap<>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            while((line=br.readLine()) != null) {
                String[] token = line.split(",");
                ArrayList<String> valueList = new ArrayList<>();
                for(int i=1;i<token.length;i++) valueList.add((token[i].trim()).toLowerCase());
                valueMap.put(token[0], valueList);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return valueMap;
    }

}