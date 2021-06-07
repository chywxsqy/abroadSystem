package com.ustc.project4.mapper;

import com.ustc.project4.entity.College;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chy
 * @since 2021-06-07
 */

@Mapper
public interface CollegeMapper extends BaseMapper<College> {

}
