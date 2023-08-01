package com.chydik0;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;

public class CalculateBMI extends HttpServlet {
    private String classification = "";
    private String color = "";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.printf("""
                           <!doctype html>
                           <html lang='en'>
                               <head>
                                   <meta charset='UTF-8'>
                                   <meta name='viewport' content='width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0'>
                                   <meta http-equiv='X-UA-Compatible' content='ie=edge'>
                                   <title>Calculator</title>
                                   <link href='%s/static/css/style.css' rel='stylesheet'/>
                              	</head>
                                        
                               <body>
                               	<div class="container">
                                   	<form action="" method="post">
                                       	<h1>BMI Calculator</h1>
                                           <div class="input-group">
                                           	<label for="weight">Weight (kg):</label>
                                               <input type="number" id="weight" name="weight" placeholder="Enter your weight">
                                           </div>
                                           <div class="input-group">
                                             	<label for="height">Height (cm):</label>
                                              	<input type="number" id="height" name="height" placeholder="Enter your height">
                                           </div>
                                           <button id="button" type="submit">Calculate BMI</button>
                                           <div id="result" style= "color = %s;">
                                             	<p>
                                              		Your BMI: %s
                                              	</p>                                         
                                               <p>
                                                 	Classification: %s
                                               </p>
                                           </div>    
                                       </form>
                                   </div>
                               </body>
                           </html>
                        """,
                req.getContextPath(),
                color,
                getParametersFromReq(req),
                classification

        );
    }

    private boolean isNumber(String parameter) {
        return parameter != null && parameter.matches("\\d+");
    }

    private double calculateBMI(double weight, double height) {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    private String bmiWithFormat(double bmi) {
        Formatter formatter = new Formatter();
        formatter.format("%.2f", bmi);
        return formatter.toString();
    }

    private String getParametersFromReq(HttpServletRequest req) {
        String weightParam = req.getParameter("weight");
        String heightParam = req.getParameter("height");
        double bmi = 0d;

        if (isNumber(weightParam) && isNumber(heightParam)) {
            double weight = Double.parseDouble(weightParam);
            double height = Double.parseDouble(heightParam);
            bmi = calculateBMI(weight, height);

            selectColorAndClassification(bmi);
        }
        return bmiWithFormat(bmi);
    }

    private void selectColorAndClassification(double bmi) {
        if (bmi < 16) {
            classification = "Severe Thinness";
            color = "#BC2020";
        } else if (bmi >= 16 && bmi < 17) {
            classification = "Moderate Thinness";
            color = "#D38888";
        } else if (bmi >= 17 && bmi < 18.5) {
            classification = "Mild Thinness";
            color = "#FFE400";
        } else if (bmi >= 18.5 && bmi < 25) {
            classification = "Normal";
            color = "#008137";
        } else if (bmi >= 25 && bmi < 30) {
            classification = "Overweight";
            color = "#FFE400";
        } else if (bmi >= 30 && bmi < 35) {
            classification = "Obese Class I";
            color = "#D38888";
        } else if (bmi >= 35 && bmi < 40) {
            classification = "Obese Class II";
            color = "red";
        } else {
            classification = "Obese Class III";
            color = "#8A0101";
        }
    }
}

/*
<script type="text/javascript">
                                        function changeTextColor(color) {
                                            alert(color);
                                            document.getElementById("result").style.color = color;
                                        }

                                        var buttonElement = document.getElementById("button");

                                        buttonElement.addEventListener("click", function() {
                                        alert("Кнопка была нажата! addEventListener");
                                        changeTextColor('%s');
                                   		});
                                   </script>
 */