package com.mujdell2019.hackathon.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class QueryUtil {

	private HashMap<String, HashMap<String, Integer>> attrMap;
    private List<String> attrList;
    
    /*
        Constructor: QueryMap 
        @param: HashMap<String, List<String>> valueMap
                expects a HashMap denoting attribute name as string
                mapped to a list of possible values of String type
    */
    public QueryUtil() {
        HashMap<String, List<String>> valueMap = fetchFeatureMap();

        // calculate maxIntervalSize of hashValue of attribute-value pair
        int maxInterval = 0;
        Iterator valueMapIterator = valueMap.entrySet().iterator();
        while(valueMapIterator.hasNext()) {
            Map.Entry pair = (Map.Entry) valueMapIterator.next();
            maxInterval = ((List<String>) pair.getValue()).size();
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
            // price will be received as extra filters from application during search
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
        }

        if(extraFilter.containsKey("price"))
            result.put( "price", extraFilter.get("price") );

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

        String featureList[][] = new String[][] {
            { "display_type", "fhd", "qhd", "uhd", "oled" },
            { "ram_size", "8", "16", "32", "64" },
            { "price", "107019", "122664", "174218", "170663", "284083", "262750", "302927", "150041", "120886", "195551", "273772", "159996", "137241", "156441", "178399", "379015", "222573", "305772", "233240", "167818", "99553", "67482", "99482", "126503", "83126", "72460", "100904", "122237", "81704" },
            { "ram_type", "ddr4" },
            { "display_size", "17.3", "15.6" },
            { "name", "alienware m17 gaming laptop", "alienware m15 gaming laptop", "new alienware m17 gaming laptop", "alienware area-51m gaming laptop", "new alienware m15 gaming laptop", "new dell g3 15 gaming laptop", "dell g7 15 gaming laptop", "dell g7 17 gaming laptop", "dell g5 15 se gaming laptop", "dell g5 15 gaming laptop" },
            { "cpu", "9th generation intel core i7-9750h", "8th generation intel core i7-8750h", "8th generation intel core i7-8750h", "8th generation intel core i9-8950hk", "9th generation intel core i9-9980hk", "9th generation intel core i7-9700", "9th generation intel core i9-9900k", "9th generation intel core i7-9700k", "9th generation intel core i9-9900", "9th generation intel core i5-9300h" },
            { "storage", "1000", "256", "512", "4000", "2000", "128", "1000", "2000", "4000" },
            { "gpu", "nvidia geforce rtx 2060 6gb gddr6 (oc ready)", "nvidia geforce rtx 2070 8gb gddr6 with max-q design", "nvidia geforce rtx 2080 8gb gddr6 with max-q design", "nvidia geforce gtx 1660 ti 6gb gddr6", "nvidia geforce rtx 2080 8gb gddr6 (oc ready)", "nvidia geforce rtx 2070 8gb gddr6 (oc ready)", "nvidia geforce gtx 1650 4gb gddr5", "nvidia geforce rtx 2060 6gb gddr6", "nvidia geforce gtx 1660 ti with max-q design 6gb gddr6" },
            { "display_fps", "60", "120", "144", "240", "2160", "1080" },
        };


        HashMap<String, List<String>> valueMap = new HashMap<>();
        for(String[] attrList : featureList) {
            ArrayList<String> valueList = new ArrayList<>();
            for(int i=1;i<attrList.length;i++)
                valueList.add(attrList[i]);
            valueMap.put(attrList[0], valueList);            
        }

        return valueMap;
    }
}
