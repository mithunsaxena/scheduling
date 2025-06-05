package com.scheduling.util;

import java.time.LocalDateTime;

public class TimeSlotUtil {
	public static boolean isHourStart(LocalDateTime time) {
		return time.getMinute() == 0 && time.getSecond() == 0;
	}
}
