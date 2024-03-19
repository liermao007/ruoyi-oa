package com.thinkgem.jeesite.modules.linkFace.linkface.config;

/**
 * SDK Restful接口 URL
 */
public interface LinkFaceConstants {


    /**
     * 接口调用URL前缀
     */
    public static final String  URL_PREFIX  = "http://cloudapi.linkface.cn";

    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";


    /**
     * 人脸识别  该API的功能是将两张人脸图片进行比对，来判断是否为同一个人。
     */
    public static final String HISTORICAL_SELFIE_VERIFICATION  = URL_PREFIX+"/identity/historical_selfie_verification";


    /**
     * 人脸识别  该 API 的功能是将人脸照片与公安带水印照片进行比对，来判断是否同一个人。
     */
    public static final String SELFIE_WATERMARK_VERIFICATION  = URL_PREFIX+"/identity/selfie_watermark_verification";


    /**
     * 人脸识别  该 API 的功能是用身份证号和姓名去查询公安部后台预留照片，然后与客户上传的人脸图片进行比对，来判断是否为同一个人。
     * (注：此接口只返回比对的置信度，不会返回公安带水印照片）
     */
    public static final String SELFIE_IDNUMBER_VERIFICATION  = URL_PREFIX+"/identity/selfie_idnumber_verification";


    /**
     * 人脸识别  该 API 的功能是用身份证号和姓名去查询公安部后台预留带水印照片，与客户上传的活体数据进行比对，来判断是否为同一个人。
     * (注：此接口只返回比对的置信度，不会返回公安带水印照片）
     */
    public static final String LIVENESS_IDNUMBER_VERIFICATION  = URL_PREFIX+"/identity/liveness_idnumber_verification";


    /**
     * 人脸识别  该 API 的功能是将活体数据与公安带水印照片进行比对,来判断是否同一个人。
     */
    public static final String LIVENESS_WATERMARK_VERIFICATION  = URL_PREFIX+"/identity/liveness_watermark_verification";


    /**
     * 人脸识别  该 API 的功能是将活体数据与自拍照片进行比对，来判断是否同一个人。
     */
    public static final String LIVENESS_SELFIE_VERIFICATION  = URL_PREFIX+"/identity/liveness_selfie_verification";


    /**
     * 人脸识别  该API的功能是将两张有多个人脸图片进行比对，来判断是否有人同时出现在两张图片中。 要求每张图片的人脸不超过4个！（如果图片中超过4个人脸，请调用1：N接口）
     * 常见的使用场景：一张个人照片，一张合影照片， 调用本接口可以判断个人照中的人是否出现在合影照片中。
     */
    public static final String COMPARE_FACES_IN_TWO_IMAGES  = URL_PREFIX+"/identity/compare_faces_in_two_images";


    /**
     * 人脸识别  该API的功能是将自拍照脸部关键部位进行打码（目前仅支持对眼部打码）
     */
    public static final String MASK_SELFIE_IMAGE  = "/identity/mask_selfie_image";


    /**
     * 人脸识别  该API的功能是将自拍照脸部关键部位进行打码（目前仅支持对眼部打码）
     */
    public static final String HANDHOLD_IDCARD_VERIFICATION  = "/identity/handhold_idcard_verification";



    /*--------------------------------------------分割线-----------------------------------------*/


    /**
     * 该API的功能是创建一个1比 n 图片搜索库。
     * 每个库最多图片上限5000张，每个api_id最多建立5个图片搜索库。
     */
    public static final String SEARCH_DB_CREATE = "https://cloudapi.linkface.cn/search/db/create";

    /**
     * 该API的功能是删除图片搜索库。
     */
    public static final String SEARCH_DB_DELETE = "https://cloudapi.linkface.cn/search/db/delete";

    /**
     * 该API的功能是查询图片搜索库的基础信息。
     */
    public static final String SEARCH_DB_INFO = "https://cloudapi.linkface.cn/search/db/info";


    /**
     * 该API的功能是列出当前api_id下的所有图片搜索库信息。
     */
    public static final String SEARCH_DB_LIST = "https://cloudapi.linkface.cn/search/db/list";

    /**
     * 该API的功能是查询图片搜索库的person列表。
     */
    public static final String SEARCH_DB_PERSON_LIST  = "https://cloudapi.linkface.cn/search/db/person_list";

    /**
     * 该API的功能是删除图片搜索库中的一张图片。
     */
    public static final String SEARCH_IMAGE_DELETE  = "https://cloudapi.linkface.cn/search/image/delete";

    /**
     * 该API的功能是删除图片搜索库中一个人。
     */
    public static final String SEARCH_IMAGE_DELETE_PERSON  = "https://cloudapi.linkface.cn/search/image/delete_person";

    /**
     * 该API的功能是向图片搜索库中插入一张图片。
     */
    public static final String SEARCH_IMAGE_INSERT  = "http://cloudapi.linkface.cn/search/image/insert";

    /**
     * 该API的功能是上传一张图片，返回图片搜索库中与该图片最相似的10张。
     */
    public static final String SEARCH_IMAGE_SEARCH  = "http://cloudapi.linkface.cn/search/image/search";


    /*--------------------------------------------分割线-----------------------------------------*/

}
