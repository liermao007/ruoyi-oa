package com.thinkgem.jeesite.modules.form.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 自定义组件
 * @author oa
 * @version 2016-12-16
 */
@XmlRootElement(name = "Component")
@XmlAccessorType(XmlAccessType.FIELD)
    public class Component implements Serializable{

    private static final long serialVersionUID = 1L;
    @XmlElement(name = "value")
    private String value;
    @XmlElement(name = "type")
    private String type;
    @XmlElement(name = "name")
    private String name;   // 组件名称
    @XmlElement(name = "content")
    private String content;   // 组件内容
    @XmlElement(name = "initMethod")
    private String initMethod;    //初始方法
    @XmlElement(name = "initValue")
    private String initValue;     //初始值

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }
}
