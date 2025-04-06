package red.mlz.module.module.tags.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.tags.entity.Tag;
import red.mlz.module.module.tags.mapper.TagMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class TagService {
    @Resource
    private TagMapper tagMapper;

    public Tag extractById(BigInteger id) {
        return tagMapper.extractById(id);
    }

    public Tag extractByTagName(String tagName) {
        return tagMapper.extractByTagName(tagName);
    }

    public List<Tag> findAll() {
        return tagMapper.getAllTags();
    }

    public int getTagIdByTagName(String tagName) {
        return tagMapper.getTagIdByTagName(tagName);
    }

    public BigInteger insert(Tag tag) {
        tagMapper.insert(tag);
        return tag.getId();
    }
    public int edit(Tag tag) {
        if (tag.getId() == null) {
            return tagMapper.insert(tag);
        }else{
            return tagMapper.update(tag);
        }
    }
    public int update(Tag tags) {
        return tagMapper.update(tags);
    }

    public int delete(BigInteger id) {
        return tagMapper.delete(id, BaseUtils.currentSeconds());
    }
}
