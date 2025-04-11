package red.mlz.module.module.tag.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.tag.entity.Tag;
import red.mlz.module.module.tag.mapper.TagMapper;
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

    @Transactional(rollbackFor = Exception.class)
    public List<Tag> findAll() {
        return tagMapper.getAllTags();
    }

    @Transactional(rollbackFor = Exception.class)
    public int getTagIdByTagName(String tagName) {
        return tagMapper.getTagIdByTagName(tagName);
    }

    @Transactional(rollbackFor = Exception.class)
    public int edit( Tag tag) {
        Tag newTag = new Tag();
        newTag.setTagName(tag.getTagName());
        if (tag.getId() == null) {
            newTag.setUpdateTime(BaseUtils.currentSeconds());
            newTag.setCreateTime(BaseUtils.currentSeconds());
            newTag.setIsDeleted(0);
            return insert(tag);
        } else {
            newTag.setUpdateTime(BaseUtils.currentSeconds());
            return update(tag);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public int insert(Tag tag) {
        return tagMapper.insert(tag);
    }
    @Transactional(rollbackFor = Exception.class)
    public int update(Tag tag) {
        return tagMapper.update(tag);
    }
    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger id) {
        return tagMapper.delete(id, BaseUtils.currentSeconds());
    }
}
