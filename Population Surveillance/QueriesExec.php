<?php
    
    $con=mysqli_connect("omega.uta.edu","pvn4560","yBaxS4KE6j","pvn4560");
    // Check connection
    if (mysqli_connect_errno()) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
?>

<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8" />
        <title> Execute Queries </title>
    </head>
    <body>

        <div align="center">

            <h2>Query Execution </h2>
        </div>

        <form action="QueriesExec.php" method="get">

            <h6 style="font-family: Arial; font-size: medium"> Query1. The surveyed female population for each town during the year 2012.
                <input type="submit" value="Execute" name="query1" id="query1" />
            </h6>
        </form>
        <?php
                        if(isset($_GET['query1']))
                    {
            $result = mysqli_query($con,"SELECT count(survey_id) AS SURVEYED_FEMALES, location_name FROM survey_conducted AS SC JOIN location AS L ON SC.location_id = L.location_id WHERE gender='FEMALE' and SC.survey_date=2012 group by SC.location_id;");
            
            echo "<table border='1'>
            <tr>
            <th>SURVEYED_FEMALES</th>
            <th>location_name</th>
            </tr>";
            
            while($row = mysqli_fetch_array($result)) {
              echo "<tr>";
              echo "<td>" . $row['SURVEYED_FEMALES'] . "</td>";
              echo "<td>" . $row['location_name'] . "</td>";
              echo "</tr>";
            }
            
            echo "</table>";
            
                   }
                    else
                    {
                        }
        ?>



        <form action="QueriesExec.php" method="get">

            <h6 style="font-family: Arial; font-size: medium"> Query2.  Most common accident type for Town A.
                <input type="submit" value="Execute" name="query2" id="query2" />

            </h6>
        </form>
        <?php
                        if(isset($_GET['query2']))
                    {
            $con=mysqli_connect("omega.uta.edu","pvn4560","yBaxS4KE6j","pvn4560");
            // Check connection
            if (mysqli_connect_errno()) {
              echo "Failed to connect to MySQL: " . mysqli_connect_error();
            }
            
            $result = mysqli_query($con,"SELECT accident_occurence, location_name, accident_type
            FROM (SELECT COUNT( accident_id ) AS accident_occurence, location_name, accident_type
            FROM accidents
            INNER JOIN location ON location.location_id = accidents.location_id and location.location_name = 'Town A'
            GROUP BY location_name, accident_type
            ORDER BY accident_occurence DESC
            ) AS A
            GROUP BY A.location_name;
            ");
            
            echo "<table border='1'>
            <tr>
            <th>accident_occurence</th>
            <th>location_name</th>
            <th>accident_type</th>
            </tr>";
            
            while($row = mysqli_fetch_array($result)) {
              echo "<tr>";
              echo "<td>" . $row['accident_occurence'] . "</td>";
              echo "<td>" . $row['location_name'] . "</td>";
               echo "<td>" . $row['accident_type'] . "</td>";
              echo "</tr>";
            }
            
            echo "</table>";
                    }
                    else{
            
                    }
            
        ?>


        <form action="QueriesExec.php" method="get">

            <h6 style="font-family: Arial; font-size: medium"> Query3. Prevalence of diabetes in Town B for the year 2014.
                <input type="submit" value="Execute" name="query3" id="query3" />

            </h6>
        </form>

        <?php
                        if(isset($_GET['query3']))
                    {
            $con=mysqli_connect("omega.uta.edu","pvn4560","yBaxS4KE6j","pvn4560");
            // Check connection
            if (mysqli_connect_errno()) {
              echo "Failed to connect to MySQL: " . mysqli_connect_error();
            }
            
            $result = mysqli_query($con," SELECT l.location_name, l.location_city, l.location_state, l.location_zip_code, sc.survey_date AS survey_year, cd.disease_name, (
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
            ");
            
            echo "<table border='1'>
            <tr>
            <th>location_name</th>
            <th>location_city</th>
            <th>location_state</th>
            <th>location_zip_code</th>
            <th>survey_year</th>
            <th>disease_name</th>
            <th>PREVALENCE</th>
            </tr>";
            
            while($row = mysqli_fetch_array($result)) {
              echo "<tr>";
              echo "<td>" . $row['location_name'] . "</td>";
              echo "<td>" . $row['location_city'] . "</td>";
               echo "<td>" . $row['location_state'] . "</td>";
               echo "<td>" . $row['location_zip_code'] . "</td>";
               echo "<td>" . $row['survey_year'] . "</td>";
               echo "<td>" . $row['disease_name'] . "</td>";
               echo "<td>" . $row['PREVALENCE'] . "</td>";
              echo "</tr>";
            }
            
            echo "</table>";
            
            }
            
              else
                    {
                    }
        ?>


        <form action="QueriesExec.php" method="get">

            <h6 style="font-family: Arial; font-size: medium"> Query4.  Town with the lowest diabetes prevalence for the year 2012.
                <input type="submit" value="Execute" name="query4" id="query4" />

            </h6>
        </form>
        <?php
                        if(isset($_GET['query4']))
                    {
            
            $con=mysqli_connect("omega.uta.edu","pvn4560","yBaxS4KE6j","pvn4560");
            // Check connection
            if (mysqli_connect_errno()) {
              echo "Failed to connect to MySQL: " . mysqli_connect_error();
            }
            
            $result = mysqli_query($con,"SELECT MIN(PREVALENCE) as prevalence, location_name AS name, location_state AS state, location_zip_code AS zip
            FROM SURVEY_MIN_DIABETES;");
            
            echo "<table border='1'>
            <tr>
            <th>Location</th>
            <th>State</th>
            <th>Zip Code</th>
            <th>Prevalence</th>
            </tr>";
            
            while($rowPrevalence = mysqli_fetch_array($result)) {
              echo "<tr>";
              echo "<td>" . $rowPrevalence['name'] . "</td>";
              echo "<td>" . $rowPrevalence['state'] . "</td>";
              echo "<td>" . $rowPrevalence['zip'] . "</td>";
              echo "<td>" . $rowPrevalence['prevalence'] . "</td>";
              echo "</tr>";
            }
            
            echo "</table>";
            }
            else{
            
            }
            
            
        ?>




        <form action="QueriesExec.php" method="get">
            <h6 style="font-family: Arial; font-size: medium"> Query5. The town(s) where no accidents have been reported.
                <input type="submit" value="Execute" name="query5" id="query5" />

            </h6>
        </form>

        <?php
                         if(isset($_GET['query5']))
                    {
            
            $con=mysqli_connect("omega.uta.edu","pvn4560","yBaxS4KE6j","pvn4560");
            // Check connection
            if (mysqli_connect_errno()) {
              echo "Failed to connect to MySQL: " . mysqli_connect_error();
            }
            
            $result = mysqli_query($con,"Select l.location_name,l.location_city, l.location_state, l.location_zip_code from location l where NOT EXISTS
            ( select a.location_id  from accidents a 
            WHERE a.location_id = l.location_id);"
            );
            
            echo "<table border='1'>
            <tr>
            <th> location_name</th>
            <th> location_city</th>
            <th> location_state</th>
            <th> location_zip_code</th>
            </tr>";
            
            while($row = mysqli_fetch_array($result)) {
              echo "<tr>";
                echo "<td>" . $row['location_name'] . "</td>";
                echo "<td>" . $row['location_city'] . "</td>";
                echo "<td>" . $row['location_state'] . "</td>";
                echo "<td>" . $row['location_zip_code'] . "</td>";
              echo "</tr>";
            }
            
            echo "</table>";
            }
            else{
            
            }
            
            
        ?>


        <form action="QueriesExec.php" method="get">
            <h6 style="font-family: Arial; font-size: medium"> Query6.   The availability of health resources for Town C for the whole observation period. This is the ratio of citizens per health infrastructure.
                <input type="submit" value="Execute" name="query6" id="query6" />
            </h6>
        </form>
        <?php
                        if(isset($_GET['query6']))
                    {
            
            $con=mysqli_connect("omega.uta.edu","pvn4560","yBaxS4KE6j","pvn4560");
            // Check connection
            if (mysqli_connect_errno()) {
              echo "Failed to connect to MySQL: " . mysqli_connect_error();
            }
            
            $result = mysqli_query($con,"SELECT population / hospitalcount AS hospital_ratio, population / rehabCount AS rehabcenter_ratio
            FROM HospitalCount, RehabCount, population;"
            );
            
            echo "<table border='1'>
            <tr>
            <th>hospital_ratio</th>
            <th> rehabcenter_ratio</th>
            </tr>";
            
            while($row = mysqli_fetch_array($result)) {
              echo "<tr>";
                echo "<td>" . $row['hospital_ratio'] . "</td>";
                echo "<td>" . $row['rehabcenter_ratio'] . "</td>";
                echo "</tr>";
            }
            
            echo "</table>";
            }
            else{
            
            }
            
            
        ?>


        <form action="QueriesExec.php" method="get">
            <h6 style="font-family: Arial; font-size: medium"> Query7.  The change in the smoking habit between in 2014 compared to 2012, for all towns.
                <input type="submit" value="Execute" name="query7" id="query7" />
            </h6>
        </form>
        <?php
                         if(isset($_GET['query7']))
                    {
            $con=mysqli_connect("omega.uta.edu","pvn4560","yBaxS4KE6j","pvn4560");
            // Check connection
            if (mysqli_connect_errno()) {
              echo "Failed to connect to MySQL: " . mysqli_connect_error();
            }
            
            $result = mysqli_query($con,"SELECT Smoking_2012-Smoking_2014 as Change_in_habit, Smoking_2014.location_name as Location
from Smoking_2012 join Smoking_2014 ON Smoking_2012.location_name = Smoking_2014.location_name
group by Smoking_2014.location_name;"
            );
            
            echo "<table border='1'>
            <tr>
            <th>Location</th>
            <th>Change in habit</th>
            </tr>";
            
            while($row = mysqli_fetch_array($result)) {
              echo "<tr>";
              echo "<td>" . $row['Location'] . "</td>";
                echo "<td>" . $row['Change_in_habit'] . "</td>";
                echo "</tr>";
            }
            
            echo "</table>";
            }
            else
            {
            
            }
            
            mysqli_close($con);
        ?>
        <br />
        <br />
        <div align="center">

            <form action="QueriesExec.php" method="get">

                <input type="submit" value="Back" name="backButton" id="backButton" />

            </form>
            <?php
                if(isset($_GET['backButton']))
                {
                    header("Location:HomeScreen.php");
                    exit;
                }
                
            ?>
            <br />
            <form action="QueriesExec.php" method="get">

                <input type="submit" value="Summary" name="summarybutton" id="summarybutton" />

            </form>
            <?php
                if(isset($_GET['summarybutton']))
                {
                    header("Location:SurveyTownSel.php");
                    exit;
                }
                
            ?>
        </div>
    </body>
</html>
