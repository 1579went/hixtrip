package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Black
* @description 针对表【order】的数据库操作Mapper
* @createDate 2024-04-13
* @Entity com.hixtrip.sample.infra.db.dataobject.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

}




