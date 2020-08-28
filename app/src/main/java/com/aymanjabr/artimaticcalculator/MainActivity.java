package com.aymanjabr.artimaticcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {



static double piValue = Math.PI;
static double muNodeValue = 4 * Math.PI * Math.pow(10,-7);
static double epsilonNodeValue = 8.85419 * Math.pow(10,-12);

String muString, epsilonString,omegaString,sigmaString, finalAnswerString;
double muValue, epsilonValue, omegaValue, sigmaValue, finalAnswer;

double aValue,bValue,rValue,thetaValue ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         final Button showStepsBtn = findViewById(R.id.showStepsBtn);
         Button solveBtn = findViewById(R.id.solveBtn);


         final EditText omegaTxt = findViewById(R.id.omegaTxt), epsilonTxt = findViewById(R.id.epsilonTxt), sigmaTxt = findViewById(R.id.sigmaTxt),
                muTxt = findViewById(R.id.muTxt);
         final TextView finalSolutionTxt = findViewById(R.id.finalSolution);
        final TextView solutionTxt = findViewById(R.id.solutionTxt);


         solveBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 muString = muTxt.getText().toString();
                 epsilonString = epsilonTxt.getText().toString();
                 omegaString = omegaTxt.getText().toString();
                 sigmaString = sigmaTxt.getText().toString();

                 boolean allTextValid = isTextValid(muString) && isTextValid(epsilonString) && isTextValid(omegaString) && isTextValid(sigmaString);
                 if(!allTextValid){
                     Toast.makeText(getApplicationContext(),"One or more of the input fields is invalid, please make sure to use correct keyboard, and to leave no empty inputs",Toast.LENGTH_LONG).show();
                 } else {
                     muString = convertString(muString);
                     epsilonString = convertString(epsilonString);
                     omegaString = convertString(omegaString);
                     sigmaString = convertString(sigmaString);

                     muValue = eval(muString);
                     epsilonValue = eval(epsilonString);
                     omegaValue = eval(omegaString);
                     sigmaValue = eval(sigmaString);


                     aValue = -Math.pow(omegaValue,2)*muValue*epsilonValue;
                     bValue = omegaValue*sigmaValue;
                     rValue = Math.sqrt((Math.pow(aValue, 2) + Math.pow(bValue, 2)));
                     thetaValue = Math.atan(bValue / aValue);
                     finalAnswerString = rValue + "\n  (cos(" + thetaValue +
                     ") + sin(" + thetaValue + ")j )";


                     finalSolutionTxt.setText(finalAnswerString);


                     finalSolutionTxt.setVisibility(View.VISIBLE);
                     showStepsBtn.setVisibility(View.VISIBLE);
                     solutionTxt.setVisibility(View.VISIBLE);
                 }


//                to show/hide elements
//                 https://stackoverflow.com/questions/42608060/android-hide-an-element
             }
         });



        showStepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Steps.class);
                Bundle b = new Bundle();

                b.putDouble("mu",muValue);
                b.putDouble("sigma",sigmaValue);
                b.putDouble("omega",omegaValue);
                b.putDouble("epsilon",epsilonValue);
                b.putDouble("a",aValue);
                b.putDouble("b",bValue);
                b.putDouble("r",rValue);
                b.putDouble("theta",thetaValue);
                b.putDouble("pi", piValue);
                b.putString("finalAnswerString", finalAnswerString);
                i.putExtras(b);

                startActivity(i);
            }
        });



    }


     public boolean isTextValid(String originalString){
        // boolean that is used to verify if the string is true
        boolean validInput = true;
        //int that is used to make sure that the brackets count is right.
        int bracketsCounter = 0;
        String noWhiteSpaceString = originalString.replaceAll(" ", "");
        if(noWhiteSpaceString.length() >  0){ // makes sure string is not empty
            for(int i = 0; i< noWhiteSpaceString.length(); i++){
                switch (noWhiteSpaceString.charAt(i)) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '(':
                    case ')':
                    case 'π':
                    case '^':
                    case 'μ':
                    case 'ε':
                    case '+':
                    case '-':
                    case '/':
                    case 'x':
                    case '.':
                        break;
                    default:
                        validInput = false;
                        Log.e("Valid Input", validInput + " Letter not within recognised in: " + originalString);
                        break;
                }
                if(noWhiteSpaceString.charAt(i) == '('){
                    bracketsCounter++;
                } else if(noWhiteSpaceString.charAt(i) == ')'){
                    bracketsCounter--;
                }

                if(bracketsCounter < 0) {
                    validInput = false;
                    Log.e("Valid Input", validInput + " Brackets not valid in: " + originalString);
                }
            }

            //makes sure that the last character is valid
            char lastChar = noWhiteSpaceString.charAt(noWhiteSpaceString.length() - 1);
        if( lastChar == '(' && lastChar == '/' && lastChar == '^' && lastChar == '*' && lastChar == '+' && lastChar == '-' && lastChar == '.' ){
            validInput = false;
            Log.e("Valid Input", validInput + " Last character not valid in: " + originalString);
        }

        } else {
            validInput = false;
            Log.e("Valid Input", validInput + " Letters not enough in: " + originalString);
        }
        return validInput;
    }

    public String convertString(String str){


         str = str.replaceAll("π", " " + String.valueOf(piValue) + " ");
         str = str.replaceAll("μ", " " + String.valueOf(muNodeValue) + " ");
         str = str.replaceAll("ε", " " + String.valueOf(epsilonNodeValue) + " ");
         str = str.replaceAll("E","^");
        str = str.replaceAll("x","*");

         return str;
    }


    //Used to evaluate a string and return it as a double
    public double eval(final String str) {
        //here we are creating an object which will be parsed to a string???
        return new Object() {
            int pos = -1, ch;

            //if the pos is not greater than the overall length of the string, we take the next
            //character in the string
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            //checks if the current character is equal to the charToEat character, and checks
            //for empty spaces
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            //
            //checks is the pos is less than the overall string length, if it is, then an unexpected character was present.
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
                    Log.e("Parsing Error: ","Unexpected: " + (char) ch);
                    Toast.makeText(getApplicationContext(), "Unexpected Character: " + (char)ch, Toast.LENGTH_LONG).show();
                }
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else {
                        Log.e("Parsing Error: ","Unknown function: " + func);
                        Toast.makeText(getApplicationContext(), "Unknown function: " + func, Toast.LENGTH_LONG).show();
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }


}