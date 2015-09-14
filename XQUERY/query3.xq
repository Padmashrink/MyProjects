let $linespace :="&#xa;" 
for $airport in doc("travel.xml")/travel/airport 
let $from:= doc("travel.xml")/travel/flight[@from=$airport/@code] 
let $to:= doc("travel.xml")/travel/flight[@to=$airport/@code] 
return ($airport[@code],$linespace,<fromCount>{count($from)}</fromCount>,$linespace,<toCount>{count($to)}</toCount>,$linespace)
