package co.com.hays.CallCenter;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher implements Runnable {
	
	 private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	 
	 private ExecutorService executorService;
	 
	 public static final Integer MAX_THREADS = 10;
	 
	 private Boolean activated;
	 
	 private ConcurrentLinkedDeque<Employee> employees;

	    private ConcurrentLinkedDeque<Call> queueCalls;

	    private Management management;

	    public Dispatcher(List<Employee> employees) {
	        this(new Management(),employees);
	    }

	    public Dispatcher(Management management,List<Employee> employees) {
	    	this.employees=null;
	    	if(employees != null && management != null) {
	    		this.employees = new ConcurrentLinkedDeque(employees);
		        this.management = management;
		        this.queueCalls = new ConcurrentLinkedDeque<>();
		        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
	    	}
	    }

	    public synchronized void dispatch(Call call) {
	        this.queueCalls.add(call);
	    }

	    public synchronized void start() {
	        this.activated = true;
	        for (Employee employee : this.employees) {
	            this.executorService.execute(employee);
	        }
	    }

	    public synchronized void stop() {
	        this.activated = false;
	        this.executorService.shutdown();
	    }

	    public synchronized Boolean getActive() {
	        return activated;
	    }

	    @Override
	    public void run() {
	        while (getActive()) {
	            if (this.queueCalls.isEmpty()) {
	                continue;
	            } else {
	                Employee employee = this.management.AssigEmployee(this.employees);
	                if (employee == null) {
	                    continue;
	                }
	                Call call = this.queueCalls.poll();
	                try {
	                	
	                    employee.attend(call);
	                    
	                } catch (Exception e) {
	                    logger.error(e.getMessage());
	                    this.queueCalls.addFirst(call);
	                }
	            }
	        }
	    }

	 
}
