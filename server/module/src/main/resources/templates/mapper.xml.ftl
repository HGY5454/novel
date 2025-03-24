<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <insert id="insert"
            useGeneratedKeys="true"
            keyProperty="id"
            parameterType="${package.Entity}.${entity}">
        insert into ${table.name}(
        <#list table.fields as field>
            <#if field.propertyName == "id">
            <#else>
        <if test="${entity}.${field.propertyName} != null and ${entity}.${field.propertyName} != ''">${field.propertyName}<#if field_has_next>,</#if></if>
            </#if>
        </#list>
        )values(
        <#list table.fields as field>
            <#if field.propertyName == "id">
            <#else>
        <if test="${entity}.${field.propertyName} != null and ${entity}.${field.propertyName} != ''"><#noparse>#{</#noparse>${entity}.${field.propertyName}<#noparse>}</#noparse><#if field_has_next>,</#if></if>
            </#if>
        </#list>
        )
    </insert>

    <update id="update"
            parameterType="${package.Entity}.${entity}">
        update ${table.name}
        set id = <#noparse>#{</#noparse>${entity}.id<#noparse>}</#noparse>
        <#list table.fields as field>
            <#if field.propertyName == "id">
            <#else>
                <if test="${entity}.${field.propertyName} != null and ${entity}.${field.propertyName} != ''"> , ${field.propertyName} = <#noparse>#{</#noparse>${entity}.${field.propertyName}<#noparse>}</#noparse></if>
            </#if>
        </#list>
        where id = <#noparse>#{</#noparse>${entity}.id<#noparse>}</#noparse>
    </update>



</mapper>

