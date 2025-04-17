package red.mlz.module.module.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.kinds.service.KindsService;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.mapper.NovelMapper;
import org.springframework.stereotype.Service;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.service.NovelTagRelationService;
import red.mlz.module.module.tag.entity.Tag;
import red.mlz.module.module.tag.service.TagService;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NovelService {
    @Resource
    private NovelMapper mapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private NovelTagRelationService novelTagRelationService;
    @Autowired
    private KindsService kindsService;

    public Novel getById(BigInteger id) {
        return mapper.getById(id);
    }

    public Novel extractById(BigInteger id) {
        return mapper.extractById(id);
    }

    public List<Novel> getNovelByKindsId(BigInteger kindsId) {
        return mapper.getByKindsId(kindsId);
    }

    public Integer getNovelCount(String titleKeyWord, String ids) {
        return mapper.getCount(titleKeyWord, ids);
    }

    public List<String> getKindsIdByKeyword(String keyword) {
        return mapper.getKindsIdByKeyWord(keyword);
    }

    public List<Novel> getNovelListByPage(Integer page, Integer pageSize, String titleKeyWord, String ids) {
        Integer start = (page - 1) * pageSize;
        return mapper.getPage(titleKeyWord, start, pageSize, ids);
    }

    public List<Novel> getAllNovel() {
        return mapper.getAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger editNovel(BigInteger novelId, String title, String images, String author, Float score, Integer worldCount, String synopsis, BigInteger kindsId, String tags) {

        int timestamp = (int) (System.currentTimeMillis() / 1000);

        if (title == null || title.length() > 50) {
            throw new RuntimeException("title长度应在0~50之间");
        }
        if (images == null || images.length() > 400) {
            throw new RuntimeException("images长度应在0~400之间");
        }
        if (author == null || author.isEmpty() || author.length() >= 10) {
            throw new RuntimeException("author长度应在0~10之间");
        }
        if (score >= 10) {
            throw new RuntimeException("score的值应该在0~10");
        }
        if (worldCount < 0) {
            throw new RuntimeException("wordCount的值应大于0");
        }
        if (synopsis == null || synopsis.length() > 50) {
            throw new RuntimeException("synopsis长度应在0~50之间");
        }
        if (kindsService.getKindsById(kindsId) == null){
            throw new RuntimeException("分类不存在");
        }

        Novel novel = new Novel();
        novel.setTitle(title);
        novel.setImagesUrl(images);
        novel.setAuthor(author);
        novel.setScore(score);
        novel.setWordCount(worldCount);
        novel.setSynopsis(synopsis);
        novel.setKindsId(kindsId);
        novel.setUpdateTime(timestamp);

        if (novelId != null) {
            if (getById(novelId) == null) {
                throw new RuntimeException("系统异常");
            }
            novel.setId(novelId);
            update(novel);
        } else {
            novel.setCreateTime(timestamp);
            novel.setIsDeleted(0);
            insert(novel);
            novelId = novel.getId();
        }

        List<BigInteger> tagIds = new ArrayList<>();
        for (String tagName : tags.split(",")) {
            Tag existingTag = tagService.getTagByTagName(tagName);
            if (existingTag == null) {
                Tag newTag = new Tag();
                newTag.setTagName(tagName.trim());
                newTag.setCreateTime(timestamp);
                newTag.setUpdateTime(timestamp);
                newTag.setIsDeleted(0);
                tagIds.add(tagService.edit(newTag.getId(), newTag.getTagName()));
            } else {
                tagIds.add(existingTag.getId());
            }
        }


        try {
            List<NovelTagRelation> novelTagRelationList = novelTagRelationService.selectByNovelId(novelId);
            List<BigInteger> originalTagIds = new ArrayList<>();
            for (NovelTagRelation novelTagRelation : novelTagRelationList) {
                originalTagIds.add(novelTagRelation.getTagId());
            }

            List<BigInteger> updateTagIds= originalTagIds.stream()
                    .filter(e -> tagIds.contains(e))
                    .collect(Collectors.toList());

            List<BigInteger> deleteTagIds = new ArrayList<>(originalTagIds);
            deleteTagIds.removeAll(updateTagIds);
            List<BigInteger> createTagIds = new ArrayList<>(tagIds);
            createTagIds.removeAll(updateTagIds);

            if (deleteTagIds.size() > 0) {
                for (BigInteger tagId : deleteTagIds) {
                    novelTagRelationService.deleteByTagId(tagId,novelId);
                }
            }
            if (createTagIds.size() > 0) {
                for (BigInteger tagId : createTagIds) {
                    BigInteger id = null;
                    novelTagRelationService.edit(id,novelId,tagId);
                }
            }
            if (updateTagIds.size() > 0) {
                for (BigInteger tagId : updateTagIds) {
                    NovelTagRelation novelTagRelation = novelTagRelationService.selectByNovelIdAndTagId(novelId, tagId);
                    novelTagRelationService.edit(novelTagRelation.getId(),novelId,tagId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return novel.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(Novel novel) {
        return mapper.update(novel);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insert(Novel novel) {
        return mapper.insert(novel);
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger novelId) {
        return mapper.delete(novelId, (int) (System.currentTimeMillis() / 1000));
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateInfo(BigInteger id, String info) {
        return mapper.updateInfo(id, info);
    }

    public String getNovelInfo(BigInteger id) {
        return mapper.getInfo(id);
    }
}