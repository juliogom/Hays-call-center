package co.com.hays.CallCenter;

import java.util.concurrent.ThreadLocalRandom;

public class Call {

	private int duration;

	public Call(int duration) {

		if (duration >= 0) {
			this.duration = duration;
		}
	}

	public int getDuration() {
		return duration;
	}

	public static Call buildCall(int minDuration, int maxDuration) {

		if (maxDuration >= minDuration && minDuration >= 0) {
			// Generation With ramdom thread to avoid unexpected performance issue
			return new Call(ThreadLocalRandom.current().nextInt(minDuration, maxDuration));
		}

		return null;
	}

}
