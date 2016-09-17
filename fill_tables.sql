insert into plane values
(default,"boeing","747",10,20,70,100);

insert into passenger values
(default,"Man","Jig","1","1"),
(default,"Bang","bang","2","2"),
(default,"Gifa","Brth","3","3"),
(default,"Hoie","Pac","4","4");

insert into flight_schedule values
(default,1,'2016-06-28 15:30:00','Oslo','2016-06-28 18:30:00','Bucharest'),
(default,1,'2016-06-28 08:30:00','Oslo','2016-06-28 10:20:00','Copenhagen'),
(default,1,'2016-06-28 23:30:00','Oslo','2016-06-28 01:30:00','Amsterdam'),
(default,1,'2016-06-28 09:00:00','Oslo','2016-06-28 18:00:00','Ethiopia'),
(default,1,'2016-06-28 07:30:00','Oslo','2016-06-28 08:30:00','Copenhagen'),
(default,1,'2016-06-28 07:30:00','Oslo','2016-06-28 08:30:00','Bucharest');

insert into seat values
(default,1,1,'confirmed',1000,'first'),
(default,1,null,'available',1000,'first'),
(default,6,2,'available',1000,'first'),
(default,6,null,'available',1000,'first'),
(default,1,3,'confirmed',800,'business'),
(default,1,4,'available',800,'business'),
(default,1,2,'confirmed',500,'economy'),
(default,1,null,'available',500,'economy'),
(default,6,null,'available',500,'economy');

insert into employee values
(default,"a","a","a","a");
