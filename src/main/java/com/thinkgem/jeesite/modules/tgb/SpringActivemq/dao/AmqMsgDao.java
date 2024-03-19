package com.thinkgem.jeesite.modules.tgb.SpringActivemq.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.DlqMessage;
import com.thinkgem.jeesite.modules.tgb.SpringActivemq.entity.MSG;

import java.util.List;

/**
 *
 * ������Ϣdao
 * @author oa
 * @version 2017-05-27
 */
@MyBatisDao
public interface AmqMsgDao extends CrudDao<MSG> {

    List<MSG> getMessage();

    String selstate(String dingdan_id);

    String selcount(String shangpin_id);

    void increaseMessageCount(String id);

    void editMessageState(String id);

    void insertDlqMessage(DlqMessage d);

    void saveMessage(MSG msg);


}
