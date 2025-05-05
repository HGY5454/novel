<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#list table.fields as field>
        <#if field.keyFlag>
            <insert id="insert"
                    parameterType="${package.Entity}.${entity}"
                    useGeneratedKeys="true"
                    keyProperty="${field.propertyName}">
                    insert into ${table.name}
                    (<#list table.fields as field>
                        <#if field_index gt 0  ></#if><if test="${table.entityPath}.${field.propertyName} !=null "> ${field.name}<#if field_has_next>,</#if></if>
                    </#list>)value(<#list table.fields as field>
                        <#if field_index gt 0></#if><if test="${table.entityPath}.${field.propertyName} !=null "> ${r"#{"}${table.entityPath}.${field.propertyName}${r"}"}<#if field_has_next>,</#if></if>
                    </#list>)
            </insert>
        </#if>
    </#list>
    <#list table.fields as field>
        <#if field.keyFlag>
            <update id="update"
                    parameterType="${package.Entity}.${entity}"
                    useGeneratedKeys="true"
                    keyProperty="${field.propertyName}">
                update ${table.name} set
                (<#list table.fields as field>
                <#if field_index gt 0 ></#if><if test="${table.entityPath}.${field.propertyName} !=null  and ${table.entityPath}.${field.propertyName} !=''"> ${field.name}=${r"#{"}${table.entityPath}.${field.propertyName}${r"}"}<#if field_has_next>,</#if></if>
                </#list>)
                WHERE
                <#list table.fields as field><#if field.keyFlag>${field.name}=${r"#{"}${table.entityPath}.${field.propertyName}${r"}"}<#if field_has_next>,</#if></#if></#list>
            </update>
        </#if>
    </#list>
</mapper>
