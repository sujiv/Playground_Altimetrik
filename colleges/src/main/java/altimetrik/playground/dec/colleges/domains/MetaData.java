package altimetrik.playground.dec.colleges.domains;

import lombok.Data;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
//"metadata":{"total":3344,"page":0,"perPage":1}
public class MetaData{//<T extends Object>{
    private int page;
    private int perPage;
    private int total;

//    Map<String, Class>  fieldTypes = new HashMap<>();
//    Map<String, Object> fieldValues = new HashMap<>();
//
//    {
//        fieldTypes.put("page",Integer.class);
//        fieldTypes.put("perPage",Integer.class);
//        fieldTypes.put("total",Integer.class);
//    }
//
//    public T getVal(String field){
//        if(fieldValues.containsKey(field)){
//            return (T)(fieldValues.get(field));
//        }
//        return null;
//    }
//
//    public void setVal(String field, Object value){
//        if(fieldTypes.containsKey(field)){
//            fieldValues.put(field,value);
//        }
//    }
}
