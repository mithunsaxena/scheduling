package com.scheduling.service;

import com.scheduling.model.Meeting;
import com.scheduling.model.Person;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SchedulerService {

	private Map<String, Person> people   = new HashMap<>();
	private List<Meeting>       meetings = new ArrayList<>();

	public Person createPerson(String name, String email) {

		if (people.containsKey(email.toLowerCase())) {
			throw new IllegalArgumentException("Email already exists.");
		}
		Person person = new Person(name, email);
		people.put(email.toLowerCase(), person);
		return person;
	}

	public Meeting scheduleMeeting(LocalDateTime timeSlot, List<Person> attendees) {

		if (timeSlot.getMinute() != 0 || timeSlot.getSecond() != 0) {
			throw new IllegalArgumentException("Meetings must start at the hour.");
		}

		for (Meeting m : meetings) {
			for (Person attendee : attendees) {
				if (m.getTimeSlot().equals(timeSlot) && m.getAttendees().contains(attendee)) {
					throw new IllegalArgumentException("Person " + attendee + " is not available at " + timeSlot);
				}
			}
		}

		Meeting meeting = new Meeting(timeSlot, attendees);
		meetings.add(meeting);
		return meeting;
	}

	public List<Meeting> getScheduleForPerson(Person person) {

		return meetings.stream()
				.filter(m -> m.getAttendees().contains(person))
				.sorted(Comparator.comparing(Meeting::getTimeSlot))
				.collect(Collectors.toList());
	}

	public List<LocalDateTime> suggestAvailableSlots(List<Person> group, LocalDateTime from, LocalDateTime to) {

		List<LocalDateTime> suggested = new ArrayList<>();

		for (LocalDateTime slot = from.withMinute(0).withSecond(0);
			 slot.isBefore(to);
			 slot = slot.plusHours(1)) {

			boolean allAvailable = true;
			for (Meeting meeting : meetings) {
				if (meeting.getTimeSlot().equals(slot)) {
					for (Person p : group) {
						if (meeting.getAttendees().contains(p)) {
							allAvailable = false;
							break;
						}
					}
				}
				if (!allAvailable) {
					break;
				}
			}

			if (allAvailable) {
				suggested.add(slot);
			}
		}

		return suggested;
	}
}
