//This file was generated from (Academic) UPPAAL 4.0.13 (rev. 4577), September 2010

/*
3.12	Alternating seq of bags for A and B arriving at Check-In 1. (4.11) SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
A[] not (Bag(3).Delivered and Bag(3).time>134 + 12) 

/*
3.12	Alternating seq of bags for A and B arriving at Check-In 1. (4.11) SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
A[] not (Bag(2).Delivered and Bag(2).time>76 + 70) 

/*
3.12	Alternating seq of bags for A and B arriving at Check-In 1. (4.11) SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
E<>  (Bag(2).Delivered and Bag(2).time==76+70)

/*
3.12	Alternating seq of bags for A and B arriving at Check-In 1. (4.11) SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
E<>  (Bag(3).Delivered and Bag(3).time==134 + 12)

/*
A sequence of bags for Destination B arriving at Check-In 1 , throughput (bags\/min) =  3 SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
A[] forall(i:t_id) not((Bag(i).Delivered and Bag(i).time > 134+66))

/*
A sequence of bags for Destination B arriving at Check-In 1 , throughput (bags\/min) = 3 SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
E<>  (Bag(3).Delivered and Bag(3).time==134+66)

/*
A sequence of bags for Destination A arriving at Check-In 1 , throughput (bags\/min) = 6.52 SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
A[] forall(i:t_id) not((Bag(i).Delivered and Bag(i).time > 76+16))

/*
A sequence of bags for Destination A arriving at Check-In 1 , throughput (bags\/min) = 6.52 SPECIAL SCENARIO, CHANGES REQUIRED IN SYS DECLARATION
*/
E<> (Bag(1).Delivered and Bag(1).time==76+16)

/*
The slowest possible handling of a bag
*/
A[] forall(i:t_id) not((Bag(i).Delivered and Bag(i).time > 134 + 106 + 66))

/*
The slowest possible handling of a bag FULL_SLOW+LNG+TIME_TILL_FIRST_BAG_GOES_OUT
*/
E<>  ((Bag(0).Delivered and Bag(0).time==134 + 106 + 66))

/*
The fastest possible handling of a bag (76) (FULL_FAST)
*/
A[] forall(i:t_id) not((Bag(i).Delivered and Bag(i).time < 76))

/*
The fastest possible handling of a bag (76) (FULL_FAST)
*/
E<> ((Bag(0).Delivered and Bag(0).time==76))\
\


/*
The maximum number of bags that can be handled simultaneously (3)
*/
A[] feedBags[L]+feedBags[R]+distBags<=3

/*
The maximum number of bags that can be handled simultaneously (3)
*/
E<> feedBags[L]+feedBags[R]+distBags==3

/*
3.5 The distribution belt is never stopped or reversed when it carries a bag.
*/
A[] forall(i:t_id) \
not(\
     (Bag(i).eL and ((DistBelt.StoppedInstantly and Bag(i).c<12) or DistBelt.Stopped)) or\
     (Bag(i).dL and (DistBelt.StoppedInstantly or DistBelt.Stopped)) or\
     (Bag(i).f and (DistBelt.StoppedInstantly or DistBelt.Stopped)) or\
     (Bag(i).dR and (DistBelt.StoppedInstantly or DistBelt.Stopped)) or\
     (Bag(i).eR and ((DistBelt.StoppedInstantly and Bag(i).c<12) or DistBelt.Stopped))\
)

/*
3.4 While a bag is turning (in section c), neither the Feed Belt nor the Distribution Belt is stopped or reversed.
*/
A[] forall(i:t_id) \
not(\
(\
Bag(i).cLL and (DistBelt.StoppedInstantly or feedDir[L] == STOP) and Bag(i).c<28\
)\
or\
(\
Bag(i).cLR and (DistBelt.StoppedInstantly or feedDir[L] == STOP) and Bag(i).c<28\
)\
	or\
(\
Bag(i).cRR and (DistBelt.StoppedInstantly or feedDir[R] == STOP) and Bag(i).c<28\
)\
or\
(\
Bag(i).cRL and (DistBelt.StoppedInstantly or feedDir[R] == STOP) and Bag(i).c<28\
)\
)\


/*
3.3 bumping, not satisfied for 2 users
*/
A[] forall(i:t_id) forall(y:t_id)\
	not(Bag(i).eR and Bag(y).dR and Bag(y).c>=9) and\
	not(Bag(i).eR and Bag(y).cRR and Bag(y).c>=11) and\
	not(Bag(i).eL and Bag(y).cLL and Bag(y).c>=11) and\
	not(Bag(i).eL and Bag(y).dL and Bag(y).c>=9)

/*
3.2 collisions, not satisfied for 2 users
*/
A[] (collisionBags[R]<=1 and collisionBags[L]<=1)

/*
3.1 Bags are delivered at the right destination
*/
A[] forall(i:t_id) (not ((Bag(i).color == B and Bag(i).YDelivered) or (Bag(i).color == Y and Bag(i).BDelivered)))

/*
3.6 Bags are eventually delivered
*/
(Bag(0).AtBeltL or Bag(0).AtBeltR)-->Bag(0).Delivered

/*
3.6 Bags are eventually delivered
*/
(Bag(1).AtBeltL or Bag(1).AtBeltR) --> Bag(1).Delivered

/*
3.6 Bags are eventually delivered
*/
(Bag(2).AtBeltL or Bag(2).AtBeltR) -->Bag(2).Delivered

/*
3.6 Bags are eventually delivered
*/
(Bag(3).AtBeltL or Bag(3).AtBeltR) -->Bag(3).Delivered

/*
Stopping Problem
*/
A[] forall(i:t_id)\
(\
	not (\
		((Bag(i).AtBeltR or Bag(i).a1R or Bag(i).sensorR or Bag(i).a2R) and feedDir[R] == STOP)\
		or\
		(Bag(i).b2R and feedDir[R]==STOP and Bag(i).c!=0)\
		or\
		(Bag(i).b1R and feedDir[R]==STOP and Bag(i).c!=12)\
		or\
		(Bag(i).cRL and feedDir[R]==STOP and Bag(i).c<=25)\
		or\
		(Bag(i).cRR and feedDir[R]==STOP and Bag(i).c<=25)\
		or\
		((Bag(i).AtBeltL or Bag(i).a1L or Bag(i).sensorL or Bag(i).a2L) and feedDir[L] == STOP)\
		or\
		(Bag(i).b2L and feedDir[L]==STOP and Bag(i).c!=0)\
		or\
		(Bag(i).b1L and feedDir[L]==STOP and Bag(i).c!=12)\
		or\
		(Bag(i).cLL and feedDir[L]==STOP and Bag(i).c<=25)\
		or\
		(Bag(i).cLR and feedDir[L]==STOP and Bag(i).c<=25)\
	)\
)

/*
concurrency
*/
E<> Bag(0).a1R and Bag(1).a1L
