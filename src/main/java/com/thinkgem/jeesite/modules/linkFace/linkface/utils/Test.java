package com.thinkgem.jeesite.modules.linkFace.linkface.utils;

import com.thinkgem.jeesite.modules.linkFace.linkface.service.SearchService;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;

/**
 * 
 */
public class Test {

    public static void main(String[] args) throws Exception {


//        Map<String,String> map = new HashMap <String, String>();
//        map.put("patientId", "1");
//        map.put("registIds", "1,2,3");
//        map.put("registType", "0");
//        map.put("prices", "1.8");
//        map.put("name", "张三丰");
//        map.put("sex", "1");
//        map.put("payType", "6");
//        map.put("name", "张三丰");
//        map.put("age", "100");
//        map.put("firstVisitIndicator", "1");
//        map.put("chargeType", "1");
//        map.put("identity", "1");
//        map.put("idNo", "1222222222");
//        map.put("orgId", "1111");
//        map.put("visitDate", "2017-10-10 17:01:00");
//        map.put("cardPayFlag", "11231231313323");

        //String s = httpClient.requestForMultipartEntity("http://127.0.0.1:8085/register/V100/insertRegisterDetail", entity);


//        System.out.println(HttpClientUtils.doPost("http://127.0.0.1:8080/register/V100/insertRegisterDetail",map));
       SearchService searchService  = new SearchService();

      /*   Map<String,String> map = new HashMap<String, String>();

        map.put("name","shuangluan");

        map.put("desc","双滦医院人脸识别库");*/


        /*System.out.println(searchService.searchDbCreate(map));*/

       File file1 = new File("E:\\CNTV\\1.jpg");
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_file", new FileBody(file1));
        entity.addPart("name", new StringBody("shuangluan"));
//
//
//        entity.addPart("desc",new StringBody("向强"));
//
//        entity.addPart("person_uuid",new StringBody("30420d83c15a4c69afb641355d21e300"));
//        System.out.println(searchService.searchImageInsertForFile(entity));

        System.out.println(searchService.searchImageSearchForFile(entity));




//        IdentityService identityService = new IdentityService();
//        File file1 = new File("E:\\CNTV\\1.jpg");
//        File file2 = new File("E:\\CNTV\\2.jpg");
//
//
//        try {
//            System.out.println(identityService.historicalSelfieVerForFile(file1,file2));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }





    }
}
