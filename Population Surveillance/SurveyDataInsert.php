<?php
   $localhost = mysqli_connect("omega.uta.edu","*****","*****","*****"); 
   if (mysqli_connect_errno()) {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
   if(isset($_POST['insert']))
   {
        $survey_date=$_POST['survey_date'];
        $date_of_birth=$_POST['date_of_birth'];
        $gender=$_POST['gender'];
        $ethnicity=$_POST['ethnicity'];
        $marital_status=$_POST['marital_status'];
        $alcohol_con=$_POST['alcohol_cons'];
        $tobacco_con=$_POST['tobacco_cons'];
        $weekly_fastfood_cons=$_POST['weekly_fastfood_cons'];
        $weekly_fruits_cons=$_POST['weekly_fruits_cons'];
        
        for($i =0; $i<count($_POST['occupation']);$i++)
        {
            
        }
        $arraySize = count($_POST['occupation']);
        
        if($arraySize == 1)
        {
            $occupation1 = $_POST['occupation'][0];
        }
        if($arraySize == 2)
        {
            $occupation1 = $_POST['occupation'][0];
            $occupation2 = $_POST['occupation'][1];
        }
        if($arraySize == 3)
        {
            $occupation1 = $_POST['occupation'][0];
            $occupation2 = $_POST['occupation'][1];
            $occupation3 = $_POST['occupation'][2];
        }
        
        for($i =0; $i<count($_POST['disease']);$i++)
        {
            
        }
        $arraySize1 = count($_POST['disease']);
        
        if($arraySize1 == 1)
        {
            $disease1 = $_POST['disease'][0];
        }
        if($arraySize1 == 2)
        {
            $disease1 = $_POST['disease'][0];
            $disease2 = $_POST['disease'][1];
        }
        if($arraySize1 == 3)
        {
            $disease1 = $_POST['disease'][0];
            $disease2 = $_POST['disease'][1];
            $disease3 = $_POST['disease'][2];
        }
        if($arraySize1 == 4)
        {
            $disease1 = $_POST['disease'][0];
            $disease2 = $_POST['disease'][1];
            $disease3 = $_POST['disease'][2];
            $disease4 = $_POST['disease'][3];
        }
        $disease_date=$_POST['disease_date'];
        $location_name=$_POST['location_name'];
   
        if($alcohol_con=="True")
        {
            $alcohol_cons=True;
        }
        else if($alcohol_con=="False")
        { 
            $alcohol_cons=False;   
        }

        if($tobacco_con=="True")
        {
            $tobacco_cons=True;
        }
        else if($tobacco_con=="False")
        { 
            $tobacco_cons=False;   
        }

        if($location_name=="towna")
        {
            $location_id=1;
        }
        else if($location_name=="townb")
        {
            $location_id=2;
        }
        else if($location_name=="townc")
        {
            $location_id=3;
        }

        if($disease1=="diabetes" or $disease2=="diabetes" or $disease3=="diabetes" or $disease4=="diabetes")
        {
            $disease_id1=1;
        }
        if ($disease1=="hypertension" or $disease2=="hypertension" or $disease3=="hypertension" or $disease4=="hypertension")
        {
            $disease_id2=2;
        }
        if($disease1=="copd" or $disease2=="copd" or $disease3=="copd" or $disease4=="copd")
        {
            $disease_id3=3;
        }
        if($disease1=="myocardialinfraction" or $disease2=="myocardialinfraction" or $disease3=="myocardialinfraction" or $disease4=="myocardialinfraction")
        {
            $disease_id4=4;
        }

        if($occupation1=="officer" or $occupation2=="officer" or $occupation3=="officer")
        {
            $occupation_id1=1;
        }
        if( $occupation1=="farmer" or $occupation2=="farmer" or $occupation3=="farmer")
        {
            $occupation_id2=2;
        }
        if($occupation1=="worker" or $occupation2=="worker" or $occupation3=="worker")
        {
            $occupation_id3=3;
        }
        $query_insert_survey=mysqli_query($localhost,"INSERT INTO survey_conducted(survey_date,weekly_fastfood_cons,weekly_fruits_cons,tobacco_cons,alcohol_cons,marital_status,ethnicity,date_of_birth,gender,location_id) VALUES ('$survey_date','$weekly_fastfood_cons','$weekly_fruits_cons','$tobacco_cons','$alcohol_cons','$marital_status','$ethnicity','$date_of_birth','$gender','$location_id')");
        $query_survey_id=mysqli_query($localhost,"SELECT LAST_INSERT_ID() AS newid");
        $rows = mysqli_num_rows($query_survey_id);
        if($rows == 0) { 
 		    echo "No Output"; 
	    }
 	    else  
        {
            echo "Survey Data Inserted";
            while($displayRow = $query_survey_id->fetch_array())
            {
                 $useSurveyId = $displayRow['newid'];
            }
        }
        if(isset($occupation_id1))
        {
            $query_insert_survey_occupation=mysqli_query($localhost,"INSERT INTO survey_occupation(survey_id,occupation_id) VALUES ('$useSurveyId','$occupation_id1')");
        }
        if(isset($occupation_id2))
        {
            $query_insert_survey_occupation=mysqli_query($localhost,"INSERT INTO survey_occupation(survey_id,occupation_id) VALUES ('$useSurveyId','$occupation_id2')");
        }
        if(isset($occupation_id3))
        {
            $query_insert_survey_occupation=mysqli_query($localhost,"INSERT INTO survey_occupation(survey_id,occupation_id) VALUES ('$useSurveyId','$occupation_id3')");
        }
        if(isset($disease_id1))
        {
             $query_insert_survey_disease=mysqli_query($localhost,"INSERT INTO survey_diseases (survey_id,disease_id,disease_date) VALUES ('$useSurveyId','$disease_id1','$disease_date')");
        }
        if(isset($disease_id2))
        {
             $query_insert_survey_disease=mysqli_query($localhost,"INSERT INTO survey_diseases (survey_id,disease_id,disease_date) VALUES ('$useSurveyId','$disease_id2','$disease_date')");
        } 
        if(isset($disease_id3))
        {
             $query_insert_survey_disease=mysqli_query($localhost,"INSERT INTO survey_diseases (survey_id,disease_id,disease_date) VALUES ('$useSurveyId','$disease_id3','$disease_date')");
        }
        if(isset($disease_id4))
        {
             $query_insert_survey_disease=mysqli_query($localhost,"INSERT INTO survey_diseases (survey_id,disease_id,disease_date) VALUES ('$useSurveyId','$disease_id4','$disease_date')");
        }     
   }
   mysqli_close($localhost); 
?>

<!DOCTYPE html>
<html>
<head>
<title>Survey Information</title>
</head>
<body bgcolor="#E6E6FA">
    <div align="center"><h2>SURVEY DATA CAPTURE FORM</h2></div>
    <form action ="SurveyDataInsert.php" method ="POST">
        <label>Survey Date: <input type="text" name="survey_date" required="true"/></label><br/><br/>
        <label>Date of Birth: <input type="text" name="date_of_birth" required="true"/></label><br/><br/>
        <label>Gender: <br/><select name="gender">
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>                                        
                            </select></label><br/><br/>
        <label>Ethnicity: <input type="text" name="ethnicity" required="true"/></label><br/><br/>
        <label>Marital Status: <input type="text" name="marital_status" required="true"/></label><br/><br/>
        <label>Alcohol Consumption: <br/><select name="alcohol_cons">
                                            <option value="True">Yes</option>
                                            <option value="False">No</option>                                        
                                         </select></label><br/><br/>
        <label>Tobacco Consumption: <br/><select name="tobacco_cons">
                                            <option value="True">Yes</option>
                                            <option value="False">No</option>                                        
                                         </select></label><br/><br/>
        <label>Fast food Consumption/week:<br/> <input type="text" name="weekly_fastfood_cons" required="true"/></label><br/><br/>
        <label>Fruits/week: <br/> <input type="text" name="weekly_fruits_cons" required="true"/></label><br/><br/>
        <label>Occupation name: <select name="occupation[]" multiple>
                                      <option value="officer">Office Employee</option>
                                      <option value="farmer">Farmer</option>
                                      <option value="worker">Industrial Labor Worker</option>
                               </select></label><br/><br/>
        <label>Chronic Diseases Names:<select name="disease[]" multiple>
                                            <option value="diabetes">Diabetes</option>
                                            <option value="hypertension">Hypertenion</option>
                                            <option value="copd">COPD</option>
                                            <option value="myocardialinfraction">Myocardial infraction</option>
                                      </select></label><br/><br/>
        <label>Diagonized Date: <input type="text" name="disease_date" required="true"/></label><br/><br/>
        <label>Location: <select name="location_name">
                                            <option value="towna">Town A</option>
                                            <option value="townb">Town B</option>
                                            <option value="townc">Town C</option>                                        
                                      </select></label><br/><br/>
        <input type="submit" name="insert" value="Insert"/><br/><br/>
        <button type="reset" value="Reset">Reset</button><br/><br/>
    </form>
    <form action ="HomeScreen.php" method ="POST">
        <input type="submit" name="back" value="Home Page" />
    </form>
</body>
</html>
