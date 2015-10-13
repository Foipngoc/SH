package com.example.timer;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("timer")
public class Timer {

	public Timer() {
		System.out.println("Timer inited!");
	}

	/**
	 * 定时任务
	 */
	@Scheduled(fixedRate = 5000)
	public void timer() {
		System.out.println("Time:" + new Date().toString());
	}
}
