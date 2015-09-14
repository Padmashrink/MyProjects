let $linespace :="&#xa;" 
for $passenger in doc("travel.xml")/travel/passenger 
return ($passenger/name, $linespace, $passenger/address, $linespace, <reservation>{count(doc("travel.xml")/travel/reservation[@passenger=$passenger/@ssn])}</reservation>,$linespace)
