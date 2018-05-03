package com.company;

public class Main {

    private static double lastCalculation;
    private static final int maxNumber = 8999999;
    private static int numberOfIterations = 0;


    public static void main(String[] args) {
	// write your code here


        double squareRootNumber = 250;


        squareRoot(squareRootNumber, 5*squareRootNumber, 5);

        System.out.println();

        System.out.println(babyloniansAlgo(squareRootNumber,10));

             //for at se Big O notation, se venligst vedhæftet PDF dokument fra ZIP filen.

    }

    /**
     *
     * @param squareRoot is the number you want the squareroot of
     * @param guess is your own guess for what the squareroot could possibly be of that number. The further away your guess is, the more operations will be required
     * @return returns the actual squareroot of squareRoot number
     */
    private static double babyloniansAlgo(double squareRoot, double guess) {


        numberOfIterations++;
        double divison = squareRoot / guess;

        //divison is our "new guess". Vi tager gennemsnitet af vores original gæt og vores nye gæt
        guess = (divison + guess) / 2;


        if (guess == lastCalculation) {

            System.out.print("\n and the squareroot of " + squareRoot + " according to Babylon's method is: ");
            return guess;
        }

        lastCalculation = guess;

        System.out.println("This is the current result from this iteration: " + guess);

        return babyloniansAlgo(squareRoot, guess);
    }


    /**
     *
     * @param xNumber is the number you want the squareroot of
     * @param a  number that helps you find the squareroot. A = 5*xNumber
     * @param b static number that's also needed for this method. B=5
     * @return returns the actual squareroot of xNumber
     */
    private  static int squareRoot(double xNumber, double a, int b){



        if (xNumber < maxNumber) {

            try {

                if (a >= b) {

                    a = a - b;
                    b = b + 10;

                    System.out.println("This is IF(R1): (" + a + ", " + b + ")");
                    numberOfIterations++;

                    return squareRoot(xNumber, a, b);

                } else if (a < b) {

                    a = a * 100; // adds two zeros to the end of a


                    String bString = String.valueOf(b);
                    StringBuilder builder = new StringBuilder(bString);


                    int index = builder.length() - 1; //index before the last digit, to insert at.
                    builder.insert(index, "0"); // inserts a 0 before the last index.

                    String bFinal = String.valueOf(builder); //coverts StringBuilder to String
                    b = Integer.parseInt(bFinal); //from here we can convert String to int


                    System.out.println("This is ELSE(R2): (" + a + ", " + b + ")");
                   numberOfIterations++;

                    return squareRoot(xNumber, a, b);


                }




                /**
                 * When the numbers becomes too big to be an int and therefore becomes a long,
                 * catch the error and stop the recursion, returning the squareroot with just enough decimals.
                 */
            } catch (NumberFormatException e) {



                String bString = String.valueOf(b);
                StringBuilder squareRoot = new StringBuilder(bString);

                if (xNumber < 1)
                    squareRoot.insert(0,"0,");

                else if (xNumber > 1 && xNumber < 10) {
                    squareRoot.insert(1, ",");

                } else if (xNumber > 10 && xNumber < 10000) {
                    squareRoot.insert(2, ",");

                } else if (xNumber > 10000 && xNumber < 1000000) {
                    squareRoot.insert(3, ",");

                } else
                    squareRoot.insert(4, ",");

                System.out.println();
                System.out.println("This is the squareroot of according sqaure root by substraction " + xNumber + " = " + squareRoot);
                System.out.println("It took this many iterations: "+ numberOfIterations);
            }
        }
        else
            System.out.println("The number is out of the range provided by this method. Try the squareroot of any number below " + maxNumber);

        return b;
    }

}
