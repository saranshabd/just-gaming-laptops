import java.io.*;
import java.util.*;

class Query {

    private HashMap<String, HashMap<String, Integer>> attrMap;
    private List<String> attrList;
    /*
        Constructor: QueryMap 
        @param: HashMap<String, List<String>> valueMap
                expects a HashMap denoting attribute name as string
                mapped to a list of possible values of String type
    */
    public Query() {
        HashMap<String, List<String>> valueMap = fetchFeatureMap();

        // calculate maxIntervalSize of hashValue of attribute-value pair
        int maxInterval = 0;
        Iterator valueMapIterator = valueMap.entrySet().iterator();
        while(valueMapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry)valueMapIterator.next();
            maxInterval = ((List<String>)pair.getValue()).size();
        }
        maxInterval += 100;
        
        String[] attributeArr = new String[] {"cpu", "gpu", "ram_type", "ram_size", "storage", "display_size", "display_type", "display_fps", "name", "price"};
        List<String> attrList = new ArrayList<String>(Arrays.asList(attributeArr));

        // memorize hashValue of each attribute-value pair
        this.attrMap = new HashMap<String, HashMap<String, Integer>>();
        for(int intervalStart = 11, attrIndex=0; attrIndex<attrList.size() ; intervalStart += maxInterval, attrIndex++) {
            String attr = (String) attrList.get(attrIndex);
            attr = attr.trim().toLowerCase();
            
            // price hash is calculated dynamically based on range
            // price will be recieved as extrafilters from app during search
            if(attr.equals("price"))
                continue;

            List<String> valueList = valueMap.get(attr);
            HashMap valueIntegerMap = new HashMap<String, Integer>();
            for(int relativeIndex = intervalStart, listIndex = 0; listIndex < valueList.size(); relativeIndex+=7, listIndex++) {
                String value = valueList.get(listIndex);
                valueIntegerMap.put(value, relativeIndex);
            }

            this.attrMap.put(attr, valueIntegerMap);
        }

        this.attrList = attrList;
    }
    
    /*
        Method: parseQuery
        returns map of attribute-value pair by searching a set of predefined values
        in the given string

        returns an map if no mapping was found

        @param String queryString: the string that was queried by user
        @param HashMap<String, String> extraFilter: additional filters added by user 
        @return HashMap<String, String>: attribute-value pair map
    */
    public HashMap<String, String> parseQuery(String queryString, HashMap<String, String> extraFilter) {
        queryString = queryString.trim().toLowerCase();
        String[] query = queryString.split(" ");

        HashMap<String, String> result = new HashMap<>();

        for(int attrIndex=0; attrIndex<attrList.size(); attrIndex++) {
            String attr = attrList.get(attrIndex);
            HashMap<String, Integer> valueMap = attrMap.get(attr);
            boolean found = false;
            
            if(!attr.equals("price")) {
                Iterator valueMapIterator = valueMap.entrySet().iterator();
                while(valueMapIterator.hasNext() && !found) {
                    Map.Entry valuePair = (Map.Entry) valueMapIterator.next();
                    String possibleValue = (String) valuePair.getKey();
                    int valueIndex = searchValue(query, 0, query.length, possibleValue);
                    
                    if(valueIndex == -1) continue;
                    
                    result.put(attr, possibleValue);
                    found = true;
                }
            }

            if(!found) 
                if(extraFilter.containsKey(attr))
                    result.put(attr, extraFilter.get(attr));
                else
                    result.put(attr, null);
        }

        if(extraFilter.containsKey("price"))
            result.put( "price", extraFilter.get("price") );
        else
            result.put("price", null);

        return result;
    }


    /*
        Method: searchValue
        returns first index of array found, such that arr[index] is a substring of key
        returns -1 if no matches were found

        @param String[] arr : query array
        @param int start    : index from where the search starts(inclusive)
        @param int end      : index where the search ends(exclusive)
        @param String key   : search element
        @return index: search result
    */
    private int searchValue(String[] arr, int start, int end, String key) {
        for(int i=start;i<end;i++) {
            if(key.indexOf(arr[i]) != -1)
                return i;
        }
        return -1;
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