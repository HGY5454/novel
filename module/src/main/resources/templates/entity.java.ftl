package ${package.Entity};
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;
@Data
@Accessors(chain = true)
public class ${entity}{
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
        private ${field.propertyType} ${field.propertyName};
</#list>
}
