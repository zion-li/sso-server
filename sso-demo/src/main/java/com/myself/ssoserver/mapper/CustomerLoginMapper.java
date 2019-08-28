package com.myself.ssoserver.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.myself.ssoserver.model.CustomerLogin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * @author Created by zion
 * @Date 2019/8/12.
 */
public interface CustomerLoginMapper extends BaseMapper<CustomerLogin> {

    /**
     * 登录失败，更新error_counts+1
     *
     * @param wrapper where条件封装
     */
    @Update("UPDATE customer_login SET  error_counts=error_counts+1 ${ew.customSqlSegment}")
    void incErrorCounts(@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 登陆成功，重置error_counts=0
     *
     * @param wrapper where条件封装
     */
    @Update("UPDATE customer_login SET  error_counts=0 ${ew.customSqlSegment}")
    void resetErrorCounts(@Param(Constants.WRAPPER) Wrapper wrapper);
}
