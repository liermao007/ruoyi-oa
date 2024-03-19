package com.thinkgem.jeesite.modules.oa.units;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SecurityEngineUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.form.util.ComponentUtils;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;
import com.thinkgem.jeesite.modules.oa.entity.PersonSigns;
import com.thinkgem.jeesite.modules.oa.service.PersonSignsService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;
import com.thinkgem.jeesite.modules.table.service.OaPersonDefineTableService;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author oa
 */
public class FlowUtils {

    private static PersonSignsService personSignsService = SpringContextHolder.getBean(PersonSignsService.class);
    private static OaPersonDefineTableService oaPersonDefineTableService = SpringContextHolder.getBean(OaPersonDefineTableService.class);

    /**
     * 在审批流程的时候需要上传的个人签名
     *
     * @param flowData
     * @param flag     标注是审批还是提交申请
     * @param
     * @return
     * @throws Exception
     */
    public static FlowData savePersonsigns(FlowData flowData, String flag, boolean boolFlag) {
        PersonSigns personSigns = personSignsService.getLoginId(UserUtils.getUser().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());
        if (personSigns != null && StringUtils.equalsIgnoreCase(flag, "0")) {
            String url = "<img style=\"width:90px; height:60px\" src=\"" + personSigns.getSignUrl() + "\"/>";
            if (StrUtil.isBlank(flowData.getAct().getComment())) {
                flowData.getAct().setComment(date + "<br>" + url);
            }
        }

        if (personSigns != null && StringUtils.equalsIgnoreCase(flag, "2") && boolFlag == true) {
            if (personSigns == null) {
                flowData.getAct().setComment(date + "<br>");
            }
            flowData.getAct().setComment(flowData.getAct().getComment() + "(代办)");
        }

        if (StringUtils.equalsIgnoreCase(flag, "1")) {
            if ("1".equals(Global.getConfig("ca.enable"))) {
                if (flowData.getDsvsInfo() == null || StrUtil.isBlank(flowData.getDsvsInfo().getCertInfo()) || StrUtil.isBlank(flowData.getDsvsInfo().getBase64Data()) || StrUtil.isBlank(flowData.getDsvsInfo().getSigServer()) || StrUtil.isBlank(flowData.getDsvsInfo().getSig()) || StrUtil.isBlank(flowData.getDsvsInfo().getSigPic())) {
                    throw new RuntimeException("认证信息获取失败, 请检查系统参数！");
                }
                String cardNo = UserUtils.getUser().getCardNo();
                if (StrUtil.length(cardNo) != 18 || !flowData.getDsvsInfo().getCertInfo().endsWith("" + cardNo)) {
                    throw new RuntimeException("当前审核人信息匹配失败, 请检查系统参数！");
                }

                flowData.getDsvsInfo().setData(new String(SecurityEngineUtils.base64Decode(flowData.getDsvsInfo().getBase64Data())));
                boolean verifyResult = SecurityEngineUtils.verifyBase64(flowData.getDsvsInfo().getSigServer(), flowData.getDsvsInfo().getBase64Data(), flowData.getDsvsInfo().getSig());
                if (!verifyResult) {
                    throw new RuntimeException("认证失败, 请检查系统参数！");
                }
                String ts = SecurityEngineUtils.getTS(flowData.getDsvsInfo().getBase64Data());
                if (StrUtil.isBlank(ts)) {
                    throw new RuntimeException("获取时间戳失败, 请检查系统参数！");
                }
                flowData.getDsvsInfo().setTs(ts);

                String url = "<img style=\"width:90px;height:60px;\" src=\"" + flowData.getDsvsInfo().getSigPic() + "\"/>";
                if (StrUtil.isBlank(flowData.getAct().getComment())) {
                    flowData.getAct().setComment(date + "<br>");
                } else {
                    flowData.getAct().setComment(flowData.getAct().getComment() + "<br>" + date + "<br>");
                }
                String comment = flowData.getAct().getComment() + url;
                flowData.getAct().setComment(comment);
            } else {
                if (personSigns != null) {
                    String url = "<img style=\"width:90px;height:60px;\" src=\"" + personSigns.getSignUrl() + "\"/>";
                    if (StringUtils.isEmpty(flowData.getAct().getComment()) || flowData.getAct().getComment() == "") {
                        flowData.getAct().setComment(date + "<br>");
                    } else {
                        flowData.getAct().setComment(flowData.getAct().getComment() + "<br>" + date + "<br>");
                    }
                    String comment = flowData.getAct().getComment() + url;
                    flowData.getAct().setComment(comment);
                } else {
                    if (StringUtils.isEmpty(flowData.getAct().getComment()) || flowData.getAct().getComment() == "") {
                        flowData.getAct().setComment(date);
                    } else {
                        flowData.getAct().setComment(flowData.getAct().getComment() + "<br>" + date + "<br>");
                    }
                    String comment = flowData.getAct().getComment();
                    flowData.getAct().setComment(comment);
                }
            }
        }

        return flowData;
    }

