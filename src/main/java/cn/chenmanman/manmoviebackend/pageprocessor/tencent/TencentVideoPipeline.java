package cn.chenmanman.manmoviebackend.pageprocessor.tencent;

import cn.chenmanman.manmoviebackend.domain.entity.movie.EpisodesEntity;
import cn.chenmanman.manmoviebackend.domain.entity.movie.MovieInfoEntity;
import cn.chenmanman.manmoviebackend.mapper.EpisodesMapper;
import cn.chenmanman.manmoviebackend.mapper.MovieInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.pageprocessor
 * @className TencentVideoPipeline
 * @description 腾讯视频入库
 * @date 2023/5/6 12:00
 */
@Slf4j
@Component
public class TencentVideoPipeline implements Pipeline {

    @Resource
    private MovieInfoMapper movieInfoMapper;

    @Resource
    private EpisodesMapper episodesMapper;

    @Override
    @Transactional
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> resultItemsAll = resultItems.getAll();
        List<Map<String,Object>> movieInfo = (List<Map<String, Object>>) resultItemsAll.get("movie_info");
        for (Object map: movieInfo) {
            Map<String, String> item = (Map<String, String>) map;
            MovieInfoEntity movieInfoEntity = new MovieInfoEntity();
            movieInfoEntity.setName(item.get("title"));
            movieInfoEntity.setScore(0);
            movieInfoEntity.setBannerUrl(item.get("banner_url"));
            movieInfoEntity.setCid(item.get("cid"));
            movieInfoEntity.setIntroduction(item.get("content"));
            movieInfoEntity.setVideoSource("tencent"); // 视频来源: 腾讯视频
            movieInfoMapper.insert(movieInfoEntity);
            List episodeList = Collections.singletonList(item.get("movie_episodes"));
            for (Object episodesMap:
                    (List)episodeList.get(0)) {
                Map<String, String> episodesItem = (Map<String, String>) episodesMap;
                MovieInfoEntity movieInfoEntity1 = movieInfoMapper.selectOne(new LambdaQueryWrapper<MovieInfoEntity>().eq(MovieInfoEntity::getCid, episodesItem.get("cid")));
                EpisodesEntity episodesEntity = new EpisodesEntity();
                episodesEntity.setMovieId(movieInfoEntity1.getId());
                episodesEntity.setMovieUrl(episodesItem.get("movieUrl"));
                episodesEntity.setTitle(episodesItem.get("unionTitle"));
                episodesMapper.insert(episodesEntity);
                log.debug("分集信息{}: {}", episodesItem.get("unionTitle"), episodesItem.get("movieUrl"));
            }
            log.debug("resultitems入库: {}", item.get("title"));
        }
    }
}
