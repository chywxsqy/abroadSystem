package com.ustc.project4.controller.college;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ustc.project4.entity.College;
import com.ustc.project4.service.CollegeService;
import com.ustc.project4.util.Project4Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chy
 * @since 2021-06-07
 */
@RestController
@RequestMapping("/college")
public class CollegeController implements Project4Constant {

    @Autowired
    private CollegeService collegeService;

    //根据国家，qs排名进行分页查询
    @GetMapping(name = "/index",produces = "application/json;charset=UTF-8")
    public String index(@RequestParam(value = "country",required = false,defaultValue = "all")String country,
                        @RequestParam(value = "qsLow",required = false,defaultValue = "1")int qsLow,
                        @RequestParam(value = "qsHigh",required = false,defaultValue = "100")int qsHigh,
                        @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                        Map<String,Object> result){
        List<College> datalists;
        Page<College> page;
        if("all".equals(country)){
            datalists = collegeService.selectAllInfo();
        }else{
            datalists = collegeService.selectByQsRank(qsLow,qsHigh,pageNum,pageSize);
        }
        result.put("url","/college/index");
        result.put("datalists",datalists);
        return "college/index";
    }

    //模糊搜素
    @GetMapping("/search")
    public String search(@RequestParam(value = "like",required = false,defaultValue = "")String like,
                         @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                         @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                         Map<String,Object> result){
        List<College> collegeList = collegeService.selectByName(like, pageNum, pageSize);
        result.put("datalist",collegeList);

        return "college/search";
    }


}
