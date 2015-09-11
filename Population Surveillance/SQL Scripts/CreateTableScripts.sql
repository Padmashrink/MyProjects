CREATE SCHEMA health_survey;

CREATE TABLE location(location_id INT(5) NOT NULL AUTO_INCREMENT, location_name VARCHAR(20), location_city VARCHAR(30), location_state VARCHAR(30), location_zip_code VARCHAR(15), no_of_people INT(10),
					  CONSTRAINT location_pk PRIMARY KEY(location_id));

CREATE TABLE accidents(accident_id INT(5) NOT NULL AUTO_INCREMENT, accident_type varchar(20), accident_date DATE, location_id INT(5),
						CONSTRAINT accidents_pk PRIMARY KEY(accident_id),
                        CONSTRAINT location_fk FOREIGN KEY(location_id) REFERENCES location(location_id) ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE health_infra(health_infra_id INT(5) NOT NULL AUTO_INCREMENT, health_infra_capacity varchar(20), health_infra_type varchar(50), location_id INT(5), health_infra_name VARCHAR(100),
						CONSTRAINT primary_key_infra PRIMARY KEY(health_infra_id),
                        CONSTRAINT location_infra_fk FOREIGN KEY(location_id) REFERENCES location(location_id) ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE CO_level(co_level_id INT(5) NOT NULL AUTO_INCREMENT, co_level_value varchar(20), co_level_date DATE, location_id INT(5),
						CONSTRAINT co_level_pk PRIMARY KEY(co_level_id),
                        CONSTRAINT location_CO_fk FOREIGN KEY(location_id) REFERENCES location(location_id) ON DELETE SET NULL ON UPDATE CASCADE);
                        
CREATE TABLE pollen_conc(pollen_conc_id INT(5) NOT NULL AUTO_INCREMENT, pollen_conc_value varchar(20), pollen_conc_date DATE, location_id INT(5),
						CONSTRAINT pollen_conc_pk PRIMARY KEY(pollen_conc_id),
                        CONSTRAINT location_pollen_fk FOREIGN KEY(location_id) REFERENCES location(location_id) ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE survey_conducted(survey_id INT(5) NOT NULL AUTO_INCREMENT, survey_date INT(4), weekly_fastfood_cons INT(5), weekly_fruits_cons INT(5), tobacco_cons boolean, alcohol_cons boolean, marital_status VARCHAR(20), ethnicity VARCHAR(20), date_of_birth DATE, gender VARCHAR(7), location_id INT(5),
							  CONSTRAINT survey_pk PRIMARY KEY(survey_id),
							  CONSTRAINT location_survey_fk FOREIGN KEY(location_id) REFERENCES location(location_id) ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE occupation(occupation_id INT(5) NOT NULL AUTO_INCREMENT, occupation_name varchar(40),
						CONSTRAINT occupation_pk PRIMARY KEY(occupation_id));
                        
CREATE TABLE survey_occupation(survey_id INT(5) NOT NULL, occupation_id INT(5) NOT NULL,
							 CONSTRAINT composite_pk PRIMARY KEY(survey_id, occupation_id),
							 CONSTRAINT survey_occ_fk FOREIGN KEY(survey_id) REFERENCES survey_conducted(survey_id)  ON DELETE CASCADE ON UPDATE CASCADE,
							 CONSTRAINT survey_occupation_fk FOREIGN KEY(occupation_id) REFERENCES occupation(occupation_id) ON DELETE CASCADE ON UPDATE CASCADE);
                             
CREATE TABLE chronic_diseases(disease_id INT(5) NOT NULL AUTO_INCREMENT, disease_name varchar(30), 
							  CONSTRAINT chronic_diseases_pk PRIMARY KEY(disease_id));
                              
CREATE TABLE survey_diseases(survey_id INT(5) NOT NULL, disease_id INT(5) NOT NULL, disease_date DATE, 
							 CONSTRAINT composite_survey_pk PRIMARY KEY(survey_id, disease_id, disease_date),
							 CONSTRAINT survey_chronic_fk FOREIGN KEY(survey_id) REFERENCES survey_conducted(survey_id)  ON DELETE CASCADE ON UPDATE CASCADE,
							 CONSTRAINT disease_fk FOREIGN KEY(disease_id) REFERENCES chronic_diseases(disease_id)  ON DELETE CASCADE ON UPDATE CASCADE);