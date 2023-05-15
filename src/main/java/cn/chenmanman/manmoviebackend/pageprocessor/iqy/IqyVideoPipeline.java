package cn.chenmanman.manmoviebackend.pageprocessor.iqy;

import cn.chenmanman.manmoviebackend.domain.entity.EpisodesEntity;
import cn.chenmanman.manmoviebackend.domain.entity.MovieInfoEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 陈慢慢
 * @description 爱奇艺数据存储管道 - mysql
 * @createDateTime 2023年5月15日 11点32分
 * */

@Component
@Slf4j
public class IqyVideoPipeline implements Pipeline {

    @Resource
    private MovieInfoMapper movieInfoMapper;

    @Resource
    private EpisodesMapper episodesMapper;

    @Transactional
    @Override
    public void process(ResultItems resultItems, Task task) {
        log.debug("resultItems: {}", resultItems.toString());
        // 存储基本信息
//        saveMovieInfo(resultItems);

        // 存储剧集信息
        saveEpisodes(resultItems);
    }

    private void saveEpisodes(ResultItems resultItems) {
        List<Map> episodes = ((HashMap<String, List>) ((HashMap<String, Object>) resultItems.get("movieInfo")).get("movieDetail")).get("episodes");

        for (Map<String, List> episode:episodes) {
            for (List<Map> episode1: episode.values()) {
                for (Map<String, String> currentEpisode:episode1) {
                    String title = currentEpisode.get("title");
                    String pageUrl = currentEpisode.get("pageUrl");
                    // todo 需根据爱奇艺的一个唯一标识一个影视剧的标识, 在Movie_info中查到其Id设置到movieid。
                    EpisodesEntity episodesEntity = new EpisodesEntity();
                    episodesEntity.setTitle(title);
                    episodesEntity.setMovieUrl(pageUrl);
                    episodesMapper.insert(episodesEntity);
                    log.debug("遍历到剧集: {}", currentEpisode);
                }
            }
        }
    }

    private void saveMovieInfo(ResultItems resultItems) {
        Map<String, String> baseMovieinfo = (HashMap<String,String>) ((HashMap<String,Object>) resultItems.get("movieInfo")).get("baseMovieInfo");
        String title = baseMovieinfo.get("title");
        String desc = baseMovieinfo.get("desc");
        // 检查该影视剧是否已存在
        MovieInfoEntity movieInfoEntity = movieInfoMapper.selectOne(new LambdaQueryWrapper<MovieInfoEntity>().eq(MovieInfoEntity::getName, title));
        if (Objects.nonNull(movieInfoEntity)) return;

        MovieInfoEntity baseMovieInfoEntity = new MovieInfoEntity();
        baseMovieInfoEntity.setName(title);
        baseMovieInfoEntity.setIntroduction(desc);
        baseMovieInfoEntity.setVideoSource("iqy"); // 视频采集于iqy
        movieInfoMapper.insert(baseMovieInfoEntity);
    }
}
