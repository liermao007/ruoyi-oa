package com.thinkgem.jeesite.modules.oa.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.dao.TableSetDao;
import com.thinkgem.jeesite.modules.oa.entity.TableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信设置Service
 * @author oa
 * @version 2017-12-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TableSetService extends CrudService<TableSetDao, TableSet> {
    @Autowired
    private TableSetDao tableSetDao;
    public String getById(String id, String cid){
        return tableSetDao.getById(id, cid);
    }

    public TableSet update(TableSet tableSet) { return tableSet; }


    public List findAll (String id) {
        List<TableSet> allList = tableSetDao.findAll(id);
        List<TableSet> lackList = tableSetDao.findLack(id);
        List<TableSet> list = new ArrayList<>();
        for(TableSet tableSet : allList) {
            for(TableSet tableSet2 : lackList) {
                if(tableSet.getTableName().equals( tableSet2.getTableName())){
                    tableSet.setIsSend("0");
                }
                continue;
            }
            list.add(tableSet);
        }
        return list;
    }

    @Override
    public void save(TableSet tableSet) {
        tableSet.preInsert();
        tableSetDao.insert(tableSet);
    }
    public void del(String id) {
        tableSetDao.del(id);
    }
    public int findTableIsSend(String tableName){
        int isSend = tableSetDao.findTableIsSend(tableName);
        return isSend;
    }
}
