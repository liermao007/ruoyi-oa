package com.thinkgem.jeesite.modules.linkFace.linkface.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.linkFace.linkface.config.LinkFaceConstants;
import com.thinkgem.jeesite.modules.linkFace.plat.common.utils.HttpClientUtils;
import org.apache.http.entity.mime.MultipartEntity;

import java.util.Map;

/**
 * 
 */
public class SearchService {
    
     /**
       * @Title:
       * @Description: 该API的功能是创建一个1比 n 图片搜索库。
       * 每个库最多图片上限5000张，每个api_id最多建立5个图片搜索库。
       * @param param
       * @return 
       * @author oa
       * @date 2017/11/20
       * @throws
      */

    public String searchDbCreate(Map<String, String> param) throws Exception {

        return HttpClientUtils.doPost(LinkFaceConstants.SEARCH_DB_CREATE,param);

    }

     /**
       * @Title:
       * @Description: 该API的功能是向图片搜索库中插入一张图片。
       *传入图片要求
       *格式为 JPG（JPEG），BMP，PNG，GIF，TIFF
       *宽和高大于 8px，小于等于4000px
       *小于等于 5 MB
       *支持自动识别人脸方向
       *上传的图片中包含有 exif 方向信息，先按此信息旋转、翻转后再做识别人脸方向并调整。
       *如果照片方向混乱且 exif 方向信息不存在或不正确，自动识别人脸方向并调整
       * @param entity                  上传资料
       * @return
       * @author oa
       * @date 2017/11/20
       * @throws
      */
    public String searchImageInsertForFile(MultipartEntity entity) throws Exception {
        return HttpClientUtils.requestForMultipartEntity(LinkFaceConstants.SEARCH_IMAGE_INSERT,entity);

    }


     /**
       * @Title:
       * @Description: 该API的功能是上传一张图片，返回图片搜索库中与该图片最相似的10张。
       * 如果库中不足10张，则会返回全部图片。
       * 图片要求
       * 格式为 JPG（JPEG），BMP，PNG，GIF，TIFF
       * 宽和高大于 8px，小于等于4000px
       * 小于等于 5 MB
       * 支持自动识别人脸方向
       * 上传的图片中包含有 exif 方向信息，先按此信息旋转、翻转后再做识别人脸方向并调整。
       * 如果照片方向混乱且 exif 方向信息不存在或不正确，自动识别人脸方向并调整
       * @param entity
       * @author oa
       * @date 2017/11/20
       * @throws
      */
    public JSONObject searchImageSearchForFile(MultipartEntity entity) throws Exception {

        String res = HttpClientUtils.requestForMultipartEntity(LinkFaceConstants.SEARCH_IMAGE_SEARCH,entity);
        JSONObject resJson = JSON.parseObject(res);
        JSONObject maxConfidence = null;
        //1.成功返回
        if("OK".equals(resJson.getString("status"))){
            //
            JSONArray result = resJson.getJSONArray("result");
            //1.1 判断是否返回成功的
            if(result.size() == 0){

            //1.2 取相似最高并且confidence大于0.7 第一条记录
            }else{

                for(Object val:result){
                    if(null == maxConfidence){
                        maxConfidence = JSON.parseObject(val.toString());
                    }else{
                        if(JSON.parseObject(val.toString()).getInteger("score") > maxConfidence.getInteger("score")){
                            maxConfidence = JSON.parseObject(val.toString());
                        }
                    }
                }
            }
        //2.失败返回
        }else{


        }

        return maxConfidence;

    }

}
