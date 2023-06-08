package cn.chenmanman.manmoviebackend.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo
 * @className PageResult
 * @description 分页返回
 * @date 2023/6/8 23:59
 */
@Data
public class PageResult<T> {
    /**
     * 当前页码
     * */
    private Long current;
    /**
     * 每页数量
     * */
    private Long size;
    /**
     * 数据总量
     * */
    private Long total;
    /**
     * 总页数
     * */
    private Long pages;
    /**
     * 当前页数据
     * */
    private List<T> list;
}
