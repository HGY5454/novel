package red.mlz.module.module.tags.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.tags.entity.Tag;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from tags where  id = #{id}")
    Tag extractById(@Param("id") BigInteger id);

    @Select("select * from tags where  tag_name = #{tagName}")
    Tag extractByTagName(@Param("tagName") String tagName);

    @Select("select * from tags")
    List<Tag> getAllTags();

    @Select("select id from tags where tag_name = #{tagName} and is_deleted = 0")
    int getTagIdByTagName(@Param("tagName") String tagName);

    int insert(@Param("tag") Tag tag);

    int update(@Param("tag") Tag tag);

    @Update("update tags set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time") Integer time);
}
