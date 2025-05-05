package red.mlz.module;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test?allowPublicKeyRetrieval=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&useAffectedRows=true", "root", "123456")
                .globalConfig(builder -> builder
                        .disableOpenDir()
                        .enableSwagger()
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/module/src/main/java/test")
                )
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.BIGINT) {
                                return DbColumnType.BIG_INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .injectionConfig(consumer ->{
                    Map<String, String> customFile = new HashMap<>();
                    customFile.put(".java", "/templates/entity.java.ftl");
                    customFile.put("Service.java", "/templates/service.java.ftl");
                    customFile.put("Controller.java", "/templates/controller.java.ftl");
                    customFile.put("Mapper.java", "/templates/mapper.java.ftl");
                    customFile.put("Mapper.xml", "/templates/mapper.xml.ftl");
                    consumer.customFile(customFile);
                })
                .packageConfig(builder -> builder
                        .parent("")
                        .pathInfo(Collections.singletonMap(OutputFile.service,"/module/src/main/java/test"))
                        .pathInfo(Collections.singletonMap(OutputFile.xml,"/module/src/main/java/test"))
                        .pathInfo(Collections.singletonMap(OutputFile.mapper,"/module/src/main/java/test"))
                        .pathInfo(Collections.singletonMap(OutputFile.controller,"/module/src/main/java/test"))
                        .pathInfo(Collections.singletonMap(OutputFile.entity,"/module/src/main/java/test"))
                )

                .strategyConfig(builder -> builder
                        .likeTable(new LikeTable("sms_task"))
                        .controllerBuilder()
                        .disable()
                        .entityBuilder()
                        .disable()
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .disable()
                        .mapperBuilder()
                        .disableMapper()
                        .disableMapperXml()
                        .build()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }


}

