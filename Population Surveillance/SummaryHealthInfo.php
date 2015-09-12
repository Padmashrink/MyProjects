<?php
    if(session_id == '' or (session_status != 'PHP_SESSION_ACTIVE')) 
    {
         session_start();
    }
    $hostname_localhost ="omega.uta.edu";
    $database_localhost ="*****";
    $username_localhost ="*****";
    $password_localhost ="*****";
    $localhost = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Summary Screen</title>
    </head>
    <body>
        <div align="center">
        <?php
                        if(isset($_SESSION['selectedTown']))
                        {
                            $location = $_SESSION['selectedTown'];
                            if($location == "townA")
                                $useDB = "Town A";
                            if($location == "townB")
                                $useDB = "Town B";
                            if($location == "townC")
                                $useDB = "Town C";
                        echo "Survey details for ".$location."<br/>";
                       if(isset($_GET['summary'])){
                       if($_GET['summary'] == "fruits"){
                            $query_search = "select l.location_name as loc, sc.survey_date as survey_year, 
                            AVG(weekly_fruits_cons) AS averageValue from survey_conducted sc, location l
                            where sc.location_id = l.location_id
                            and l.location_name = '".$useDB."'
                            and sc.alcohol_cons = true
                            group by sc.survey_date";
                            $query_exec = mysqli_query($localhost, $query_search);
                            $rows = mysqli_num_rows($query_exec);
                             if($rows == 0) { 
                                echo "No Output"; 
                             }
                            else  {
                                echo "<table border='1'>
                                <tr>
                                <th>City</th>
                                <th>Year</th>
                                <th>Fruits Consumption</th>
                                <tr>";
                                while($rows = mysqli_fetch_array($query_exec)){
                                  echo "<tr>";
                                  echo "<td>" . $rows['loc'] . "</td>";
                                  echo "<td>" . $rows['survey_year'] . "</td>";
                                  echo "<td>" . $rows['averageValue'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                }
                             }
                            if($_GET['summary'] == "fastfood"){
                                $query_search = "select l.location_name as loc, sc.survey_date as survey_year, 
                                AVG(weekly_fastfood_cons) AS averageValue from survey_conducted sc, location l
                                where sc.location_id = l.location_id
                                and l.location_name = '".$useDB."'
                                and sc.alcohol_cons = true
                                group by sc.survey_date";
                                $query_exec = mysqli_query($localhost, $query_search);
                                $rows = mysqli_num_rows($query_exec);
                                 if($rows == 0) { 
                                    echo "No Output"; 
                                 }
                                else  {            
                                echo "<table border='1'>
                                <tr>
                                <th>City</th>
                                <th>Year</th>
                                <th>Fastfood Consumption</th>
                                <tr>";
                                while($rows = mysqli_fetch_array($query_exec)){
                                  echo "<tr>";
                                  echo "<td>" . $rows['loc'] . "</td>";
                                  echo "<td>" . $rows['survey_year'] . "</td>";
                                  echo "<td>" . $rows['averageValue'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                    }
                                }
                                if($_GET['summary'] == "alcohol"){
                                    echo "Alcohol consumption survey information for : ".$useDB;
                                    $query_alc_cons = "select l.location_name as loc, sc.survey_date as survey_year, concat((count(sc.alcohol_cons)/l.no_of_people * 100),'%') AS alcohol_consumption
                                from survey_conducted sc, location l
                                where sc.location_id = l.location_id
                                and l.location_name = '".$useDB."'
                                and sc.alcohol_cons = true
                                group by sc.survey_date;";
                                $alco= mysqli_query($localhost,$query_alc_cons);
                                echo "<table border='1'>
                                <tr>
                                <th>City</th>
                                <th>Year</th>
                                <th>Alcohol Consumption</th>
                                <tr>";
                                while($rowAlco = mysqli_fetch_array($alco)){
                                  echo "<tr>";
                                  echo "<td>" . $rowAlco['loc'] . "</td>";
                                  echo "<td>" . $rowAlco['survey_year'] . "</td>";
                                  echo "<td>" . $rowAlco['alcohol_consumption'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                }
                                if($_GET['summary'] == "tobacco"){
                                    echo "Tobacco consumption survey information for : ".$useDB;
                                    $query_tbc_cons = "select l.location_name as loc, sc.survey_date as survey_year, concat((count(sc.tobacco_cons)/l.no_of_people * 100),'%') AS tobacco_consumption
                                from survey_conducted sc, location l
                                where sc.location_id = l.location_id
                                and l.location_name = '".$useDB."'
                                and sc.tobacco_cons = true
                                group by sc.survey_date;";
                                    $tbc= mysqli_query($localhost,$query_tbc_cons);
                                echo "<table border='1'>
                                <tr>
                                <th>City</th>
                                <th>Year</th>
                                <th>Tobacco Consumption</th>
                                <tr>";
                                while($rowTbc = mysqli_fetch_array($tbc)){
                                  echo "<tr>";
                                  echo "<td>" . $rowTbc['loc'] . "</td>";
                                  echo "<td>" . $rowTbc['survey_year'] . "</td>";
                                  echo "<td>" . $rowTbc['tobacco_consumption'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                }
                                if($_GET['summary'] == "accidents"){
                                    echo "<br/>"."Accidents survey information for : ".$useDB." for year 2012"."<br/>";
                                    $query_accidents = "select a.accident_type AS type, l.location_name as loc, count(a.accident_id) as countAcc
                                from accidents a, location l
                                where a.location_id = l.location_id
                                and l.location_name = '".$useDB."'
                                and a.accident_date LIKE '2012%'
                                group by a.accident_type;";
                                    $acc= mysqli_query($localhost,$query_accidents);
                                echo "<table border='1'>
                                <tr>
                                <th>City</th>
                                <th>Accident Type</th>
                                <th>Year</th>
                                <th>No. of Accidents Occured</th>
                                <tr>";
                                while($rowAcc = mysqli_fetch_array($acc)){
                                  echo "<tr>";
                                  echo "<td>" . $rowAcc['loc'] . "</td>";
                                  echo "<td>" . $rowAcc['type'] . "</td>";
                                  echo "<td>" . "2012" . "</td>";
                                  echo "<td>" . $rowAcc['countAcc'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                echo "<br/>"."Accidents survey information for : ".$useDB." for year 2013"."<br/>";
                                $query_accidents = "select a.accident_type AS type, l.location_name as loc, count(a.accident_id) as countAcc
                                from accidents a, location l
                                where a.location_id = l.location_id
                                and l.location_name = '".$useDB."'
                                and a.accident_date LIKE '2013%'
                                group by a.accident_type;";
                                $acc= mysqli_query($localhost,$query_accidents);
                                echo "<table border='1'>
                                <tr>
                                <th>City</th>
                                <th>Accident Type</th>
                                <th>Year</th>
                                <th>No. of Accidents Occured</th>
                                <tr>";
                                while($rowAcc = mysqli_fetch_array($acc)){
                                  echo "<tr>";
                                  echo "<td>" . $rowAcc['loc'] . "</td>";
                                  echo "<td>" . $rowAcc['type'] . "</td>";
                                  echo "<td>" . "2013" . "</td>";
                                  echo "<td>" . $rowAcc['countAcc'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                echo "<br/>"."Accidents survey information for : ".$useDB." for year 2014"."<br/>";
                                $query_accidents = "select a.accident_type AS type, l.location_name as loc, count(a.accident_id) as countAcc
                            from accidents a, location l
                            where a.location_id = l.location_id
                            and l.location_name = '".$useDB."'
                            and a.accident_date LIKE '2014%'
                            group by a.accident_type;";
                            $acc= mysqli_query($localhost,$query_accidents);
                            echo "<table border='1'>
                            <tr>
                            <th>City</th>
                            <th>Accident Type</th>
                            <th>Year</th>
                            <th>No. of Accidents Occured</th>
                            <tr>";
                            while($rowAcc = mysqli_fetch_array($acc)){
                              echo "<tr>";
                              echo "<td>" . $rowAcc['loc'] . "</td>";
                              echo "<td>" . $rowAcc['type'] . "</td>";
                              echo "<td>" . "2014" . "</td>";
                              echo "<td>" . $rowAcc['countAcc'] . "</td>";
                              echo "</tr>";
                            }
                            echo "</table>";
                            }
                            if($_GET['summary'] == "colevel"){
                                echo "Percentage of CO concentration in : ".$useDB;
                                $query_co_cons = "SELECT concat((co_level_value/sum(co_level_value)*100),'%') AS COValue, sc.survey_date as Year
                            FROM location, CO_level, survey_conducted AS sc
                            WHERE CO_level.location_id = location.location_id
                            AND location.location_id = sc.location_id
                            AND location.location_name = '".$useDB."'
                            GROUP BY sc.survey_date";
                                $co= mysqli_query($localhost,$query_co_cons);
                                echo "<table border='1'>
                                <tr>
                                <th>Year</th>
                                <th>COValue</th>
                                <tr>";
                                while($rowco = mysqli_fetch_array($co)){
                                  echo "<tr>";
                                  echo "<td>" . $rowco['Year'] . "</td>";
                                  echo "<td>" . $rowco['COValue'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                }
                                if($_GET['summary'] == "pollen"){
                                    echo "Percentage of Pollen concentration in : ".$useDB;
                                    $query_pollen_cons = "SELECT concat((pollen_conc_value/sum(pollen_conc_value)*100),'%') AS Pollen_Concentration, sc.survey_date as Year
                                FROM location, pollen_conc, survey_conducted AS sc
                                WHERE pollen_conc.location_id = location.location_id
                                AND location.location_id = sc.location_id
                                AND location.location_name = '".$useDB."'
                                GROUP BY sc.survey_date";
                                $pollenconc= mysqli_query($localhost,$query_pollen_cons);
                                echo "<table border='1'>
                                <tr>
                                <th>Year</th>
                                <th>Pollen_Concentration</th>
                                <tr>";
                                while($rowpollen = mysqli_fetch_array($pollenconc)){
                                  echo "<tr>";
                                  echo "<td>" . $rowpollen['Year'] . "</td>";
                                  echo "<td>" . $rowpollen['Pollen_Concentration'] . "</td>";
                                  echo "</tr>";
                                }
                                echo "</table>";
                                }
                                if($_GET['summary'] == "diseases"){
                                echo "<br/>"."Disease survey information for : ".$useDB." for year 2012"."<br/>";
	                            $query_diseases = "select cd.disease_name AS Disease, l.location_name as loc, count(sd.disease_id) as count_disease from chronic_diseases cd, location l, survey_conducted sc,survey_diseases sd
                            where sc.location_id = l.location_id and cd.disease_id=sd.disease_id and sc.survey_id=sd.survey_id 
                            and l.location_name = '".$useDB."'
                            and sc.survey_date=2012
                            group by cd.disease_name;";
	                        $disease= mysqli_query($localhost,$query_diseases);
                            echo "<table border='1'>
                            <tr>
                            <th>City</th>
                            <th>Disease</th>
                            <th>Number of people Diagnosed</th>
                            <th>Year</th>
                            <tr>";
                            while($rowd = mysqli_fetch_array($disease)){
                              echo "<tr>";
                              echo "<td>" . $rowd['loc'] . "</td>";
                              echo "<td>" . $rowd['Disease'] . "</td>";
                              echo "<td>" . $rowd['count_disease'] . "</td>";
                              echo "<td>" . "2012" . "</td>";
                              echo "</tr>";
                            }
                            echo "</table>";
                            echo "<br/>"."Disease survey information for : ".$useDB." for year 2013"."<br/>";
	                        $query_diseases = "select cd.disease_name AS Disease, l.location_name as loc, count(sd.disease_id) as count_disease
                            from chronic_diseases cd, location l, survey_conducted sc,survey_diseases sd
                            where sc.location_id = l.location_id and cd.disease_id=sd.disease_id and sc.survey_id=sd.survey_id 
                            and l.location_name = '".$useDB."'
                            and sc.survey_date=2013
                            group by cd.disease_name;";
	                            $disease= mysqli_query($localhost,$query_diseases);
                            echo "<table border='1'>
                            <tr>
                            <th>City</th>
                            <th>Disease</th>
                            <th>Number of people Diagnosed</th>
                            <th>Year</th>
                            <tr>";
                            while($rowd = mysqli_fetch_array($disease)){
                              echo "<tr>";
                              echo "<td>" . $rowd['loc'] . "</td>";
                              echo "<td>" . $rowd['Disease'] . "</td>";
                              echo "<td>" . $rowd['count_disease'] . "</td>";
                              echo "<td>" . "2013" . "</td>";
                              echo "</tr>";
                            }
                            echo "</table>";
                            echo "<br/>"."Disease survey information for : ".$useDB." for year 2014"."<br/>";
	                        $query_diseases = "select cd.disease_name AS Disease, l.location_name as loc, count(sd.disease_id) as count_disease
                            from chronic_diseases cd, location l, survey_conducted sc,survey_diseases sd
                            where sc.location_id = l.location_id and cd.disease_id=sd.disease_id and sc.survey_id=sd.survey_id 
                            and l.location_name = '".$useDB."'
                            and sc.survey_date=2014
                            group by cd.disease_name;";
	                        $disease= mysqli_query($localhost,$query_diseases);
                            echo "<table border='1'>
                            <tr>
                            <th>City</th>
                            <th>Disease</th>
                            <th>Number of people Diagnosed</th>
                            <th>Year</th>
                            <tr>";
                            while($rowd = mysqli_fetch_array($disease)){
                              echo "<tr>";
                              echo "<td>" . $rowd['loc'] . "</td>";
                              echo "<td>" . $rowd['Disease'] . "</td>";
                              echo "<td>" . $rowd['count_disease'] . "</td>";
                              echo "<td>" . "2014" . "</td>";
                              echo "</tr>";
                            }
                            echo "</table>";
                            }
                            }
                        }
                        else{
                            echo "missing fields";
                        }
        ?>
            <form action="SummaryHealthInfo.php" method="get">
                <input type="submit" value="Home" name="homeButton" id="homeButton" />
            </form>
            <?php
                if(isset($_GET['homeButton']))
                {
                    header("Location:HomeScreen.php");
                    exit;
                }
            ?>
            <form action="SummaryHealthInfo.php" method="get">
                <input type="submit" value="Back" name="backButton" id="backButton" />
            </form>
            <?php
                if(isset($_GET['backButton']))
                {
                    header("Location:SurveyTownSel.php");
                    exit;
                }
            ?>
    </div>
    </body>
</html>
