package altimetrik.playground.dec.colleges.controllers;

import altimetrik.playground.dec.colleges.domains.ResponseData;
import altimetrik.playground.dec.colleges.services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
public class CollegeController {

    @Autowired
    CollegeService collegeService;

    @GetMapping(value="/schools")
    public ResponseEntity<Object> getResults(@RequestParam(value="predominant", required = true) String predominant,
                                                   @RequestParam(value="year", required = true) int year,
                                                   @RequestParam(value="zip", required = true) int zip,
                                                   @RequestParam(value="distance", required = true) int loc,
                                                   @RequestParam(value="page", required = false) Integer page,
                                                   @RequestParam(value="perPage", required = false) Integer perPage){
        Map<String, String> criteria = new HashMap();
        criteria.put("school.degrees_awarded.predominant",predominant);
        criteria.put("year",""+year);
        criteria.put("zip","" + zip);
        criteria.put("distance",""+loc+"mi");

        System.out.println(criteria);
        ResponseData responseData;
        if(perPage == null){
            responseData = collegeService.getResponseData(criteria);
        }
        else{
            responseData = collegeService.getPaginatedResult(criteria, page, perPage);
        }

        if(responseData!=null)
            return ResponseEntity.status(201).body(responseData);

        return ResponseEntity.status(400).body("input errors");

    }

    @GetMapping(value="/latlong/{zip}")
    public ResponseEntity<String> getLatLong(@PathVariable(name = "zip", required = true) int zip){
        return ResponseEntity.status(200).body(collegeService.getLatLong(zip).toString());
    }
}
