# Car Sharing

We want to simulate a deposit of shared cars.
 - Initially we have `NC` cars
 
A new client comes every `T_IN` minutes
  - If there are available cars, he lends one car, for a duration of `T_TRAVEL` minutes
  - If there are no cars, he is a dissatisfied client

Compute the number of dissatisfied clients, at the end of the day, as a function of `NC`.

Assume:
  - `T_IN` = 10 minutes
  - `T_TRAVEL` = random (1 hour, 2 hours, 3 hours)

## Analysis

Events:

  - Client arrives
  - Client returns car
  
World model:

  - Number of total cars (constant)
  - Number of available cars (current)
  - Number of clients served
  - Number of dissatisfied clients 