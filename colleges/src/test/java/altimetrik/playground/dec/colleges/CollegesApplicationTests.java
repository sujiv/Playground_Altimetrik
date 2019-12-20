package altimetrik.playground.dec.colleges;

import altimetrik.playground.dec.colleges.controllers.CollegeController;
import altimetrik.playground.dec.colleges.domains.MetaData;
import altimetrik.playground.dec.colleges.domains.ResponseData;
import altimetrik.playground.dec.colleges.domains.Result;
import altimetrik.playground.dec.colleges.services.CollegeService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class CollegesApplicationTests {

    @Autowired
    CollegeController collegeController;

    @MockBean
    CollegeService collegeService;

    @Test
    void contextLoads() {
    }

    @Test
    void testController1(){
        Map<String, String> criteria = new HashMap();
        criteria.put("predominant","2,3");
        criteria.put("year","2018");
        criteria.put("zip","52557");
        criteria.put("distance","10mi");
        ResponseData responseData = new ResponseData();
        MetaData metaData = new MetaData();
        metaData.setTotal(1000);
        metaData.setPerPage(100);
        metaData.setPage(0);
//        metaData.setVal("total",100);
//        metaData.setVal("page",0);
//        metaData.setVal("perPage",100);

        responseData.setMetaData(metaData);
        responseData.setResultSet(new ArrayList<Result>());
        when(collegeService.getResponseData(criteria)).thenReturn(responseData);
        ResponseData responseData1 = (ResponseData)collegeController.getResults("2,3", 2018, 52557, 10,0,null).getBody();
        Assert.assertEquals(1000, responseData1.getMetaData().getTotal());
    }

    @Test
    void testController2(){
        Map<String, String> criteria = new HashMap();
        criteria.put("predominant","2,3");
        criteria.put("year","2013");
        criteria.put("zip","52556");
        criteria.put("distance","10mi");
        ResponseData responseData = new ResponseData();
        MetaData metaData = new MetaData();
        metaData.setTotal(1000);
        metaData.setPerPage(100);
        metaData.setPage(0);
        responseData.setMetaData(metaData);
        responseData.setResultSet(new ArrayList<Result>());
        when(collegeService.getResponseData(criteria)).thenReturn(responseData);
        ResponseData responseData1 = (ResponseData)collegeController.getResults("2,3", 2018, 52557, 10,0,null).getBody();
        Assert.assertEquals(100, responseData1.getMetaData().getPerPage());
    }

    @Test
    void testController3(){
        Map<String, String> criteria = new HashMap();
        criteria.put("predominant","2,3");
        criteria.put("year","2010");
        criteria.put("zip","52557");
        criteria.put("distance","10mi");
        ResponseData responseData = new ResponseData();
        MetaData metaData = new MetaData();
        metaData.setTotal(1000);
        metaData.setPerPage(100);
        metaData.setPage(0);
        responseData.setMetaData(metaData);
        List<Result> resultList = new ArrayList<Result>();
        resultList.add(new Result(1,"MUM",100,2017));
        responseData.setResultSet(resultList);
        when(collegeService.getResponseData(criteria)).thenReturn(responseData);
        ResponseData responseData1 = (ResponseData)collegeController.getResults("2,3", 2018, 52557, 10,0,null).getBody();
        Assert.assertEquals(1, responseData1.getResultSet().size());
    }
}
