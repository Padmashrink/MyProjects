for $flight in doc("travel.xml")/travel/flight 
where data($flight/@from) = data(doc("travel.xml")/travel/airport[name="DFW"]/@code) 
and data($flight/@to) = data(doc("travel.xml")/travel/airport[name="JFK"]/@code) 
and $flight[date="11/20/2014"] 
return $flight
