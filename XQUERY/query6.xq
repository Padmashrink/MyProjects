let $linespace:="&#xa;"
let $passenger:= doc("travel.xml")/travel/passenger[name="John Smith"]/@ssn
for $reservation in doc("travel.xml")/travel/reservation[@passenger = $passenger]
let $flightid:=$reservation/@flight
let $flight:=doc("travel.xml")/travel/flight[@id=$flightid]/@to
let$flightname:= doc("travel.xml")/travel/airport[@code=$flight]/name
return ($linespace,$flightname,$linespace) 