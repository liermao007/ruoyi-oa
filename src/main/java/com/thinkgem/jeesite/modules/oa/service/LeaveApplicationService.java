/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.entity.TravelApplication;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.LeaveApplication;
import com.thinkgem.jeesite.modules.oa.dao.LeaveApplicationDao;

/**
 * 请假单统计Service
 * @author oa
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class LeaveApplicationService extends CrudService<LeaveApplicationDao, LeaveApplication> {

    @Autowired
    private LeaveApplicationDao leaveApplicationDao;
	public LeaveApplication get(String id) {
		return super.get(id);
	}
	
	/*public List<LeaveApplication> findList(LeaveApplication leaveApplication) {
		return super.findList(leaveApplication);
	}
	*/
	public Page<LeaveApplication> findList1(Page<LeaveApplication> page,String type, LeaveApplication leaveApplication, String tableName,String qjksrq,String qjjsrq) {

        StringBuffer company = new StringBuffer();
        if(StringUtils.isNotBlank(type)&&type.equals("2")){
        String companyId=  UserUtils.getUser().getCompany().getId();
        company.append(" where del_flag= '0'and companyid= "+"'"+companyId+"'");
        }else{
            company.append(" where del_flag= '0'");
        }
      if(leaveApplication.getQjksrq()!=null){
          company.append( " AND qjksrq >= "+"'"+qjksrq+"'") ;
      }
        if(leaveApplication.getQjksrq()!=null){
            company.append( " AND qjjsrq <= "+"'"+qjjsrq+"'") ;
        }
        leaveApplication.setPage(page);
        String sql;
        if(page.getPageNo()<=1){
            sql = "select ks,xm,qjlx,qjts,qjksrq,qjjsrq,(select  COUNT(*)  from " + tableName+company+" ORDER BY update_date DESC) as 'count' from " + tableName+company+" ORDER BY update_date DESC ";
        }else{
            int a=  page.getPageNo()*20-20;
            sql = "select ks,xm,qjlx,qjts,qjksrq,qjjsrq,(select  COUNT(*)  from " + tableName+company+" ORDER BY update_date DESC) as 'count' from " + tableName+company+" ORDER BY update_date DESC limit "+a+","+page.getPageSize();
        }

      List<LeaveApplication> list  =leaveApplicationDao.findPage(sql);
        if(list.size() > 0) {
            page.setCount( list.get(0).getCount());
        }
        page.setList( list);
		return   page;
}

    @Transactional(readOnly = false)
	public void save(LeaveApplication leaveApplication) {
		super.save(leaveApplication);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeaveApplication leaveApplication) {
		super.delete(leaveApplication);
	}



    public Page<LeaveApplication> findLeaveApplication(Page<LeaveApplication> page, LeaveApplication leaveApplication) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        leaveApplication.getSqlMap().put("dsf", dataScopeFilter(leaveApplication.getCurrentUser(), "o", "a"));
        // 设置分页参数
        leaveApplication.setPage(page);
        // 执行分页查询
        List<LeaveApplication> leaveApplications =super.findList(leaveApplication);

        page.setList(leaveApplications);
        return page;
    }
}