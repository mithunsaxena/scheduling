package com.scheduling.model;

import com.scheduling.model.Person;

import java.time.LocalDateTime;
import java.util.List;

public class Meeting {

	private LocalDateTime timeSlot; // Must be on the hour
	private List<Person>  attendees;

	public Meeting(LocalDateTime timeSlot, List<Person> attendees) {

		if (timeSlot.getMinute() != 0 || timeSlot.getSecond() != 0) {
			throw new IllegalArgumentException("Meeting must start at the hour mark.");
		}
		this.timeSlot = timeSlot;
		this.attendees = attendees;
	}

	public LocalDateTime getTimeSlot() {

		return timeSlot;
	}

	public List<Person> getAttendees() {

		return attendees;
	}

	@Override
	public String toString() {

		return "Meeting at " + timeSlot + " with " + attendees;
	}
}
