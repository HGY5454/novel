package red.mlz.module.module.tag.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.tag.entity.Tag;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from tag where  id = #{id}")
    Tag extractById(@Param("id") BigInteger id);

    @Select("select * from tag where  tag_name = #{tagName}")
    Tag extractByTagName(@Param("tagName") String tagName);

    @Select("select * from tag")
    List<Tag> getAllTags();

    @Select("select id from tag where tag_name = #{tagName} and is_deleted = 0")
    int getTagIdByTagName(@Param("tagName") String tagName);

    BigInteger insert(@Param("tag") Tag tag);

    BigInteger update(@Param("tag") Tag tag);

    @Update("update tag set is_deleted = 1,update_time=#{time} where id = #{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time") Integer time);
}
