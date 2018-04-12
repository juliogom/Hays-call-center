package co.com.hays.CallCenter;

import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;


public class DispatcherTest extends TestCase {

	private static final int CALL_AMOUNT = 10;

    private static final int MIN_CALL_DURATION = 5;

    private static final int MAX_CALL_DURATION = 10;
    
    @Test(expected = NullPointerException.class)
    public void testDispatcherCreationWithNullEmployees() {
        new Dispatcher(null);
    }
    
    @Test
    public void testDispatchCalls() throws InterruptedException {
    	
    	
        List<Employee> employeeList = getEmployees();
        
        Dispatcher dispatcher = new Dispatcher(employeeList);
        dispatcher.start();
        TimeUnit.SECONDS.sleep(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        TimeUnit.SECONDS.sleep(1);
        
        List<Call> calls=new ArrayList<Call>();
        
        for (int i=0;i< CALL_AMOUNT;i++) {
        	
        	calls.add(Call.buildCall(MIN_CALL_DURATION, MAX_CALL_DURATION));
        }
        
        calls.stream().forEach(call -> {
            dispatcher.dispatch(call);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });

        executorService.awaitTermination(MAX_CALL_DURATION * 2, TimeUnit.SECONDS);
        assertEquals(CALL_AMOUNT, employeeList.stream().mapToInt(employee -> employee.getAttendedCalls().size()).sum());
    }
	
    private static List<Employee> getEmployees() {
    	
        Employee em1 = new Employee(Type.OPERATOR);
        Employee em2 = new Employee(Type.OPERATOR);
        Employee em3 = new Employee(Type.OPERATOR);
        Employee em4 = new Employee(Type.OPERATOR);
        Employee em5 = new Employee(Type.OPERATOR);
        Employee em6 = new Employee(Type.OPERATOR);
        Employee em7 = new Employee(Type.SUPERVISOR);
        Employee em8 = new Employee(Type.SUPERVISOR);
        Employee em9 = new Employee(Type.SUPERVISOR);
        Employee em10 = new Employee(Type.DIRECTOR);
        
       
        return Arrays.asList(em1,em2,em3,em4,em5,em6,em7,em8,em9,em10);
    }
	
}
