let $linespace :="&#xa;"
let $from:= doc("travel.xml")/travel/airport[name="DFW"]/@code 
for $flight in doc("travel.xml")/travel/flight[@from=$from] 
let $reservation:=doc("travel.xml")/travel/reservation[@flight=$flight/@id] 
return ($flight/date,$linespace,$flight/departureTime,$linespace,$flight/arrivalTime,$linespace,<departureCode>{data($flight/@from)}</departureCode>,$linespace,<arrivalCode>{data($flight/@to)}</arrivalCode>,$linespace,<count>{count($reservation)}</count>)
