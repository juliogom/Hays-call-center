package co.com.hays.CallCenter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Management implements StackAttend {

	private static final Logger logger = LoggerFactory.getLogger(Management.class);

	public Employee AssigEmployee(Collection<Employee> employees) {
		logger.info("Search for assginable employee ");

		Optional<Employee> employee = null;

		if (employees != null) {
			List<Employee> availableEmployees = employees.stream().filter(e -> e.getState() == State.AVAILABLE)
					.collect(Collectors.toList());
			employee = employees.stream().filter(e -> e.getType() == Type.OPERATOR).findAny();

			if (!employee.isPresent()) {
				logger.info("No available operators found");
				employee = employees.stream().filter(e -> e.getType() == Type.SUPERVISOR).findAny();
				if (!employee.isPresent()) {
					logger.info("No available supervisors found");
					employee = employees.stream().filter(e -> e.getType() == Type.DIRECTOR).findAny();
					if (!employee.isPresent()) {
						logger.info("No available directors found");
						return null;
					}
				}
			}
		}

		return employee.get();
	}

}
