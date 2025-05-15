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

    @Select("select * from novel_tag_relation where  id = #{id} ")
    NovelTagRelation extractById(@Param("novelId") BigInteger id);

    int insert(@Param("novelTagRelation") NovelTagRelation novelTagRelation);

    int update(@Param("novelTagRelation") NovelTagRelation novelTagRelation);

    @Update("update novel_tag_relation set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time") Integer time);

    @Select("select * from novel_tag_relation where id=#{id} and is_deleted=0 ")
    NovelTagRelation getById(@Param("id") BigInteger id);

    @Select("select * from novel_tag_relation where novel_id = #{novelId} and tag_id = #{tagId} and is_deleted=0")
    NovelTagRelation selectByNovelIdAndTagsId(@Param("novelId") BigInteger novelId,
                                              @Param("tagId") BigInteger tagId);

    @Select("select * from novel_tag_relation where novel_id = #{novelId} and is_deleted=0")
    List<NovelTagRelation> selectByNovelId(@Param("novelId") BigInteger novelId);

    @Update("update novel_tag_relation set is_deleted = 1,update_time=#{time} where tag_id = #{tagId} and novel_id = #{novelId} limit 1")
    int deleteByTagId(@Param("tagId") BigInteger tagId,@Param("novelId")BigInteger novelId, @Param("time") Integer time);

}
