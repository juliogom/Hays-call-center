# Hays-call-center

Maven project created using java 1.8.
Multithread base solution for a call center where all calls, at least 
10 in the first approach,must be in differents threads and mus be attended 
by differents type of employees (Operator, supervisor and director).
The calls must be attended in different orders, being first attended by 
the operator, if the operator is busy then supervisor will take the call 
and if this person is not available then director must be attend the call.


# Tests
All test are maden with JUnit, simulating a list of 10 calls for 10 operators.
