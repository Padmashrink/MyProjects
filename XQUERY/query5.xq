let $linespace :="&#xa;"
let $passenger:= doc("travel.xml")/travel/passenger[name="John Smith"] 
let $passatt:= data($passenger/@ssn) 
for $reservation in doc("travel.xml")/travel/reservation 
where data($reservation/@passenger) = $passatt 
return ($passenger/name,$linespace,<countOfReservation>{count($reservation)}</countOfReservation>)
