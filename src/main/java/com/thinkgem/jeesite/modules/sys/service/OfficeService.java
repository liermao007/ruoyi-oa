/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SynchroDataUtils;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 *
 * @author oa
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private DictDao dictDao;

    @Autowired
    private RoleDao roleDao;

    public List<Office> findAll() {
        return UserUtils.getOfficeList();
    }

    public List<Office> findList(Boolean isAll) {
        if (isAll != null && isAll) {
            return UserUtils.getOfficeAllList();
        } else {

            if (UserUtils.getUser().getId().equals("1")) {
                Office office = new Office();
                office.setParentIds("%" + (StringUtils.isBlank(office.getParentIds()) ? "" : office.getParentIds()) + "%");
                List<Office> list = dao.findByParentIdsLike(office);
                return list;
            } else {
                Office office = new Office();
                String compangyId = UserUtils.getUser().getCompany().getId();
                Office of = officeDao.get(compangyId);
                office.setParentIds(compangyId);
                List<Role> roles = roleDao.getByRoleName(UserUtils.getUser().getId());
                List<Office> list=null;
                if(roles.size()>0&&roles!=null){
                    if(roles.get(0).getDataScope().equals("2")){
                        office.setParentIds("%" + (StringUtils.isBlank(office.getParentIds()) ? "" : office.getParentIds()) + "%");//所在公司及以下数据
                        list = dao.findByParentIdsLike(office);
                        list.add(of);
                    } else  if(roles.get(0).getDataScope().equals("1")){
                        office.setParentIds("");//全部数据
                        list = dao.findByParentIdsLike(office);

                    }else{
                        office.setCompanyId(UserUtils.getUser().getCompany().getId());
                        office.setParentIds("," + UserUtils.getUser().getCompany().getId() + ",");
                        office.setCode("," + UserUtils.getUser().getCompany().getId());
                        list = officeDao.findByCompany(office);
                        list.add(of);
                    }
                }


                //return UserUtils.getOfficeList();
                return list;
            }
        }
    }
  /* public List<Office> findList(Boolean isAll){
       if (isAll != null && isAll){
           return UserUtils.getOfficeAllList();
       }else{
           return UserUtils.getOfficeList();
       }
   }*/

    @Transactional(readOnly = true)
    public List<Office> findList(Office office){
        if(office != null){
            office.setParentIds("%"+(StringUtils.isBlank(office.getParentIds()) ? "" :office.getParentIds())+"%");
            return dao.findByParentIdsLike(office);
        }
        return  new ArrayList<Office>();
    }

    public List<Office> findOfficeUserTreeData(Boolean isAll) {
        if (isAll != null && isAll) {
            return UserUtils.getOfficeAllList();
        } else {

            if (UserUtils.getUser().getId().equals("1")) {
                Office office = new Office();
                office.setParentIds("%" + (StringUtils.isBlank(office.getParentIds()) ? "" : office.getParentIds()) + "%");
                List<Office> list = dao.findOfficeUserTreeData(office);
                return list;
            } else {
                Office office = new Office();
                String compangyId = UserUtils.getUser().getCompany().getId();
                Office of = officeDao.get(compangyId);
                office.setParentIds(compangyId);
                office.setParentIds("%" + (StringUtils.isBlank(office.getParentIds()) ? "" : office.getParentIds()) + "%");
                List<Office> list = dao.findOfficeUserTreeData(office);
                list.add(of);
                //return UserUtils.getOfficeList();
                return list;
            }
        }
    }

    @Transactional(readOnly = true)
    public Office get(String compangyId) {

        return officeDao.get(compangyId);
    }



    @Transactional(readOnly = false)
    public void save(Office office) {
        super.save(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    /**
     * OA同步人力资源机构的数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public String saveOrg(Office entity) {

        @SuppressWarnings("unchecked")
        Class<Office> entityClass = Reflections.getClassGenricType(getClass(), 1);

        Office parentEntity = null;
        try {
            parentEntity = entityClass.getConstructor(String.class).newInstance(entity.getParentIds());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        entity.setParent(parentEntity);
        entity.getParent().setParentIds(StringUtils.EMPTY);
        entity.setParentIds(null);
        Area areaEntity = null;
        try {
            areaEntity = new Area();
            areaEntity.setId(entity.getAreaIdAmq());
            entity.setAddress(null);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        entity.setArea(areaEntity);

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = entity.getParentIds();

        // 设置新的父节点串
        entity.setParentIds(entity.getParent().getParentIds() + entity.getParent().getId() + ",");

        // 保存或更新实体
        dao.insert(entity);

        // 更新子节点 parentIds
        Office o = null;
        try {
            o = entityClass.newInstance();
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return entity.getId();
    }


    /**
     * OA同步人力资源的部门数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    private void saveDept(Office entity) {

        @SuppressWarnings("unchecked")
        Class<Office> entityClass = Reflections.getClassGenricType(getClass(), 1);
        Office parentEntity = null;
        try {
            parentEntity = entityClass.getConstructor(String.class).newInstance(entity.getParentIds());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        if (StringUtils.equalsIgnoreCase(entity.getParentIds(), "0")) {
            try {
                parentEntity = entityClass.getConstructor(String.class).newInstance(entity.getAddress());
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }
        entity.setParent(parentEntity);
        entity.getParent().setParentIds(StringUtils.EMPTY);
        entity.setParentIds(null);
        Area areaEntity = null;
        try {
            areaEntity = new Area();
            areaEntity.setId("2222");
//            entity.setAddress(null);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        entity.setArea(areaEntity);
//        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = entity.getParentIds();

        Office office = dao.get(entity.getAddress());

        // 设置新的父节点串

        entity.setParentIds(office.getParentIds() + entity.getParent().getId() + ",");

        // 保存或更新实体
        dao.insert(entity);
    }


    /**
     * OA同步人力资源的数据时，插入完机构后，将机构的parentIds更新
     *
     * @param office
     * @return
     * @author oa
     */
    @Transactional(readOnly = false)
    public int updateOrgParentIds(Office office) {
        String parentIds;
        if (StringUtils.isNotBlank(office.getParentIds())) {
            Office of = dao.get(office.getParent().getId());
            if (of != null) {
                parentIds = of.getParentIds() + office.getParent().getId() + ",";
                office.setParentIds(parentIds);
                office.preUpdate();
                dao.update(office);
            }
        } else {
            office.setParentIds("%%");
            List<Office> list = dao.findByParentIdsLike(office);
            for (Office e : list) {
                Office a = dao.get(e.getParent().getId());
                if (a != null) {
                    parentIds = a.getParentIds() + e.getParentIds();
                    e.setParentIds(parentIds);
                    e.preUpdate();
                    dao.update(e);
                }
            }
        }
        return 1;
    }


    @Transactional(readOnly = false)
    public void delete(Office office) {
        super.delete(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }


    /**
     * OA同步人力资源的部门数据的通用方法
     * 同时适用于webService接口调用和AMQ中间件使用
     *
     * @param type 用来判断是否新增或者修改
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public String saveDept(String type, String inputLine,String orgId) throws Exception {
        List<Map<String, Object>> result;
        if (StringUtils.isBlank(inputLine)) {
            String urlIP = DictUtils.getDValue("oa_hrm_ip", "");
            String urlString = DictUtils.getDValue("oa_dept_data", "");
            String url = urlIP + urlString;
            if (StringUtils.isNotBlank(orgId)) {
                url = url + "?orgId=" + orgId;
            }
            result = convertList(SynchroDataUtils.getJsonByUrl(url));
        } else {
            result = convertList(inputLine);
        }

        if (result != null) {
            List<Office> offices = findList(new Office());
            Map<String, Office> deptMap = Maps.uniqueIndex(offices, new Function<Office, String>() {
                @Override
                public String apply(Office office) {
                    return office.getId();
                }
            });
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    Office office = (Office) entry.getValue();
                    office.setType("2");
                    office.setGrade("2");
                    office.setUseable("1");
                    //使用webService同步数据以及修改数据
                    if (deptMap.get(office.getId()) == null) {
                        office.setDelFlag("0");
                        saveDept(office);
                    } else {
                        Office updateOffice = deptMap.get(office.getId());
                        office.setParent(updateOffice.getParent());
                        office.setParentIds(updateOffice.getParentIds());
                        office.preUpdate();
                        dao.update(office);
                    }
                }
            }
            return "success";
        }
        return null;
    }


    /**
     * OA同步人力资源的机构数据的其他类访问的方法
     *
     * @param type 用来判断是什么地方使用这个方法
     * @return
     * @throws IOException
     */
    @Transactional(readOnly = false)
    public String saveOrg(String type, String inputLine) throws Exception {
        String urlIP = DictUtils.getDValue("oa_hrm_ip", "");
        String urlString = DictUtils.getDValue("oa_org_data", "");
        String url = urlIP + urlString;
        List<Map<String, Object>> result;
        //判断是AMQ新增还是webService接口同步数据
        if (StringUtils.isBlank(inputLine)) {
            result = convertList(SynchroDataUtils.getJsonByUrl(url));
        } else {
            result = convertList(inputLine);
        }

        if (result != null) {
            List<Office> offices = findList(new Office());
            Map<String, Office> deptMap = Maps.uniqueIndex(offices, new Function<Office, String>() {
                @Override
                public String apply(Office office) {
                    return office.getId();
                }
            });
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = result.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    Office office = (Office) entry.getValue();
                    office.setType("1");
                    office.setGrade("1");
                    office.setUseable("1");
                    //使用webService同步数据以及修改数据
                    if (deptMap.get(office.getId()) == null) {
                        office.setDelFlag("0");
                        saveOrg(office);
                        Dict dict = new Dict();
                        //在插入机构的时候同时向“显示”机构的位置显示
                        if(StringUtils.isNotBlank(office.getName()) && StringUtils.isNotBlank(office.getId()) && office.getSort()!=null){
                            dict.setType("org_type");
                            dict.setValue(office.getId());
                            dict.setLabel(office.getName());
                            dict.setSort(office.getSort());
                            dict.setDescription("所有机构");
                            dict.setDelFlag("0");
                            dict.preInsert();
                            dictDao.insert(dict);
                        }
                        if (StringUtils.equalsIgnoreCase("add", type)) {
                            //AMQ新增同时新增部门
                            saveOrgVsDept(office);
                        }
                    } else {
                        Office updateOffice = deptMap.get(office.getId());
                        office.setParent(updateOffice.getParent());
                        office.setParentIds(updateOffice.getParentIds());
                        office.preUpdate();
                        dao.update(office);
                        if(StringUtils.isNotBlank(office.getName()) && StringUtils.isNotBlank(office.getId()) && office.getSort()!=null){
                            Dict dict = new Dict();
                            dict.setType("org_type");
                            dict.setValue(office.getId());
                            dict.setLabel(office.getName());
                            dict.setSort(office.getSort());
                            dict.setDescription("所有机构");
                            dict.setDelFlag("0");
                            List<Dict> dicts = dictDao.findAllList(dict);
                            if(dicts != null && dicts.size()>0){
                                dicts.get(0).setLabel(office.getName());
                                dicts.get(0).setSort(office.getSort());
                                dicts.get(0).preUpdate();
                                dictDao.update(dicts.get(0));
                            }
                        }
                    }
                }
            }
            if (StringUtils.equalsIgnoreCase("addOrg", type)) {
                updateOrgParentIds(new Office());
            }
            return "success";
        }
        return null;
    }


    /**
     * 转换，将人力资源传递的数据转成list集合
     *
     * @param json 人力资源地址,或者是AMQ传递的json数据
     * @return
     * @author oa
     */
    public List<Map<String, Object>> convertList(String json) {
        Map<String, String[]> replaceMap = new HashMap<>();
        String namespace = Office.class.getName();
        replaceMap.put("id", new String[]{namespace, "id"});
        replaceMap.put("name", new String[]{namespace, "name"});
        replaceMap.put("orgId", new String[]{namespace, "address"});
        replaceMap.put("parentId", new String[]{namespace, "parentIds"});
        replaceMap.put("sort", new String[]{namespace, "sort"});
        replaceMap.put("delFlag", new String[]{namespace, "delFlag"});
        replaceMap.put("createBy", new String[]{namespace, "createBy.id"});
        replaceMap.put("createDate", new String[]{namespace, "createDate"});
        replaceMap.put("updateBy", new String[]{namespace, "updateBy.id"});
        replaceMap.put("updateDate", new String[]{namespace, "updateDate"});
        return SynchroDataUtils.getDataByJson(json, replaceMap);
    }

    /**
     * OA同步人力资源数据时，同时保存机构和预设的部门数据
     *
     * @param office
     * @author oa
     */
    private void saveOrgVsDept(Office office) {
        String hr = saveOrg(office);
        int j = updateOrgParentIds(office);
        if (j != 0) {
            System.out.println("机构Id" + hr);
            try {
//                hr = connectWebService("oa_hrm_ip", "oa_dept_data", "?orgId=" + hr);
                //根据orgId查询出预设的部门，保存
                saveDept("add", hr,"");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 根据机构id查询当前机构下的部门
     * @param parentId  父id   用户的机构id
     * @return
     */
    public List<Office> findOfficeByOrgId(String parentId){
        return dao.findOfficeByOrgId(parentId);
    }

    /**
     * 根据机构id查询当前机构以及所有的下属机构
     * @param parentId  父id   用户的机构id
     * @return
     */
    public List<Office> findCompany(String parentId){
        return dao.findCompany(parentId);
    }


    /**
     * 查询当前部门下的所有人员的手机号
     * @param id
     * @return
     */
    public List<User> findPhone(String id){
        //根据当前的部门id查询下级部门的id
        StringBuilder sb = new StringBuilder();
        List<Office> offices = dao.getIdByParentId(id);
        if(offices.size() == 0){
            sb.append(id+",");
        }else{
            for(int j = 0;j<offices.size();j++){
                List<Office> officeList = dao.getIdByParentId(offices.get(j).getId());
                for(int i=0;i<officeList.size();i++){
                    sb.append(officeList.get(i).getId()+",");
                }
            }
        }
        String [] officeId = null;
        if(StringUtils.isNotBlank(sb.toString())){
            officeId= sb.substring(0,sb.length() - 1).split(",");
        }else{
            officeId = new String[1];
            officeId[0] = id;
        }

        List<User> users = dao.findPhone(officeId);
        return users;
    }

}
