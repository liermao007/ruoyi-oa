package com.thinkgem.jeesite.modules.linkFace.linkface.service;

import com.thinkgem.jeesite.modules.linkFace.linkface.config.LinkFaceConstants;
import com.thinkgem.jeesite.modules.linkFace.plat.common.utils.HttpClientUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;

/**
 * 人脸比对  Service
 */
public class IdentityService {



    private String historicalSelfieVer(MultipartEntity entity, Boolean selfie_auto_rotate, Boolean historical_selfie_auto_rotate) throws Exception {
        return HttpClientUtils.requestForMultipartEntity(LinkFaceConstants.HISTORICAL_SELFIE_VERIFICATION,entity);
    }

    /**
     * 该API的功能是将两张人脸图片进行比对，来判断是否为同一个人。
     * @param selfieFile                     第一张图片的selfie_file，本地上传选取此参数
     * @param historicalSelfieFile           第二张图片的selfie_file，本地上传选取此参数
     * @return
     * request_id	string	本次请求的 id
     * status	string	状态。正常为 OK ，其他值表示失败。详见错误码
     * confidence	float	置信度。值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * historical_selfie	object	第二张图片的云端id，若使用file、url方式上传第二张图片返回此参数
     * selfie	object	第一张图片的云端id，若使用file、url方式上传第一张图片返回此参数
     * @throws Exception
     */
    public String historicalSelfieVerForFile(File selfieFile,File historicalSelfieFile) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_file", new FileBody(selfieFile));
        entity.addPart("historical_selfie_file", new FileBody(historicalSelfieFile));
        return this.historicalSelfieVer(entity,false,false);
    }

    /**
     * 该API的功能是将两张人脸图片进行比对，来判断是否为同一个人。
     * @param selfieURL                       第一张图片的url，从网络获取时选取此参数
     * @param historicalSelfieURL             第二张图片的url，从网络获取时需选取此参数
     * @return
     * request_id	string	本次请求的 id
     * status	string	状态。正常为 OK ，其他值表示失败。详见错误码
     * confidence	float	置信度。值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * historical_selfie	object	第二张图片的云端id，若使用file、url方式上传第二张图片返回此参数
     * selfie	object	第一张图片的云端id，若使用file、url方式上传第一张图片返回此参数
     * @throws Exception
     */
    public String historicalSelfieVerForURL(String selfieURL,String historicalSelfieURL) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_url", new StringBody(selfieURL));
        entity.addPart("historical_selfie_url", new StringBody(historicalSelfieURL));
        return this.historicalSelfieVer(entity,false,false);

    }

    /**
     * 该API的功能是将两张人脸图片进行比对，来判断是否为同一个人。
     * @param selfie_image_id                   第一张图片的云端id，在云端上传过可选取此参数
     * @param historical_selfie_image_id        第二张图片的云端id，在云端上传过可选取此参数
     * @return
     * request_id	string	本次请求的 id
     * status	string	状态。正常为 OK ，其他值表示失败。详见错误码
     * confidence	float	置信度。值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * historical_selfie	object	第二张图片的云端id，若使用file、url方式上传第二张图片返回此参数
     * selfie	object	第一张图片的云端id，若使用file、url方式上传第一张图片返回此参数
     * @throws Exception
     */
    public String historicalSelfieVerForId(String selfie_image_id,String historical_selfie_image_id) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_image_id", new StringBody(selfie_image_id));
        entity.addPart("historical_selfie_image_id", new StringBody(historical_selfie_image_id));
        return this.historicalSelfieVer(entity,false,false);

    }


    private String selfieWatermarkVer(MultipartEntity entity, Boolean selfie_auto_rotate, Boolean historical_selfie_auto_rotate) throws Exception {
        return HttpClientUtils.requestForMultipartEntity(LinkFaceConstants.SELFIE_WATERMARK_VERIFICATION,entity);
    }


    /**
     * 该 API 的功能是将人脸照片与公安带水印照片进行比对，来判断是否同一个人。
     * @param selfie_file                         人脸图片文件，上传本地图片进行检测时选取此参数
     * @param watermark_picture_file              公安带水印照片，上传本地图片进行检测时选取此参数
     * @return
     * request_id	string	本次请求的id
     * status	string	状态，正常为 OK，其他值表示失败，详见错误码
     * confidence	float	置信度，值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * watermark_picture	object	使用file、url方式返回的公安部后台预留的带水印照片的云端id
     * selfie	object	使用file、url方式返回的普通人脸图片的云端id
     * @throws Exception
     */
    public String selfieWatermarkVerForFile(File selfie_file,File watermark_picture_file) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_file", new FileBody(selfie_file));
        entity.addPart("watermark_picture_file", new FileBody(watermark_picture_file));
        return this.selfieWatermarkVer(entity,false,false);
    }

    /**
     * 该 API 的功能是将人脸照片与公安带水印照片进行比对，来判断是否同一个人。
     * @param selfie_url                         人脸图片网络地址，采用抓取网络图片方式时需选取此参数
     * @param watermark_picture_url              公安带水印照片的网络地址，采用抓取网络图片方式时需选取此参数
     * @return
     * request_id	string	本次请求的id
     * status	string	状态，正常为 OK，其他值表示失败，详见错误码
     * confidence	float	置信度，值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * watermark_picture	object	使用file、url方式返回的公安部后台预留的带水印照片的云端id
     * selfie	object	使用file、url方式返回的普通人脸图片的云端id
     * @throws Exception
     */
    public String selfieWatermarkVerForURL(String selfie_url,String watermark_picture_url) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_image_url", new StringBody(selfie_url));
        entity.addPart("watermark_picture_url", new StringBody(watermark_picture_url));
        return this.selfieWatermarkVer(entity,false,false);
    }


    /**
     * 该 API 的功能是将人脸照片与公安带水印照片进行比对，来判断是否同一个人。
     * @param selfie_image_id                   人脸图片的id，在云端上传过图片可采用此参数
     * @param watermark_picture_image_id        公安带水印照片的id，在云端上传过图片可采用
     * @return
     * request_id	string	本次请求的id
     * status	string	状态，正常为 OK，其他值表示失败，详见错误码
     * confidence	float	置信度，值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * watermark_picture	object	使用file、url方式返回的公安部后台预留的带水印照片的云端id
     * selfie	object	使用file、url方式返回的普通人脸图片的云端id
     * @throws Exception
     */
    public String selfieWatermarkVerForId(String selfie_image_id,String watermark_picture_image_id) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_image_id", new StringBody(selfie_image_id));
        entity.addPart("historical_selfie_image_id", new StringBody(watermark_picture_image_id));
        return this.selfieWatermarkVer(entity,false,false);
    }


    private String selfieIdnumberVer(MultipartEntity entity, Boolean selfie_auto_rotate) throws Exception {
        return HttpClientUtils.requestForMultipartEntity(LinkFaceConstants.SELFIE_IDNUMBER_VERIFICATION,entity);
    }


    /**
     * 该 API 的功能是用身份证号和姓名去查询公安部后台预留照片，然后与客户上传的人脸图片进行比对，来判断是否为同一个人。
     * @param selfie_image_id                   人脸图片的id，在云端上传过图片可采用此参数
     * @param id_number                         身份证号。用以查询公安部后台预留照片
     * @param name                              与身份证号相对应的姓名。身份证号及姓名相匹配才能查询公安后台照片
     * @return
     * request_id	string	本次请求的id。
     * status	string	状态。正常为 OK，其他值表示失败。详见错误码
     * confidence	float	置信度。值为 0~1，值越大表示两张照片属于同一个人的可能性越大。无法得到公安后台预留水印照时该值为 null
     * identity	object	公安接口调用结果。
     * selfie	object	请求参数中使用file、url方式会返回图片的id。
     * identity 的结构如下：
     * validity	boolean	身份证和姓名经过公安接口验证是否匹配。匹配为 true，不匹配为 false
     * photo_id	string	公安后台预留照片的 ID。公安后台无该身份信息对应的照片时该值为 null
     * reason	string	公安接口出错的原因。正常为Gongan status OK 。其他错误类型参考reason字段表
     * @throws Exception
     */
    public String selfieIdnumberVerForId(String selfie_image_id,String id_number,String name) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_image_id", new StringBody(selfie_image_id));
        entity.addPart("id_number", new StringBody(id_number));
        entity.addPart("name", new StringBody(name));
        return this.selfieIdnumberVer(entity,false);
    }


    /**
     * 该 API 的功能是用身份证号和姓名去查询公安部后台预留照片，然后与客户上传的人脸图片进行比对，来判断是否为同一个人。
     * @param selfie_url                        图片网络地址。采用抓取网络图片方式可选取此参数
     * @param id_number                         身份证号。用以查询公安部后台预留照片
     * @param name                              与身份证号相对应的姓名。身份证号及姓名相匹配才能查询公安后台照片
     * @return
     * request_id	string	本次请求的id。
     * status	string	状态。正常为 OK，其他值表示失败。详见错误码
     * confidence	float	置信度。值为 0~1，值越大表示两张照片属于同一个人的可能性越大。无法得到公安后台预留水印照时该值为 null
     * identity	object	公安接口调用结果。
     * selfie	object	请求参数中使用file、url方式会返回图片的id。
     * identity 的结构如下：
     * validity	boolean	身份证和姓名经过公安接口验证是否匹配。匹配为 true，不匹配为 false
     * photo_id	string	公安后台预留照片的 ID。公安后台无该身份信息对应的照片时该值为 null
     * reason	string	公安接口出错的原因。正常为Gongan status OK 。其他错误类型参考reason字段表
     * @throws Exception
     */
    public String selfieIdnumberVerForURL(String selfie_url,String id_number,String name) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_url", new StringBody(selfie_url));
        entity.addPart("id_number", new StringBody(id_number));
        entity.addPart("name", new StringBody(name));
        return this.selfieIdnumberVer(entity,false);
    }


    /**
     * 该 API 的功能是用身份证号和姓名去查询公安部后台预留照片，然后与客户上传的人脸图片进行比对，来判断是否为同一个人。
     * @param selfie_file                       需上传的图片文件。上传本地图片可选取此参数
     * @param id_number                         身份证号。用以查询公安部后台预留照片
     * @param name                              与身份证号相对应的姓名。身份证号及姓名相匹配才能查询公安后台照片
     * @return
     * request_id	string	本次请求的id。
     * status	string	状态。正常为 OK，其他值表示失败。详见错误码
     * confidence	float	置信度。值为 0~1，值越大表示两张照片属于同一个人的可能性越大。无法得到公安后台预留水印照时该值为 null
     * identity	object	公安接口调用结果。
     * selfie	object	请求参数中使用file、url方式会返回图片的id。
     * identity 的结构如下：
     * validity	boolean	身份证和姓名经过公安接口验证是否匹配。匹配为 true，不匹配为 false
     * photo_id	string	公安后台预留照片的 ID。公安后台无该身份信息对应的照片时该值为 null
     * reason	string	公安接口出错的原因。正常为Gongan status OK 。其他错误类型参考reason字段表
     * @throws Exception
     */
    public String selfieIdnumberVerForFile(File selfie_file,String id_number,String name) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_file", new FileBody(selfie_file));
        entity.addPart("id_number", new StringBody(id_number));
        entity.addPart("name", new StringBody(name));
        return this.selfieIdnumberVer(entity,false);
    }


    private String livenessIdnumberVer(MultipartEntity entity) throws Exception {
        return HttpClientUtils.requestForMultipartEntity(LinkFaceConstants.LIVENESS_IDNUMBER_VERIFICATION,entity);
    }


    /**
     * 该 API 的功能是用身份证号和姓名去查询公安部后台预留带水印照片，与客户上传的活体数据进行比对，来判断是否为同一个人。
     * @param liveness_data_file                移动端SDK返回的protobuf文件。上传本地文件时选取此参数
     * @param id_number                         身份证号。用以查询公安部后台预留照片
     * @param name                              与身份证号相对应的姓名。身份证号及姓名相匹配才能查询公安后台照片
     * @return
     * request_id	string	本次请求的id
     * status	string	返回状态，正常为 OK，其他值表示失败。详见错误码
     * hack_score	float	防hack得分，取值范围是0~1。值越大表示被hack的可能性越大
     * verify_score	float	置信度。取值范围为 0~1，值越大表示两张照片是同一个人的可能性越大
     * liveness_data_id	string	云端活体数据的id。如果使用file、url方式上传活体数据返回此参数
     * @throws Exception
     */
    public String livenessIdnumberVerForFile(File liveness_data_file,String id_number,String name) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("liveness_data_file", new FileBody(liveness_data_file));
        entity.addPart("id_number", new StringBody(id_number));
        entity.addPart("name", new StringBody(name));
        return this.livenessIdnumberVer(entity);
    }

    /**
     * 该 API 的功能是用身份证号和姓名去查询公安部后台预留带水印照片，与客户上传的活体数据进行比对，来判断是否为同一个人。
     * @param liveness_data_url                 移动端SDK返回的protobuf文件的url。采用抓取网络文件方式时选取此参数
     * @param id_number                         身份证号。用以查询公安部后台预留照片
     * @param name                              与身份证号相对应的姓名。身份证号及姓名相匹配才能查询公安后台照片
     * @return
     * request_id	string	本次请求的id
     * status	string	返回状态，正常为 OK，其他值表示失败。详见错误码
     * hack_score	float	防hack得分，取值范围是0~1。值越大表示被hack的可能性越大
     * verify_score	float	置信度。取值范围为 0~1，值越大表示两张照片是同一个人的可能性越大
     * liveness_data_id	string	云端活体数据的id。如果使用file、url方式上传活体数据返回此参数
     * @throws Exception
     */
    public String livenessIdnumberVerForURL(String liveness_data_url,String id_number,String name) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("liveness_data_url", new StringBody(liveness_data_url));
        entity.addPart("id_number", new StringBody(id_number));
        entity.addPart("name", new StringBody(name));
        return this.livenessIdnumberVer(entity);
    }

    /**
     * 该 API 的功能是用身份证号和姓名去查询公安部后台预留带水印照片，与客户上传的活体数据进行比对，来判断是否为同一个人。
     * @param liveness_data_id                  移动端SDK返回的protobuf文件的id。在云端上传过文件可选取此参数
     * @param id_number                         身份证号。用以查询公安部后台预留照片
     * @param name                              与身份证号相对应的姓名。身份证号及姓名相匹配才能查询公安后台照片
     * @return
     * request_id	string	本次请求的id
     * status	string	返回状态，正常为 OK，其他值表示失败。详见错误码
     * hack_score	float	防hack得分，取值范围是0~1。值越大表示被hack的可能性越大
     * verify_score	float	置信度。取值范围为 0~1，值越大表示两张照片是同一个人的可能性越大
     * liveness_data_id	string	云端活体数据的id。如果使用file、url方式上传活体数据返回此参数
     * @throws Exception
     */
    public String livenessIdnumberVerForID(String liveness_data_id,String id_number,String name) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("liveness_data_id", new StringBody(liveness_data_id));
        entity.addPart("id_number", new StringBody(id_number));
        entity.addPart("name", new StringBody(name));
        return this.livenessIdnumberVer(entity);
    }


    private String livenessSelfieVer(MultipartEntity entity, Boolean selfie_auto_rotate) throws Exception {
        return HttpClientUtils.requestForMultipartEntity(LinkFaceConstants.LIVENESS_SELFIE_VERIFICATION,entity);
    }


    /**
     * 该 API 的功能是将活体数据与自拍照片进行比对，来判断是否同一个人。
     * @param selfie_file                           自拍照片，上传本地图片进行检测时选取此参数
     * @param liveness_data_file                    移动端SDK返回的protobuf文件。上传本地文件时选取此参数
     * @return
     * request_id	string	本次请求的id。
     * status	string	状态。正常为 OK，其他值表示失败。详见错误码
     * hack_score	float	防hack得分，取值范围是0~1。值越大表示被hack的可能性越大
     * verify_score	float	置信度，值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * liveness_data_id	string	使用file、url方式上传活体数据返回的活体数据的id
     * image_id	string	使用file、url方式上传公安部后台预留返回的公安部后台预留的带水印照片的id
     * @throws Exception
     */
    public String livenessSelfieVerForFile(File selfie_file,File liveness_data_file) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_file", new FileBody(selfie_file));
        entity.addPart("liveness_data_file", new FileBody(liveness_data_file));
        return this.livenessSelfieVer(entity,false);
    }


    /**
     * 该 API 的功能是将活体数据与自拍照片进行比对，来判断是否同一个人。
     * @param selfie_url                           自拍照片的网络地址，采用抓取网络图片方式时需选取此参数
     * @param liveness_data_url                    移动端SDK返回的protobuf文件的url。采用抓取网络文件方式时需选取此参数
     * @return
     * request_id	string	本次请求的id。
     * status	string	状态。正常为 OK，其他值表示失败。详见错误码
     * hack_score	float	防hack得分，取值范围是0~1。值越大表示被hack的可能性越大
     * verify_score	float	置信度，值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * liveness_data_id	string	使用file、url方式上传活体数据返回的活体数据的id
     * image_id	string	使用file、url方式上传公安部后台预留返回的公安部后台预留的带水印照片的id
     * @throws Exception
     */
    public String livenessSelfieVerForURL(String selfie_url,String liveness_data_url) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_file", new StringBody(selfie_url));
        entity.addPart("liveness_data_file", new StringBody(liveness_data_url));
        return this.livenessSelfieVer(entity,false);
    }

    /**
     * 该 API 的功能是将活体数据与自拍照片进行比对，来判断是否同一个人。
     * @param selfie_image_id                      自拍照片的id，在云端上传过自拍照片可采用选取此参数
     * @param liveness_data_id                    移动端SDK返回的protobuf文件的id。在云端上传过文件可采用此参数
     * @return
     * request_id	string	本次请求的id。
     * status	string	状态。正常为 OK，其他值表示失败。详见错误码
     * hack_score	float	防hack得分，取值范围是0~1。值越大表示被hack的可能性越大
     * verify_score	float	置信度，值为 0~1，值越大表示两张照片是同一个人的可能性越大
     * liveness_data_id	string	使用file、url方式上传活体数据返回的活体数据的id
     * image_id	string	使用file、url方式上传公安部后台预留返回的公安部后台预留的带水印照片的id
     * @throws Exception
     */
    public String livenessSelfieVerForID(String selfie_image_id,String liveness_data_id) throws Exception {
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("selfie_file", new StringBody(selfie_image_id));
        entity.addPart("liveness_data_file", new StringBody(liveness_data_id));
        return this.livenessSelfieVer(entity,false);
    }

}
