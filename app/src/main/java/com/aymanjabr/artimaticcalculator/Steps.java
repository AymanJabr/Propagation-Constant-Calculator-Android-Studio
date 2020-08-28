package com.aymanjabr.artimaticcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Steps extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Bundle b = getIntent().getExtras();

        double muValue = b.getDouble("mu");
        double sigmaValue = b.getDouble("sigma");
        double omegaValue = b.getDouble("omega");
        double epsilonValue = b.getDouble("epsilon");
        double aValue = b.getDouble("a");
        double bValue = b.getDouble("b");
        double rValue = b.getDouble("r");
        double thetaValue = b.getDouble("theta");
        String finalAnswerString = b.getString("finalAnswerString");




        final Button ebutton1 = findViewById(R.id.ebutton1);
        final Button ebutton2 = findViewById(R.id.ebutton2);

        final TextView estep1Txt = findViewById(R.id.estep1Txt);
        final TextView estep2Txt = findViewById(R.id.estep2Txt);

        final TextView step1Txt = findViewById(R.id.step1Txt);
        final TextView step2Txt = findViewById(R.id.step2Txt);
        final TextView step3Txt = findViewById(R.id.step3Txt);


        step1Txt.setVisibility(View.GONE);
        step2Txt.setVisibility(View.GONE);


        estep1Txt.setText(omegaValue + "j x √(" + muValue + "(" +
                epsilonValue + " - (" + sigmaValue + "/" + omegaValue + ")j ))");
        step1Txt.setText("= √(" + omegaValue+ "²j² x " + muValue + "x(" + epsilonValue + " - (" + sigmaValue + "/" + omegaValue + ")j) )" +
                "\n= √(-1)x" + omegaValue + "² x " + muValue + "x(" + epsilonValue + " - (" + sigmaValue + "/" + omegaValue + ")j) )" +
                "\n= √(-" + omegaValue + "² x " + muValue + "x(" + epsilonValue + ") + " + omegaValue + "² x (" + sigmaValue + "/" + omegaValue + ")j )" +
                "\n= √(-" + omegaValue + "² x " + muValue + "x(" + epsilonValue + ") + " + sigmaValue + " x " + omegaValue + "j )" +
                "\n= √(a + bj)" +
                "\na = -" + omegaValue + "² x " + muValue + "x(" + epsilonValue + ")" +
                "\nb = "+ sigmaValue + " x " + omegaValue);


        estep2Txt.setText(aValue + "  " + bValue + "j");
        step2Txt.setText("polar form is given by r(cos(θ)+jsin(θ))" +
                "\nwhere r=√(a²+b²) and θ= atan(b/a)" +
                "\na = " + aValue + " b = " + bValue +
                "\nr = √(" + aValue + ")² + (" + bValue + ")² )" +
                "\nr = " + rValue +
                "\ntheta = atan(" + bValue + " / " + aValue + ")" +
                "\ntheta = " + thetaValue);

        step3Txt.setText("r = " + rValue + "  theta: " + thetaValue + "\nfinal answer: " + finalAnswerString);


        ebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (step1Txt.getVisibility() == View.GONE) {
                    step1Txt.setVisibility(View.VISIBLE);
                    ebutton1.setText("-");
                } else {
                    step1Txt.setVisibility(View.GONE);
                    ebutton1.setText("+");
                }


            }
        });


        ebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step2Txt.getVisibility() == View.GONE) {
                    step2Txt.setVisibility(View.VISIBLE);
                    ebutton2.setText("-");
                } else {
                    step2Txt.setVisibility(View.GONE);
                    ebutton2.setText("+");
                }
            }
        });


    }
}


