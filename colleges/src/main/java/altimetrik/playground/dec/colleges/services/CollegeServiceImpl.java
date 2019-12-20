package altimetrik.playground.dec.colleges.services;

import altimetrik.playground.dec.colleges.domains.MetaData;
import altimetrik.playground.dec.colleges.domains.ResponseData;
import altimetrik.playground.dec.colleges.domains.Result;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    RestTemplate restTemplate;

    //API data
    private String baseUrl = "https://api.data.gov/ed/collegescorecard/v1/schools.json";
    private String API_Key = "api_key=ZD0xcnjzCKdOdtwQMtln7ocw4ec6LMVYfgwMdJv6";

    @Override
    public ResponseData getPaginatedResult(Map<String,String> queryParam, int page, int perPage) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("api_key","ZD0xcnjzCKdOdtwQMtln7ocw4ec6LMVYfgwMdJv6");
        HttpEntity<?> httpEntity = new HttpEntity<Object>("",httpHeaders);

        String url = baseUrl;
        String fields = "&fields=id,school.name,"+queryParam.get("year")+".student.size";
        if(queryParam.size()>0) {
            String criteria = "";
            for (Map.Entry<String, String> s : queryParam.entrySet()) {
                if(s.getKey().equalsIgnoreCase("year")){
                    continue;
                }
                criteria = criteria + "&" + s.getKey() + "=" + s.getValue();
            }

            url = url+"?"+criteria.substring(1);
            url = url+fields;
        }

        url = url+"&page="+page+"&per_page="+perPage+"&"+API_Key;
        System.out.println("API url:"+url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity,String.class);
        if(response!=null){
            try {
                JSONObject json = new JSONObject(response.getBody());
                ResponseData responseData = new ResponseData();
                MetaData metaData = new MetaData();
                JSONObject metaJson = json.getJSONObject("metadata");
                metaData.setPage(metaJson.getInt("page"));
                metaData.setPerPage(metaJson.getInt("per_page"));
                metaData.setTotal(metaJson.getInt("total"));
//                metaData.getFieldTypes().forEach((k,v)->metaData.getFieldValues().put(k,metaJson.get(k)));

                JSONArray results = json.getJSONArray("results");
                List<Result> resultSet = new ArrayList<>();

                for(int i=0; i<results.length(); i++){
                    JSONObject obj = results.getJSONObject(i);
                    Result result = new Result();
                    result.setId(obj.getInt("id"));
                    result.setStudentSize(obj.getInt(queryParam.get("year")+".student.size"));
                    result.setYear(Integer.parseInt(queryParam.get("year")));
                    result.setSchoolName(obj.get("school.name").toString());
                    resultSet.add(result);
                }

                Collections.sort(resultSet);
                responseData.setMetaData(metaData);
                responseData.setResultSet(resultSet);
                return responseData;
            } catch (JSONException e) {
                e.printStackTrace();
                //Todo: handle json error
                System.out.println("Error parsing json from response");
            }
        }
        return null;
    }

    @Override
    public ResponseData getResponseData(Map<String,String> queryParam){
        return getPaginatedResult(queryParam, 0, 100);
    }

}
