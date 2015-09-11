<?php
 if(session_id == '' or (session_status != 'PHP_SESSION_ACTIVE')) 
                {
                session_start();
                }
?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Summary</title>
        <!-- CSS for buttons-->
        <style>
                .forButtons {
                background-color:#808080;
                border-radius:6px;
                border:1px solid #c584f3;
                color:#000;
                }
        </style>
    </head>
    <body>
        <div align="center">
            <h3 style="font-family:verdana; font-size:120%; color:#766565">Summary of Health Surveillance</h3>
            <h6 style="font-family:verdana; font-size:90%; color:#766565"><i>View the summary of health surveys - Based on Location</i></h6>
            </div>
            <form action="SurveyTownSel.php" method="get">
                <label style="font-family:verdana; font-size:80%; color:#766565; font-style: oblique;"> Select the location you wish to view the survey of: </label>
                    <br/>
<input type="radio" id="town" name="town" value="townA">Town A<br/>
<input type="radio" id="town" name="town" value="townB">Town B<br/>
<input type="radio" id="town" name="town" value="townC">Town C

                <br/>
                <br/>
                <div align="center">
                    <input type="submit" value="Survey Details" name="viewSummary" id="viewSummary" class="forButtons" />
                </div>
             </form>   
            <?php
                if (isset($_GET['viewSummary'])) {
                    if(isset($_GET['town']))
                    {
                    echo "You have selected :".$_GET['town'];  //  Displaying Selected Value
                    $selectedTown = $_GET['town'];
                    $_SESSION['selectedTown'] = $_GET['town'];
                    echo "<h4>Health Information</h4>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=fruits\">"."Fruits Consumption"."</a>"."<br/>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=fastfood\">"."Fast Food"."</a>"."<br/>";
                    echo "<h4>Habits</h4>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=alcohol\">"."Alcohol Consumption"."</a>"."<br/>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=tobacco\">"."Tobacco Consumption"."</a>"."<br/>";
                    echo "<h4>Environment</h4>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=colevel\">"."CO level"."</a>"."<br/>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=pollen\">"."Pollen Concentration"."</a>"."<br/>";
                    echo "<h4>Saftey - Accidents</h4>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=accidents\">"."Accidents occured"."</a>"."<br/>";
                    echo "<h4>Diseases</h4>";
                    echo "<a href=\"SummaryHealthInfo.php?summary=diseases\">"."Chronic Diseases"."</a>"."<br/>";
                    }
                }
            ?>
    </body>
</html>
