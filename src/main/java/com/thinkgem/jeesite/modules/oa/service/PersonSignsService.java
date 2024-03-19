/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.dao.PersonSignsDao;
import com.thinkgem.jeesite.modules.oa.entity.PersonSigns;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 个人签名Service
 * @author oa
 * @version 2017-02-06
 */
@Service
@Transactional(readOnly = true)
public class PersonSignsService extends CrudService<PersonSignsDao, PersonSigns> {

	public PersonSigns get(String id) {
		return super.get(id);
	}
	
	public List<PersonSigns> findList(PersonSigns personSigns) {
		return super.findList(personSigns);
	}
	
	public Page<PersonSigns> findPage(Page<PersonSigns> page, PersonSigns personSigns) {
		return super.findPage(page, personSigns);
	}

    /**
     *保存带有图片路径的个人签名
     * @param file
     * @param name
     * @param signName
     * @param request
     * @throws java.io.IOException
     */
    @Transactional(readOnly = false)
    public void saveContractFile(MultipartFile file, String name,String signName,String id, HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        String filePath = null ;
        String realPath = request.getSession().getServletContext().getRealPath("/static"+File.separator+"userfiles");
        System.out.println(file.getOriginalFilename());
        String filename=file.getOriginalFilename();
        System.out.println(filename.lastIndexOf("."));
//        filename=filename.substring(filename.lastIndexOf("."));
        filename= UserUtils.getUser().getId()+"signs"+filename;
        System.out.println(filename);
        File targetFile = new File(realPath+File.separator+"contract") ;
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        File desFile = new File(realPath+File.separator+"contract"+File.separator+filename) ;
        if (!desFile.exists()){
            desFile.createNewFile() ;
        }
        filePath = desFile.getPath();
        System.out.println("路径："+filePath);
        filePath = filePath.substring(filePath.indexOf("webapp")+6);
        FileOutputStream fileOutputStream = new FileOutputStream(desFile) ;
        String s = filePath.replaceAll("\\\\", "\\/");
        byte[] bytes = new byte[1024] ;
        while(!(inputStream.read(bytes)== -1)){
            fileOutputStream.write(bytes);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
        //文件保存成功


        PersonSigns personSigns = new PersonSigns();
        if(StringUtils.isNotBlank(id)){
            personSigns.setSignUrl(s.substring(s.lastIndexOf("static")-1));
            personSigns.setId(id);
            personSigns.setName(name);
            personSigns.setSignName(signName);
            personSigns.setLoginId(UserUtils.getUser().getId());
            personSigns.preUpdate();
            dao.update(personSigns);
        }else{
            System.out.println(s.substring(s.lastIndexOf("static")-1));
            personSigns.setSignUrl(s.substring(s.lastIndexOf("static") - 1));
            personSigns.setName(name);
            personSigns.setSignName(signName);
            personSigns.setLoginId(UserUtils.getUser().getId());
            personSigns.preInsert();
            dao.insert(personSigns);
        }

    }

	
	@Transactional(readOnly = false)
	public void delete(PersonSigns personSigns) {
		super.delete(personSigns);
	}

    public PersonSigns getLoginId(String loginId) {
        return dao.getLoginId(loginId);
    }
	
}