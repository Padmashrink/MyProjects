SELECT count(survey_id) AS SURVEYED_FEMALES, location_name FROM survey_conducted AS SC JOIN location AS L ON SC.location_id = L.location_id WHERE gender='FEMALE' and SC.survey_date=2012 group by SC.location_id;



SELECT accident_occurence, location_name, accident_type
            FROM (SELECT COUNT( accident_id ) AS accident_occurence, location_name, accident_type
            FROM accidents
            INNER JOIN location ON location.location_id = accidents.location_id and location.location_name = 'Town A'
            GROUP BY location_name, accident_type
            ORDER BY accident_occurence DESC
            ) AS A
            GROUP BY A.location_name;



SELECT l.location_name, l.location_city, l.location_state, l.location_zip_code, sc.survey_date AS survey_year, cd.disease_name, (
            count( cd.disease_id ) / (
            SELECT count( cd.disease_id )
            FROM location AS l, survey_conducted AS sc, chronic_diseases AS cd, survey_diseases AS sd
            WHERE l.location_name = 'Town B'
            AND sc.survey_date = 2014
            AND sc.location_id = l.location_id
            AND sd.survey_id = sc.survey_id
            AND sd.disease_id = cd.disease_id ) *100
            ) AS PREVALENCE
            FROM location AS l, survey_conducted AS sc, chronic_diseases AS cd, survey_diseases AS sd
            WHERE cd.disease_name = 'diabetes'
            AND l.location_name = 'Town B'
            AND sc.survey_date = 2014
            AND sc.location_id = l.location_id
            AND sd.survey_id = sc.survey_id
            AND sd.disease_id = cd.disease_id;



CREATE VIEW SURVEY_MIN_DIABETES
AS
(select  l.location_name,l.location_state,l.location_zip_code,
(count(cd.disease_id)/(select count(cd.disease_id) 
from location as l, survey_conducted as sc, chronic_diseases as  cd, survey_diseases as sd 
where sc.survey_date=2012 and sc.location_id=l.location_id and sd.survey_id=sc.survey_id and 
sd.disease_id=cd.disease_id)*100) as PREVALENCE 
from location as l, survey_conducted as sc, chronic_diseases as  cd, survey_diseases as sd 
where cd.disease_name='diabetes' and sc.survey_date=2014 and 
sc.location_id=l.location_id and sd.survey_id=sc.survey_id and sd.disease_id=cd.disease_id 
group by l.location_name);


SELECT MIN(PREVALENCE) as prevalence, location_name AS name, location_state AS state, location_zip_code AS zip
FROM SURVEY_MIN_DIABETES;




select l.location_name,l.location_city, l.location_state, l.location_zip_code from location l where NOT EXISTS
(select a.location_id  from accidents a 
WHERE a.location_id = l.location_id);




CREATE VIEW population
AS
SELECT COUNT(sc.survey_id) AS population
FROM survey_conducted SC JOIN location L ON SC.location_id = L.location_id
WHERE L.location_name = 'Town C';

CREATE VIEW HospitalCount
AS
SELECT COUNT(HI.health_infra_id) AS hospitalCount, HI.health_infra_type
FROM health_infra HI JOIN location L ON HI.location_id = L.location_id
WHERE L.location_name = 'Town C' AND HI.health_infra_type = 'Hospital';

CREATE VIEW RehabCount
AS
SELECT COUNT(HI.health_infra_id) AS rehabCount, HI.health_infra_type
FROM health_infra HI JOIN location L ON HI.location_id = L.location_id
WHERE L.location_name = 'Town C' AND HI.health_infra_type = 'Rehabilitation Center';

SELECT population / hospitalcount AS hospital_ratio, population / rehabCount AS rehabcenter_ratio
FROM HospitalCount, RehabCount, population;



CREATE VIEW Smoking_2012
as
SELECT COUNT(tobacco_cons) AS Smoking_2012, location_name
FROM survey_conducted, location where location.location_id = survey_conducted.location_id and tobacco_cons='1' and survey_date=2012
group by location_name;

CREATE VIEW Smoking_2014
as
SELECT COUNT(tobacco_cons) AS Smoking_2014, location_name
FROM survey_conducted, location where location.location_id = survey_conducted.location_id and tobacco_cons='1' and survey_date=2014
group by location_name;


SELECT Smoking_2012-Smoking_2014 as Change_in_habit, Smoking_2014.location_name as Location
from Smoking_2012 join Smoking_2014 ON Smoking_2012.location_name = Smoking_2014.location_name
group by Smoking_2014.location_name;
