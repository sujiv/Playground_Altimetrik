package altimetrik.playground.dec.colleges.services;

import altimetrik.playground.dec.colleges.domains.ResponseData;
import org.json.JSONObject;

import java.util.Map;

public interface CollegeService {

    public ResponseData getResponseData(Map<String,String> queryParam);
    public ResponseData getPaginatedResult(Map<String,String> queryParam, int page, int records);
    public JSONObject getLatLong(int zip);
}
