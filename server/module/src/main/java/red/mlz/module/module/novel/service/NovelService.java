package red.mlz.module.module.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.mapper.NovelMapper;
import org.springframework.stereotype.Service;
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.mapper.NovelTagRelationMapper;
import red.mlz.module.module.novelTagRelation.service.NovelTagRelationService;
import red.mlz.module.module.tags.entity.Tag;
import red.mlz.module.module.tags.mapper.TagsMapper;
import red.mlz.module.module.tags.service.TagsService;
import red.mlz.module.utils.BaseUtils;
import red.mlz.module.utils.Response;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class NovelService {
    @Resource
    private NovelMapper mapper;

    TagsService tagsService = new TagsService();

    NovelTagRelationService novelTagRelationService = new NovelTagRelationService();

    public Novel getById(BigInteger id) {
        return mapper.getById(id);
    }

    public Novel extractById(BigInteger id) {
        return mapper.extractById(id);
    }

    public List<Novel> getNovelByKindsId(BigInteger kindsId) { return mapper.getByKindsId(kindsId);}

    public Integer getNovelCount(String titleKeyWord,String ids) {
        return mapper.getCount(titleKeyWord,ids);
    }

    public List<String> getKindsIdByKeyword(String keyword) { return mapper.getKindsIdByKeyWord(keyword);}

    public List<Novel> getNovelListByPage(Integer page, Integer pageSize, String titleKeyWord,String ids) {
        Integer start = (page-1)*pageSize;
        return mapper.getPage(titleKeyWord,start,pageSize,ids);
    }

    public List<Novel> getAllNovel() {
        return mapper.getAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger editNovel(BigInteger novelId,String title, String images,String author,Float score,Integer worldCount,String synopsis,BigInteger kindsId,String tags) {

        int timestamp = (int) (System.currentTimeMillis() / 1000);

        Novel novel = new Novel();

        if(title == null || title.length() > 50){
            throw new RuntimeException("title长度应在0~50之间");
        }
        if(images == null || images.length() > 400){
            throw new RuntimeException("images长度应在0~400之间");
        }
        if(author == null || author.isEmpty() || author.length() >= 10){
            throw new RuntimeException("author长度应在0~10之间");
        }
        if(score>=10) {
            throw new RuntimeException("score的值应该在0~10");
        }
        if(worldCount<0) {
            throw new RuntimeException("wordCount的值应大于0");
        }
        if(synopsis == null || synopsis.length() > 50){
            throw new RuntimeException("synopsis长度应在0~50之间");
        }
        novel.setTitle(title);
        novel.setImagesUrl(images);
        novel.setAuthor(author);
        novel.setScore(score);
        novel.setWordCount(worldCount);
        novel.setSynopsis(synopsis);
        novel.setKindsId(kindsId);
        novel.setUpdateTime(timestamp);

        List<BigInteger> tagIds = new ArrayList<>();
        for(String tagName : tags.split(",")){
            Tag tag = tagsService.extractByTagName(tagName);
            if (tag == null){
                tag.setTagName(tagName);
                tag.setCreateTime(timestamp);
                tag.setUpdateTime(timestamp);
                tag.setIsDelete(0);
                BigInteger tagId = tagsService.insert(tag);
                tagIds.add(tagId);
            }else{
                tagIds.add(tag.getId());
            }
        }

        if(novelId != null) {
           if(getById(novelId) == null) {
              throw new RuntimeException("系统异常");
           }
           novel.setId(novelId);
           update(novel);
        }else{
           novel.setCreateTime(timestamp);
           novel.setIsDeleted(0);
           insert(novel);
        }
        try{
            for(BigInteger tagId : tagIds) {
                if(novelTagRelationService.SelectByNovelIdaAndTagsId(novelId,tagId) == null) {
                   NovelTagRelation novelTagRelation = new NovelTagRelation();
                   novelTagRelation.setNovelId(novelId);
                   novelTagRelation.setTagId(tagId);
                   novelTagRelation.setCreateTime(BaseUtils.currentSeconds());
                   novelTagRelation.setUpdateTime(BaseUtils.currentSeconds());
                   novelTagRelation.setIsDelete(0);
                   novelTagRelationService.insert(novelTagRelation);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return novel.getId();
    }
    public int update(Novel novel) {
        return mapper.update(novel);
    }

    public int insert(Novel novel) {
        return mapper.insert(novel);
    }

    public int delete(BigInteger novelId) {
        return mapper.delete(novelId,(int) (System.currentTimeMillis() / 1000));
    }

    public int updateInfo(BigInteger id,String info){
        return mapper.updateInfo(id,info);
    }

    public String getNovelInfo(BigInteger id) {
        return mapper.getInfo(id);
    }
}