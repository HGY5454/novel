package ${package.Mapper};

import ${package.Entity}.${entity};
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.math.BigInteger;

@Mapper
public interface ${table.mapperName}{

        @Select("select * from ${entity?uncap_first} where is_deleted = 0")
        List<${entity}>  getList${entity}();

<#list table.fields as field>
    <#if field.keyFlag>

        @Select("select * from ${entity?uncap_first} where  ${field.propertyName} = #${r'{'}${field.propertyName}}")
        ${entity} getById(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});
    </#if>
</#list>
<#list table.fields as field>
    <#if field.keyFlag>

        @Select("select * from ${entity?uncap_first} where  ${field.propertyName} = #${r'{'}${field.propertyName}}")
        ${entity} extractById(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});
    </#if>
</#list>
<#list table.fields as field>
    <#if field.keyFlag>

        int insert(@Param("${table.entityPath}") ${entity} ${table.entityPath});
    </#if>
</#list>
<#list table.fields as field>
    <#if field.keyFlag>

        int update(@Param("${table.entityPath}") ${entity} ${table.entityPath});
    </#if>
</#list>
<#list table.fields as field>
    <#if field.keyFlag>

        @Update("update ${entity} set is_deleted = 1 and update_time=#${r'{'}timestamp} where ${field.propertyName} = #${r'{'}${field.propertyName}} limit 1")
        int delete(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName},
                   @Param("timestamp")int timestamp);
    </#if>
</#list>
}
