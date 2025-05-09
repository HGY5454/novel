package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Param;
import java.math.BigInteger;
import java.util.List;

@Service
public class ${table.serviceName} {
    @Autowired
    private ${table.mapperName} ${table.entityPath}Mapper;

    public List<${entity}> getList${entity?cap_first}()
    {
        return ${table.mapperName?uncap_first}.getList${entity}();
    }

<#list table.fields as field>
    <#if field.keyFlag>

    public ${entity} get${entity?cap_first}InfoBy${field.propertyName?cap_first}( ${field.propertyType} ${field.propertyName}) {
        return ${table.entityPath}Mapper.getById(${field.propertyName});
    }
    </#if>
</#list>
<#list table.fields as field>
    <#if field.keyFlag>

        public ${entity} get${entity?cap_first}By${field.propertyName?cap_first}( ${field.propertyType} ${field.propertyName}) {
        return ${table.entityPath}Mapper.extractById(${field.propertyName});
        }
    </#if>
</#list>

<#list table.fields as field>
    <#if field.keyFlag>
        public int insert${entity?cap_first}(<#list table.fields as field><#if !field.keyFlag><#if field.propertyName != "createTime" &&field.propertyName != "updateTime" && field.propertyName != "isDeleted"><#if field_index==1>${field.propertyType} ${field.propertyName} <#else>, ${field.propertyType} ${field.propertyName}</#if></#if></#if></#list>) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        ${entity} ${entity?uncap_first} = new ${entity}();
        <#list table.fields as field>
            <#if !field.keyFlag>
                <#if field.propertyName != "createTime" &&field.propertyName != "updateTime" && field.propertyName != "isDeleted">
                    ${entity?uncap_first}.set${field.propertyName?cap_first}(${field.propertyName});
                </#if>
            </#if>
        </#list>
        ${entity?uncap_first}.setCreateTime(timestamp);
        ${entity?uncap_first}.setUpdateTime(timestamp);
        ${entity?uncap_first}.setIsDeleted(0);

        return ${table.entityPath}Mapper.insert(${table.entityPath});
        }
    </#if>
</#list>

    public int update${entity?cap_first}(<#list table.fields as field><#if field.propertyName != "createTime" &&field.propertyName != "updateTime" && field.propertyName != "isDeleted"><#if field_index==0>${field.propertyType} ${field.propertyName} <#else>, ${field.propertyType} ${field.propertyName}</#if></#if></#list>) {
<#list table.fields as field>
    <#if field.keyFlag>
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        ${entity} ${entity?uncap_first} = new ${entity}();
        <#list table.fields as field>
            <#if field.propertyName != "createTime" &&field.propertyName != "updateTime" && field.propertyName != "isDeleted">
        ${entity?uncap_first}.set${field.propertyName?cap_first}(${field.propertyName});
            </#if>
        </#list>
        ${entity?uncap_first}.setCreateTime(timestamp);
        ${entity?uncap_first}.setUpdateTime(timestamp);
        ${entity?uncap_first}.setIsDeleted(0);

        return ${table.entityPath}Mapper.update(${entity?uncap_first});
    }
    </#if>
</#list>

<#list table.fields as field>
    <#if field.keyFlag>

        public int delete${entity?cap_first}( ${field.propertyType} ${field.propertyName}) {
        return ${table.entityPath}Mapper.delete((int) (System.currentTimeMillis() / 1000),${field.propertyName});
        }
    </#if>
</#list>
}
