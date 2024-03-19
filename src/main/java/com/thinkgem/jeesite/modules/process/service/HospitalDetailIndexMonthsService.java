/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.entity.HospitalDetailIndexMonths;
import com.thinkgem.jeesite.modules.process.dao.HospitalDetailIndexMonthsDao;

/**
 * 医院经营指标月表Service
 * @author oa
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly = true)
public class HospitalDetailIndexMonthsService extends CrudService<HospitalDetailIndexMonthsDao, HospitalDetailIndexMonths> {

	public HospitalDetailIndexMonths get(String id) {
		return super.get(id);
	}
	
	public List<HospitalDetailIndexMonths> findList(HospitalDetailIndexMonths hospitalDetailIndexMonths) {
		return super.findList(hospitalDetailIndexMonths);
	}
	
	public Page<HospitalDetailIndexMonths> findPage(Page<HospitalDetailIndexMonths> page, HospitalDetailIndexMonths hospitalDetailIndexMonths) {
		return super.findPage(page, hospitalDetailIndexMonths);
	}
	
	@Transactional(readOnly = false)
	public void save(HospitalDetailIndexMonths hospitalDetailIndexMonths) {
		super.save(hospitalDetailIndexMonths);
	}
	
	@Transactional(readOnly = false)
	public void delete(HospitalDetailIndexMonths hospitalDetailIndexMonths) {
		super.delete(hospitalDetailIndexMonths);
	}
	
}