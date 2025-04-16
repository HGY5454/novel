package red.mlz.module.module.novelTagRelation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.mapper.NovelTagRelationMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class NovelTagRelationService {
    @Resource
    private NovelTagRelationMapper novelTagRelationMapper;

    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger id) {
        return novelTagRelationMapper.delete(id, BaseUtils.currentSeconds());
    }

    public NovelTagRelation extract(BigInteger id) {
        return novelTagRelationMapper.extractById(id);
    }
    public NovelTagRelation selectByNovelIdAndTagId(BigInteger novelId, BigInteger tagId) {
        return novelTagRelationMapper.selectByNovelIdAndTagsId(novelId, tagId);
    }

    public NovelTagRelation getById(BigInteger id) {
        return novelTagRelationMapper.getById(id);
    }

    public BigInteger edit(NovelTagRelation novelTagRelation) {
        NovelTagRelation newNovelTagRelation = new NovelTagRelation();
        if (novelTagRelation.getId() == null) {
            newNovelTagRelation.setNovelId(novelTagRelation.getNovelId());
            newNovelTagRelation.setTagId(novelTagRelation.getTagId());
            newNovelTagRelation.setCreateTime(BaseUtils.currentSeconds());
            newNovelTagRelation.setUpdateTime(BaseUtils.currentSeconds());
            newNovelTagRelation.setIsDeleted(0);
            return insert(newNovelTagRelation);
        }else {
            if (getById(novelTagRelation.getId()) == null) {
                throw new RuntimeException("系统异常");
            }
            newNovelTagRelation.setId(novelTagRelation.getId());
            newNovelTagRelation.setNovelId(novelTagRelation.getNovelId());
            newNovelTagRelation.setTagId(novelTagRelation.getTagId());
            newNovelTagRelation.setUpdateTime(BaseUtils.currentSeconds());
            newNovelTagRelation.setIsDeleted(0);
            return update(newNovelTagRelation);
        }
    }
    public BigInteger insert(NovelTagRelation novelTagRelation) {
        novelTagRelationMapper.insert(novelTagRelation);
        return novelTagRelation.getId();
    }
    public BigInteger update(NovelTagRelation novelTagRelation) {
        novelTagRelationMapper.update(novelTagRelation);
        return novelTagRelation.getId();
    }

    public List<NovelTagRelation> selectByNovelId(BigInteger novelId) {
        return novelTagRelationMapper.selectByNovelId(novelId);
    }

    public int deleteByTagId(BigInteger tagId, BigInteger novelId) {
        return novelTagRelationMapper.deleteByTagId(tagId,novelId, BaseUtils.currentSeconds());
    }
}