    /**
     * flowData中数据放入到Map
     *
     * @param flowData
     * @return
     */
    public static Map<String, Object> toMap(FlowData flowData) {
        Map<String, Object> map = flowData.getDatas();
        if (map == null) {
            map = new HashMap<>();
        } else if (!"flowForm".equals(flowData.getShowType())) {
            OaPersonDefineTable oaPersonDefineTable = oaPersonDefineTableService.findByTableName(flowData.getTableName(), null);
            List<OaPersonDefineTableColumn> columns = oaPersonDefineTableService.findColumnListByTableId(oaPersonDefineTable.getId());
            if (columns.size() > 0) {
                if (map.get("flowInfo") != null) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("flowInfo");
                    for (Map<String, Object> m : list) {
                        format(m, columns);
                    }
                } else {
                    format(map, columns);
                }
            }
        }
        map.put("tableName", flowData.getTableName());
        map.put("id", flowData.getId());
        map.put("formNo", flowData.getFormNo());
        map.put("ctx", Global.getAdminPath());
        map.put("caFlag", Global.getConfig("ca.enable"));
        if("1".equals(Global.getConfig("ca.enable"))) {
            map.put("dsvs", flowData.getDsvsInfo());
        }

        Act act = flowData.getAct();
        if (act != null) {
            if (act.getTaskName() != null) {
                act.setTaskName(act.getTaskName());
            }
            map.put("act", act);
        }

//        是否需要指定审核人
        if("T_ZGQXJSPB".equalsIgnoreCase(flowData.getTableName())) {
            if("0".equals(Global.getConfig("qj.check.zts"))) {
                if(act.getTaskDefKey() != null && act.getTaskDefKey().endsWith("_with_zd")) {
                    map.put("zdFlag", "1");
                }
            } else {
                int zts = flowData.getDatas() != null && flowData.getDatas().containsKey("ZTS") ? NumberUtil.parseInt(flowData.getDatas().get("ZTS").toString()) : 0;
                if(act.getTaskDefKey() != null && act.getTaskDefKey() != null && act.getTaskDefKey().endsWith("_with_zd") && zts > 1) {
                    map.put("zdFlag", "1");
                } else {
                    String grade = UserUtils.getUser().getGrade();
                    if(Lists.newArrayList("20", "21", "22", "30").contains(grade)) {
                        // 主任的申请， 或者主任审核大于1天的请假， 需要选择副院长
                        if(flowData.getDatas() == null || (flowData.getDatas().containsKey("ZTS") && NumberUtil.parseInt(flowData.getDatas().get("ZTS").toString()) > 1)) {
                            map.put("zdFlag", "1");
                        }
                    }
                }
            }
        } else {
            if(act.getTaskDefKey() != null && act.getTaskDefKey().endsWith("_with_zd")) {
                map.put("zdFlag", "1");
            }
        }
        return map;
    }

    private static void format(Map<String, Object> map, List<OaPersonDefineTableColumn> columns) {
        if (columns != null && map != null && columns.size() > 0 && map.keySet().size() > 0) {
            for (OaPersonDefineTableColumn column : columns) {
                if (ComponentUtils.chargeMoreData(column.getControlTypeId()) && map.get(column.getColumnName()) != null) {
                    map.put(column.getColumnName(), DictUtils.getDictLabels((String) map.get(column.getColumnName()), column.getRemarks(), ""));
                }
            }
        }
    }
}
