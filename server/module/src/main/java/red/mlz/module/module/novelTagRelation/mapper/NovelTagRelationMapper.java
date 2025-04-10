package red.mlz.module.module.novelTagRelation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface NovelTagRelationMapper {
    @Select("select * from novel_tag_relation where  novel_id = #{novelId} ")
    NovelTagRelation extractByNovelId(@Param("novelId") BigInteger novelId);

    int insert(@Param("novelTagRelation") NovelTagRelation novelTagRelation);

    int update(@Param("novelTagRelation") NovelTagRelation novelTagRelation);

    @Select("SELECT * FROM novel_tag_relation WHERE novel_id = #{novelId} AND tag_id = #{tagId} and is_deleted=0")
    NovelTagRelation SelectByNovelIdaAndTagsId(@Param("novelId") BigInteger novelId,
                                               @Param("tagId") BigInteger tagId);

    @Select("SELECT * FROM novel_tag_relation WHERE novel_id = #{novelId} and is_deleted=0")
    List<NovelTagRelation> SelectByNovelId(@Param("novelId") BigInteger novelId);

    @Update("update novel_tag_relation set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time") Integer time);

    @Update("update novel_tag_relation set is_deleted = 1,update_time=#{time} where tag_id = #{tagId} and novel_id = #{novelId} limit 1")
    int deleteByTagId(@Param("tagId") BigInteger tagId,@Param("novelId")BigInteger novelId, @Param("time") Integer time);

}
