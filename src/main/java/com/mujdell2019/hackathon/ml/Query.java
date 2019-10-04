import java.util.*;

class Query {

    private HashMap<String, HashMap<String, Integer>> attrMap;

    /*
        Constructor: QueryMap 
        @param: HashMap<String, List<String>> valueMap
                expects a HashMap denoting attribute name as string
                mapped to a list of possible values of String type
    */
    public Query(HashMap<String, List<String>> valueMap) {
        
        // calculate maxIntervalSize of hashValue of attribute-value pair
        int maxInterval = 0;
        Iterator valueMapIterator = valueMap.entrySet().iterator();
        while(valueMapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry)valueMapIterator.next();
            maxInterval = ((List<String>)pair.getValue()).size();
        }
        maxInterval += 7;

        // memorize hashValue of each attribute-value pair
        valueMapIterator = valueMap.entrySet().iterator();
        attrMap = new HashMap<String, HashMap<String, Integer>>();
        for(int intervalStart = 8; valueMapIterator.hasNext(); intervalStart += maxInterval) {
            Map.Entry pair = (Map.Entry) valueMapIterator.next();
            String attr = (String) pair.getKey();
            List<String> valueList = (List<String>)pair.getValue();

            HashMap valueIntegerMap = new HashMap<String, Integer>();
            for(int relativeIndex = intervalStart, listIndex = 0; listIndex < valueList.size(); relativeIndex++, listIndex++) {
                String value = valueList.get(listIndex);
                valueIntegerMap.put(value, relativeIndex);
            }

            attrMap.put(attr, valueIntegerMap);
        }
    }

    /*
        Method: queryToHashList
        returns a list of predefined integers that represent a query
        returns an empty list if no mapping was found

        @param String queryString: the string that was queried by user
    */
    public List<Integer> queryToHashList(String queryString) {
        String[] query = queryString.split(" ");
        ArrayList<Integer> hashList = new ArrayList<Integer>();
        Iterator attrMapIterator = attrMap.entrySet().iterator();
        int tokenCount = query.length;

        while(attrMapIterator.hasNext() && tokenCount>0) {
            Map.Entry pair = (Map.Entry) attrMapIterator.next();
            String attr = (String) pair.getKey();
            HashMap<String, Integer> valueMap = (HashMap<String, Integer>) pair.getKey();
            
            Iterator valueMapIterator = valueMap.entrySet().iterator();
            boolean found = false;
            while(valueMapIterator.hasNext() && !found) {
                Map.Entry valuePair = (Map.Entry) valueMapIterator.next();
                String possibleValue = (String) valuePair.getKey();
                int valueIndex = searchValue(query, 0, tokenCount, possibleValue);
                if(valueIndex == -1) continue;
                tokenCount = deleteStringAtIndex(query, tokenCount, valueIndex);
                hashList.add( getHash(attr, possibleValue) );
                found = true;
            }

            if(!found)
                hashList.add(0);
        }

        return hashList;
    }

    /*
       Method: getHash
       returns a unique predefined integer that is mapped to attribute-value pair.
       returns 0 if pair was not found

       @param String attr
       @param String val
    */
    private int getHash(String attr, String val) {
        if(!attrMap.containsKey(attr)) return 0;  // if attribute was not found
        
        HashMap<String, Integer> valueMap = attrMap.get(attr);
        if(!valueMap.containsKey(val)) return 0;  // if value was not found
        
        return valueMap.get(val);
    }

    private int deleteStringAtIndex(String[] arr, int len, int index) {
        if(index < 0 || index >= len-1)
            return len;
        for(int i=index;i<len-1;i++)
            arr[i] = arr[i+1];

        return len-1;
    }

    /*
        Method searchValue
        returns first index of array found, such that key is a substring of arr[index]
        returns -1 if no matches were found

        @param String[] arr : query array
        @param int start    : index from where the search starts(inclusive)
        @param int end      : index where the search ends(exclusive)
        @param String key   : search element
    */
    private int searchValue(String[] arr, int start, int end, String key) {
        for(int i=start;i<end;i++) {
            if(arr[i].indexOf(key) != -1)
                return i;
        }
        return -1;
    }
}