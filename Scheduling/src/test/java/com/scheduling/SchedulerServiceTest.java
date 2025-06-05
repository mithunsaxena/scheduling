package com.scheduling;

import com.scheduling.model.Meeting;
import com.scheduling.model.Person;
import com.scheduling.service.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SchedulerServiceTest {

	private SchedulerService scheduler;

	private Person mithun;
	private Person kishan;
	private Person aavir;

	@BeforeEach
	public void setup() {

		scheduler = new SchedulerService();
		mithun = scheduler.createPerson("Mithun Saxena", "mithun.saxena@gmail.com");
		kishan = scheduler.createPerson("Mithun Kishan", "mithun.kishan@gmail.com");
		aavir = scheduler.createPerson("Aavir", "aavir@gmail.com");
	}

	@Test
	public void testCreateAndRetrieveMeeting() {

		LocalDateTime meetingTime = LocalDateTime.of(2025, 6, 6, 10, 0);
		scheduler.scheduleMeeting(meetingTime, Arrays.asList(mithun, kishan));

		List<Meeting> mithunMeetings = scheduler.getScheduleForPerson(mithun);
		assertEquals(1, mithunMeetings.size());
		assertEquals(meetingTime, mithunMeetings.get(0).getTimeSlot());
	}

	@Test
	public void testSuggestAvailableSlots() {

		LocalDateTime meetingTime = LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
		scheduler.scheduleMeeting(meetingTime, Collections.singletonList(mithun));

		List<LocalDateTime> availableSlots = scheduler.suggestAvailableSlots(Arrays.asList(mithun, aavir), LocalDateTime.now(), LocalDateTime.now().plusHours(10));
		assertFalse(availableSlots.contains(meetingTime));
	}

	@Test
	public void testDuplicatePersonThrowsException() {

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			scheduler.createPerson("Duplicate Mithun Saxena", "mithun.saxena@gmail.com");
		});
		assertEquals("Email already exists.", exception.getMessage());
	}

	@Test
	public void testInvalidMeetingTimeThrowsException() {

		LocalDateTime invalidTime = LocalDateTime.of(2025, 6, 6, 10, 15);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			scheduler.scheduleMeeting(invalidTime, Collections.singletonList(mithun));
		});
		assertEquals("Meetings must start at the hour.", exception.getMessage());
	}
}
