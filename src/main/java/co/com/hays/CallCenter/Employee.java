package co.com.hays.CallCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Employee implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Employee.class);
	
	private ConcurrentLinkedDeque<Call> callsInQeue;
	private ConcurrentLinkedDeque<Call> callsAttended;

	private State state;
	private Type type;

	public Employee(Type type) {
		logger.info("Creating a new Employee ");

		this.state = State.AVAILABLE;
		callsInQeue = new ConcurrentLinkedDeque<Call>();
		callsAttended = new ConcurrentLinkedDeque<Call>();
		
		this.type=type;
		
	}

	public synchronized State getState() {
		return state;
	}

	private synchronized void setState(State employeeState) {
		logger.info("Setting state");
		this.state = employeeState;
	}

	
	public Type getType() {
        return type;
    }
	public synchronized void attend(Call call) {
        
        this.callsInQeue.add(call);
    }
	
	 public synchronized List<Call> getAttendedCalls() {
	        return new ArrayList<>(callsInQeue);
	    }
	
	public void run() {
		logger.info("Employee " + Thread.currentThread().getName() + " starts to work");

		while (true) {
			if (!this.callsInQeue.isEmpty()) {
				Call call = this.callsInQeue.poll();
				this.setState(State.BUSY);

				try {

					TimeUnit.SECONDS.sleep(call.getDuration());

				} catch (InterruptedException e) {
					logger.error("Somthing wrong happend in call " + Thread.currentThread().getName());
				} finally {

					this.setState(state.AVAILABLE);

				}

				this.callsAttended.add(call);
			}
		}

	}
}
