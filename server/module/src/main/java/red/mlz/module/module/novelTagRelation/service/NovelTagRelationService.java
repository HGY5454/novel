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

    @Transactional(rollbackFor = Exception.class)
    public NovelTagRelation extract(BigInteger id) {
        return novelTagRelationMapper.extract(id);
    }
    @Transactional(rollbackFor = Exception.class)
    public NovelTagRelation selectByNovelIdAndTagId(BigInteger novelId, BigInteger tagId) {
        return novelTagRelationMapper.selectByNovelIdAndTagsId(novelId, tagId);
    }

    @Transactional(rollbackFor = Exception.class)
    public NovelTagRelation getById(BigInteger id) {
        return novelTagRelationMapper.getById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    public int edit(NovelTagRelation novelTagRelation) {
        NovelTagRelation newNovelTagRelation = new NovelTagRelation();
        newNovelTagRelation.setNovelId(novelTagRelation.getNovelId());
        newNovelTagRelation.setTagId(novelTagRelation.getTagId());
        newNovelTagRelation.setUpdateTime(BaseUtils.currentSeconds());
        newNovelTagRelation.setIsDeleted(0);
        if (novelTagRelation.getId() == null) {
            newNovelTagRelation.setCreateTime(BaseUtils.currentSeconds());
            return novelTagRelationMapper.insert(novelTagRelation);
        }else {
            if (getById(novelTagRelation.getId()) == null) {
                throw new RuntimeException("系统异常");
            }
            newNovelTagRelation.setId(novelTagRelation.getId());
            newNovelTagRelation.setUpdateTime(BaseUtils.currentSeconds());
            return novelTagRelationMapper.update(novelTagRelation);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<NovelTagRelation> selectByNovelId(BigInteger novelId) {
        return novelTagRelationMapper.selectByNovelId(novelId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteByTagId(BigInteger tagId, BigInteger novelId) {
        return novelTagRelationMapper.deleteByTagId(tagId,novelId, BaseUtils.currentSeconds());
    }
}
