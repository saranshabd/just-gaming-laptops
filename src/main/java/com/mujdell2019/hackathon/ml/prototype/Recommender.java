import java.util.*;
import java.io.*;

class Recommender {

    private HashMap<String, List<Integer>> hashedDellDataset;
    private TreeSet<String> productIDSet;
    private Query query;
    private MLTool mlTool;

    private HashMap<String, HashMap<String, Integer>> searchDataset;
    
    private HashMap<String, HashMap<String, Integer>> buyDataset;

    Recommender() {
        HashMap<String, List<String>> featureMap = fetchFeatureMap();
        String[] x = new String[] {"cpu", "gpu", "ram_type", "ram_size", "storage", "display_size", "display_type", "display_fps", "name", "price"};
        ArrayList<String> attributeList = new ArrayList<>(Arrays.asList(x));

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
        this.query = new Query(attributeList, featureMap, 0, 20000, 360000);
        HashMap<String, String> f = new HashMap<>();

        
        // Load hashed dataset
        this.hashedDellDataset = loadHashedDellDataset(attributeList);

        // Initialise ML Object
        this.mlTool = new MLTool();

        // Load search dataset
        this.searchDataset = loadSearchDataset();

        // Load buy dataset
        // this.buyDataset = loadBuyDataset();
    }

    // TODO return reverse list
    public List<String> searchRecommendations(String searchQuery, HashMap<String, String> extraFilter, int count) {
        List<Integer> hashedQuery = query.parseQuery(searchQuery, extraFilter);
        return mlTool.featureRankedList(
            hashedQuery,
            this.hashedDellDataset,
            count,
            true
        );
    }

    public List<String> generalRecommendations(List<String> cartAdded, List<String> cartDeleted, List<String> itemClicked, int count) {
        List<String> posList = new ArrayList<String>(itemClicked);
        posList.addAll(cartAdded);

        int minIterations = (int) Math.min(3, posList.size());
        List<String>[] recommended = new ArrayList[minIterations];
        for(int i=0 ; i<minIterations ; i++) 
            recommended[i] = mlTool.collaborativeFilteredList(
                posList.get(i),
                this.searchDataset,
                40,
                true
            );
        List<String> res = new ArrayList<String>();
        String pid;
        boolean emptyFlag;
        for(int i=0, f=0; i<count; i++, f=(f+1)%minIterations) {
            emptyFlag = true;
            for(int j=0;j<minIterations && emptyFlag;j++)
                emptyFlag = recommended[j].size() == 0;
            if(emptyFlag) 
                break;
            
            if(recommended[f].size()==0) {
                i--;
                continue;
            }
            pid = recommended[f].get(0);
            if(cartDeleted.contains(pid) || res.contains(pid)) {
                i--;
                recommended[f].remove(0);
                continue;
            }

            res.add(pid);
        }

        return res;        
    }

    public List<String> lastBuyRecommendations(String productID, int count) {
        return mlTool.collaborativeFilteredList(
            productID,
            this.buyDataset,
            count,
            true
        );
    }

    // returns productID -> (userID-Integer) map
    private HashMap<String, HashMap<String, Integer>> loadSearchDataset() {
        
        HashMap<String, HashMap<String, Integer>> res = new HashMap<>();
        for(String pid : productIDSet)
            res.put(pid, new HashMap<String, Integer>());
        
        TreeSet<String> uidSet = new TreeSet<>();

        // TODO from DATABASE
        String filename = "user_searchhistory.csv";
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(filename));
            br.readLine();

            while( (line=br.readLine()) != null ) {
                String[] token = line.split(",");
                String pid = token[0];
                String uid = token[1];

                uidSet.add(uid);

                HashMap<String, Integer> fmap = res.get(pid);
                if(!fmap.containsKey(uid))
                    fmap.put(uid, 1);
                else
                    fmap.put(uid, fmap.get(uid)+1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        for(String pid : productIDSet) {
            HashMap<String, Integer> fmap = res.get(pid);

            for(String uid : uidSet)
                if(!fmap.containsKey(uid))
                    fmap.put(uid, 0);
        }

        return res;
    }

    private HashMap<String, List<Integer>> loadHashedDellDataset(ArrayList<String> attributeList) {

        // TODO load from database
        // TODO create hashedDellDataset

        String filename = "dell_product_new.csv";
        BufferedReader br = null;
        String line = "";
        HashMap<String, List<Integer>> res = new HashMap<>();

        this.productIDSet = new TreeSet<String>();

        try {
            br = new BufferedReader(new FileReader(filename));
            br.readLine();
            boolean[] ignoreIndex = new boolean[13];
            ignoreIndex[0] = true;
            ignoreIndex[10] = true;
            ignoreIndex[12] = true;
            while((line=br.readLine()) != null) {
                String[] token = line.split(",");
                HashMap<String, String> productAttr = new HashMap<>();
                Iterator attrIterator = attributeList.iterator();
                for(int i=0;i<token.length;i++) {
                    if(ignoreIndex[i]) continue;
                    String attrName = (String) attrIterator.next();
                    productAttr.put(attrName.trim().toLowerCase(), token[i].trim().toLowerCase());
                }
                List<Integer> hashedAttr = this.query.parseQuery("UNDEFINED", productAttr);
                res.put(token[0], hashedAttr);
                this.productIDSet.add(token[0]);
            }          
        } catch(Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    private HashMap<String, List<String>> fetchFeatureMap() {

        // TODO from database

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