/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.LeaveApplication;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.TravelApplication;
import com.thinkgem.jeesite.modules.oa.dao.TravelApplicationDao;

/**
 * 出差统计表Service
 * @author oa
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true)
public class TravelApplicationService extends CrudService<TravelApplicationDao, TravelApplication> {

    @Autowired
    private TravelApplicationDao travelApplicationDao;
	public TravelApplication get(String id) {
		return super.get(id);
	}
	

	
	public Page<TravelApplication> findList1(Page<TravelApplication> page, String type,TravelApplication travelApplication,String tableName,String ccrq) {

        StringBuffer company = new StringBuffer();
        if(StringUtils.isNotBlank(type)&&type.equals("2")){
            String companyId=  UserUtils.getUser().getCompany().getId();
            company.append(" where del_flag= '0'and companyid= "+"'"+companyId+"'");
        }else{
            company.append(" where del_flag= '0'");
        }
        if(StringUtils.isNotBlank(travelApplication.getBm())){
            company.append( " AND bm= "+"'"+travelApplication.getBm()+"'") ;
        }
        if(travelApplication.getCcrq()!=null){
            company.append( " AND ccrq = "+"'"+ccrq+"'") ;
        }
        travelApplication.setPage(page);
        String sql;
        if(page.getPageNo()<=1){
            sql = "select bm,xm,ccrq,ccdd,ccsy,(select  COUNT(*)  from " + tableName+company+" ORDER BY update_date DESC) as 'count' from " + tableName+company+" ORDER BY update_date DESC ";
        }else{
            int a=  page.getPageNo()*20-20;
            sql = "select bm,xm,ccrq,ccdd,ccsy,(select  COUNT(*)  from " + tableName+company+" ORDER BY update_date DESC) as 'count' from " + tableName+company+" ORDER BY update_date DESC limit "+a+","+page.getPageSize();
        }

        List<TravelApplication> list=  travelApplicationDao.findPage(sql);
        if(list.size()>0){
            page.setCount( list.get(0).getCount());
        }

        page.setList(list);


		return  page;
	}
	
	@Transactional(readOnly = false)
	public void save(TravelApplication travelApplication) {
		super.save(travelApplication);
	}
	
	@Transactional(readOnly = false)
	public void delete(TravelApplication travelApplication) {
		super.delete(travelApplication);
	}


    public Page<TravelApplication> findTravelApplication(Page<TravelApplication> page, TravelApplication travelApplication,String tableName) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        travelApplication.getSqlMap().put("dsf", dataScopeFilter(travelApplication.getCurrentUser(), "o", "a"));
        // 设置分页参数
        travelApplication.setPage(page);
        // 执行分页查询
        StringBuffer company = new StringBuffer();
        String companyId=  UserUtils.getUser().getCompany().getId();
        company.append(" where companyid= "+"'"+companyId+"'");
        if(StringUtils.isNotBlank(travelApplication.getBm())){
            company.append( " AND bm= "+"'"+travelApplication.getBm()+"'") ;
        }
        if(travelApplication.getCcrq()!=null){
            company.append( " AND ccrq = "+"'"+travelApplication.getCcrq()+"'") ;
        }

        String sql = "select * from " + tableName+company+" ORDER BY update_date DESC ";
        List<TravelApplication> travelApplications =travelApplicationDao.findPage(sql);

        page.setList(travelApplications);
        return page;
    }
	
}