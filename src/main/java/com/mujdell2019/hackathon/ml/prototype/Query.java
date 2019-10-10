
import java.util.*;

class Query {

    private HashMap<String, HashMap<String, Integer>> attrMap;
    private List<String> attrList;
    private int priceHashStart, priceRangeSize, priceStart, priceEnd;
    private List<String> parseOrder;
    /*
        Constructor: QueryMap 
        @param: HashMap<String, List<String>> valueMap
                expects a HashMap denoting attribute name as string
                mapped to a list of possible values of String type
    */
    public Query(List<String> attrList, HashMap<String, List<String>> valueMap,
                 int priceStart, int priceRangeSize, int priceEnd
                ) {
        
        // calculate maxIntervalSize of hashValue of attribute-value pair
        int maxInterval = 0;
        Iterator valueMapIterator = valueMap.entrySet().iterator();
        while(valueMapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry)valueMapIterator.next();
            maxInterval = ((List<String>)pair.getValue()).size();
        }
        maxInterval += 100;

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
                priceHashStart = relativeIndex + 8;
            }

            this.attrMap.put(attr, valueIntegerMap);
        }

        this.attrList = attrList;

        this.priceStart = priceStart;
        this.priceRangeSize = priceRangeSize;
        this.priceEnd = priceEnd;
    }
    
    /*
        Method: parseQuery
        returns a list of predefined integers that represent a query
        returns an empty list if no mapping was found

        @param String queryString: the string that was queried by user
        @param HashMap<String, String> extraFilter: additional filters added by user 
        @return List<Integer>: list of hashvalues of attributes
    */
    public List<Integer> parseQuery(String queryString, HashMap<String, String> extraFilter) {
        queryString = queryString.trim().toLowerCase();
        String[] query = queryString.split(" ");

        ArrayList<Integer> hashList = new ArrayList<Integer>();

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
                    
                    hashList.add( getHash(attr, possibleValue) );
                    found = true;
                }
            }

            if(!found) {
                if(extraFilter.containsKey(attr))
                    hashList.add( getHash(attr, extraFilter.get(attr) ));
                else
                    hashList.add(0);
            }
        }

        if(extraFilter.containsKey("price"))
            hashList.add( getHash("price", extraFilter.get("price")) );
        else
            hashList.add(0);

        return hashList;
    }

    /*
       Method: getHash
       returns a unique predefined integer that is mapped to attribute-value pair.
       returns 0 if pair was not found.
       For the attribute, hash is calculated dynamically; does not use pre-defined value

       @param String attr
       @param String val
       @param int: hashvalue
    */
    private int getHash(String attr, String val) {
        if(attr.equals("price")) {
            int priceVal = Integer.parseInt(val);
            for(int hash=priceHashStart, price=priceStart; 
                price+priceRangeSize<=priceEnd ; 
                price+=priceRangeSize, hash++) {
                    if(price <= priceVal && priceVal <= price+priceRangeSize)
                        return hash;
            }

            return 0;
        }

        if(!attrMap.containsKey(attr)) return 0;  // if attribute was not found
    
        HashMap<String, Integer> valueMap = attrMap.get(attr);
        Iterator valueMapIterator = valueMap.entrySet().iterator();
        while(valueMapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) valueMapIterator.next();
            String possibleValue = (String) pair.getKey();
            if(possibleValue.indexOf(val) != -1)
                return (Integer) pair.getValue();
        }
        return 0;
        
        // if(!valueMap.containsKey(val)) return 0; // if value was not found

        // return valueMap.get(val);
    }

    /*  Method: deleteStringAtIndex
        deletes string at specified index in given string array
        in given range and returns updated array size.
        It does not actually delete from elment from memory,
        rather it left shifts the elements following the specified index

        @param String[] arr: source array to search in
        @param int len: original length of array
        @param int index: index of element to be deleted
        @return int: new array length
    */

    private int deleteStringAtIndex(String[] arr, int len, int index) {
        if(index < 0)
            return len;
        if(index == len-1)
            return len-1;
        for(int i=index;i<len-1;i++)
            arr[i] = arr[i+1];

        return len-1;
    }

    /*
        Method: searchValue
        returns first index of array found, such that key is a substring of arr[index]
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
}