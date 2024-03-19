/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryDay;
import com.thinkgem.jeesite.modules.oa.dao.OaSummaryDayDao;

/**
 * 工作日志Service
 *
 * @author oa
 * @version 2016-11-16
 */
@Service
@Transactional(readOnly = true)
public class OaSummaryDayService extends CrudService<OaSummaryDayDao, OaSummaryDay> {

    @Autowired
    private OaSummaryDayDao oaSummaryDayDao;

    public OaSummaryDay get(String id) {
        return super.get(id);
    }

    public List<OaSummaryDay> findList(OaSummaryDay oaSummaryDay) {
        return super.findList(oaSummaryDay);
    }


    public Page<OaSummaryDay> findByDate1(Page<OaSummaryDay> page, OaSummaryDay oaSummaryDay) {
        oaSummaryDay.setPage(page);

        return page.setList(oaSummaryDayDao.findByDate1(oaSummaryDay));
    }

    public OaSummaryDay findByDate(OaSummaryDay oaSummaryDay) {
        return oaSummaryDayDao.findByDate(oaSummaryDay);
    }

    public Page<OaSummaryDay> findPage(Page<OaSummaryDay> page, OaSummaryDay oaSummaryDay) {
        return super.findPage(page, oaSummaryDay);
    }

    @Transactional(readOnly = false)
    public void save(OaSummaryDay oaSummaryDay) {
        if (oaSummaryDay != null && oaSummaryDay.getSumDate() != null) {
            //先根据当前日期，当前登陆人的id去数据库中查询，是否有数据，如果有数据则修改没有则添加
            OaSummaryDay byDate = dao.findByDate(oaSummaryDay);
            if (byDate != null) {
                oaSummaryDay.preUpdate();
                dao.update(oaSummaryDay);
            } else {
                oaSummaryDay.preInsert();
                dao.insert(oaSummaryDay);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(OaSummaryDay oaSummaryDay) {
        super.delete(oaSummaryDay);
    }

    public List<OaSummaryDay> findNewFore( OaSummaryDay oaSummaryDay){
        return oaSummaryDayDao.findNewFore(oaSummaryDay);
    }

}