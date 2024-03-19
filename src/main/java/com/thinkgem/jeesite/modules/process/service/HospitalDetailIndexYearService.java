/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.process.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.process.entity.HospitalDetailIndexYear;
import com.thinkgem.jeesite.modules.process.dao.HospitalDetailIndexYearDao;

/**
 * 医院经营指标年表Service
 * @author oa
 * @version 2018-06-26
 */
@Service
@Transactional(readOnly = true)
public class HospitalDetailIndexYearService extends CrudService<HospitalDetailIndexYearDao, HospitalDetailIndexYear> {

	public HospitalDetailIndexYear get(String id) {
		return super.get(id);
	}
	
	public List<HospitalDetailIndexYear> findList(HospitalDetailIndexYear hospitalDetailIndexYear) {
		return super.findList(hospitalDetailIndexYear);
	}
	
	public Page<HospitalDetailIndexYear> findPage(Page<HospitalDetailIndexYear> page, HospitalDetailIndexYear hospitalDetailIndexYear) {
		return super.findPage(page, hospitalDetailIndexYear);
	}
	
	@Transactional(readOnly = false)
	public void save(HospitalDetailIndexYear hospitalDetailIndexYear) {
		super.save(hospitalDetailIndexYear);
	}
	
	@Transactional(readOnly = false)
	public void delete(HospitalDetailIndexYear hospitalDetailIndexYear) {
		super.delete(hospitalDetailIndexYear);
	}
	
}