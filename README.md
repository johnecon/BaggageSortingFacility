# Baggage Sorting Facility #

The system is a baggage sorting facility which sorts bags regarding their color (black and yellow). There are two feed belts which accept the bags, pass them into color sensors and deliver them into the distribution belt. The distribution belt is responsible for sorting them (yellow bags are delivered to the left side and black ones to the right). The sorting facility is handled by one or two users, which can either stand on the left check-in or on the right one. The two feed belts and the distribution belt are controlled by two feed belt controllers. The bag’s position in the system is represented by the bag model.

The facility is [modeled]() using [UPPAAL](http://www.uppaal.org/) model checker and all required properties are verified.
It is then [implemented]() in java and [integrated]() in a LEGO system using [leJOS](http://www.lejos.org/) framework and a JVM RCX brick. [Docs]() folder contains detailed documentation on the tools used.