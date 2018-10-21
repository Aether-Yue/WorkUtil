package com.sinosoft.platform.quartz.domain.mapper;

import java.util.HashMap;

import com.sinosoft.platform.quartz.domain.model.Scheduleinfo;

public interface ScheduleInfoMapper {

	Scheduleinfo findByID(int id);
	HashMap<String,String> findMapByID(int id);
}
