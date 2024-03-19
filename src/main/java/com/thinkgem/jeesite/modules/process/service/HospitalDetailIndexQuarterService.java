/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.entity.HospitalDetailIndexQuarter;
import com.thinkgem.jeesite.modules.process.dao.HospitalDetailIndexQuarterDao;

/**
 * 医院经营指标季度表Service
 * @author oa
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly = true)
public class HospitalDetailIndexQuarterService extends CrudService<HospitalDetailIndexQuarterDao, HospitalDetailIndexQuarter> {

	public HospitalDetailIndexQuarter get(String id) {
		return super.get(id);
	}
	
	public List<HospitalDetailIndexQuarter> findList(HospitalDetailIndexQuarter hospitalDetailIndexQuarter) {
		return super.findList(hospitalDetailIndexQuarter);
	}
	
	public Page<HospitalDetailIndexQuarter> findPage(Page<HospitalDetailIndexQuarter> page, HospitalDetailIndexQuarter hospitalDetailIndexQuarter) {
		return super.findPage(page, hospitalDetailIndexQuarter);
	}
	
	@Transactional(readOnly = false)
	public void save(HospitalDetailIndexQuarter hospitalDetailIndexQuarter) {
		super.save(hospitalDetailIndexQuarter);
	}
	
	@Transactional(readOnly = false)
	public void delete(HospitalDetailIndexQuarter hospitalDetailIndexQuarter) {
		super.delete(hospitalDetailIndexQuarter);
	}
	
}