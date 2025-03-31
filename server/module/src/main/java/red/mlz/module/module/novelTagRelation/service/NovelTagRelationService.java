package red.mlz.module.module.novelTagRelation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.mapper.NovelTagRelationMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;

@Service
public class NovelTagRelationService {
    @Resource
    private NovelTagRelationMapper novelTagRelationMapper;

    @Transactional(rollbackFor = Exception.class)
    public int insert(NovelTagRelation novelTagRelation) {
        return novelTagRelationMapper.insert(novelTagRelation);
    }
    @Transactional(rollbackFor = Exception.class)
    public int update(NovelTagRelation  novelTagRelation) {
        return novelTagRelationMapper.update(novelTagRelation);
    }
    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger id) {
        return novelTagRelationMapper.delete(id, BaseUtils.currentSeconds());
    }
    @Transactional(rollbackFor = Exception.class)
    public NovelTagRelation SelectByNovelIdaAndTagsId(BigInteger novelId, BigInteger tagId) {
        return novelTagRelationMapper.SelectByNovelIdaAndTagsId(novelId, tagId);
    }
}
