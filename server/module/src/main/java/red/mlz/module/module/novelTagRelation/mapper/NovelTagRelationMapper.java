package red.mlz.module.module.novelTagRelation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;

import java.math.BigInteger;

@Mapper
public interface NovelTagRelationMapper {
    @Select("select * from novel_tag_relation where  novel_id = #{novelId}")
    NovelTagRelation extractByNovelId(@Param("novelId") BigInteger novelId);

    int insert(@Param("novelTagRelation") NovelTagRelation novelTagRelation);

    int update(@Param("novelTagRelation") NovelTagRelation novelTagRelation);

    @Update("update novel_tag_relation set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time")Integer time);
}
