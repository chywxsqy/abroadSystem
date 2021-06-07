package com.ustc.project4.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ustc.project4.entity.College;
import com.ustc.project4.mapper.CollegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollegeService {

    @Resource
    private CollegeMapper collegeMapper;

    @Autowired
    public CollegeService(CollegeMapper collegeMapper){
        this.collegeMapper = collegeMapper;
    }

    //查询所有院校信息
    public List<College> selectAllInfo() {
        return collegeMapper.selectList(null);
    }

    //根据ID查询
    public College selectById(int id) {
        return collegeMapper.selectById(id);
    }


    //根据QS排名分页查询
    public List<College> selectByQsRank(int qsLow, int qsHigh) {
        Page<College> page = new Page<>(1,10);
        Page<College> qs_rank = collegeMapper.selectPage(page, new QueryWrapper<College>().between("qs_rank", qsLow, qsHigh));
        List<College> qs_rankRecords = qs_rank.getRecords();
        return qs_rankRecords;
    }


    //根据学校名称模糊查询
    public List<College> selectByName(String collegeName) {
        Page<College> page = new Page<>(1,10);
        Page<College> college_name = collegeMapper.selectPage(page, new QueryWrapper<College>().like("college_name", collegeName));
        List<College> collegeNameRecords = college_name.getRecords();
        return collegeNameRecords;
    }
}
