package com.scheduling;

import com.scheduling.service.SchedulerService;
import com.scheduling.model.Person;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {

	public static void main(String[] args) {

		SchedulerService scheduler = new SchedulerService();

		Person mithun = scheduler.createPerson("Mithun Saxena", "mithun.saxena@gmail.com");
		Person kishan = scheduler.createPerson("Mithun Kishan", "mithun.kishan@gmail.com");
		Person aavir = scheduler.createPerson("Aavir", "aavir@gmail.com");

		//Schedule meeting for mithun, kishan and aavir
		LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
		scheduler.scheduleMeeting(now, Arrays.asList(mithun, kishan, aavir));

		// Show Mithun's schedule
		System.out.println("Mithun's Schedule:");
		scheduler.getScheduleForPerson(mithun).forEach(System.out::println);

		// Suggest Available Slots
		System.out.println("\nAvailable slots for Mithun, Kishan, and Aavir (next 10 hours):");

		List<LocalDateTime> suggestions = scheduler.suggestAvailableSlots(Arrays.asList(mithun, kishan, aavir), now, now.plusHours(20));
		suggestions.forEach(System.out::println);
	}
}
