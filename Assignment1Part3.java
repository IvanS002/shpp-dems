package com.shpp.rshmelev.cs.ishestachenko.assignment1;
import com.shpp.karel.KarelTheRobot;

public class Assignment1Part3 extends KarelTheRobot{


    public void turnRight() throws Exception{
//        * Makes Karel turn right by turning left for 3 times at the same place.
        for (int i = 0; i < 3; i++){
            turnLeft();
        }

    }


    public void turnAround() throws Exception{
//        * Makes Karel turn around by turning left for 2 times at the same place.
        for (int i = 0; i < 2; i++){
            turnLeft();
        }

    }


    public void goTowardsTheFrontWall() throws Exception{
//        * Makes Karel approach the wall in front of him.
        while(!frontIsBlocked()){
            move();
        }
    }


    public void buildSquareDiagonal() throws Exception{
//        **
//        * According to the task, eastern and western walls never can be longer that southern and northern ones.
//        * --> World can always contain a square that contains the southern wall.
//        * This method makes Karel build such diagonals with beepers for future use of location of their crossing.
//        * When the method is called, Karel might face North or South, "if's" below operate with each of these options.
//        **
        if (facingNorth()){
            while (!rightIsBlocked()) {
                turnRight();
                move();
                turnLeft();
                move();
                putBeeper();
            }
        }

        else if (facingSouth()){
            while (!leftIsBlocked()) {
                turnLeft();
                move();
                turnRight();
                move();
                putBeeper();
            }

        }
    }


    public void findMiddle() throws Exception{
//                **
//                * When the method is called, Karel is in the south-eastern corner of the world, facing West.
//                * He has already built 2 "square" diagonals according to description of the method buildSquareDiagonals().
//                * The middle point of the southern line will be defined by the location of crossing of those diagonals.
//                *
//                * If the southern line consists of odd number of cells, crossing of diagonals looks like that:
//                *                                    *      *
//                *                                       **
//                *                                    *      *
//                * Distance between beepers on the lowest line is 1 free cell.
//                *
//                * If the southern line consists of even number of cells, crossing of diagonals looks like that:
//                *                                    *        *
//                *                                       *  *
//                *                                       *  *
//                *                                    *        *
//                * On the 2nd & 3rd line, there are no free cells between beepers.
//                *
//                * Method makes Karel move along each horizontal line of the world until he finds a place, where
//                * beepers are 1 free cell away form each other, or are located in the adjoining cells.
//                * Each time when any of these conditions isn't met, Karel removes beeper from the cell (if there's one).
//                * If beepers are 1 free cell from each other, Karel removes the first beeper he spotted -
//                * it's done to remove beeper from the south-eastern corner, in case when southern wall consists of 3 cells.
//                **
        while (!frontIsBlocked()){
            if (beepersPresent()){
                move();
                if (beepersPresent() && facingWest()){
                    turnLeft();
                    goTowardsTheFrontWall();
                    putBeeper();
                }
                else if (beepersPresent() && facingEast()){
                    turnRight();
                    goTowardsTheFrontWall();
                    putBeeper();
                }
                else{
                    move();
                    if (beepersPresent() && facingEast()){
                        turnAround();
                        move();
                        move();
                        pickBeeper();
                        turnAround();
                        move();
                        turnRight();
                        goTowardsTheFrontWall();
                        putBeeper();

                    }
                    else if (beepersPresent() && facingWest()){
                        turnAround();
                        move();
                        move();
                        pickBeeper();
                        turnAround();
                        move();
                        turnLeft();
                        goTowardsTheFrontWall();
                        putBeeper();
                    }
                    else{
                        turnAround();
                        move();
                        move();
                        turnAround();
                        pickBeeper();
                    }
                }

            }
            else{
                move();
                if (frontIsBlocked() && facingWest()){
                    while(beepersPresent()){
                        pickBeeper();
                    }
                    turnRight();
                    move();
                    turnRight();
                }
                else if(frontIsBlocked() && facingEast()){
                    while(beepersPresent()){
                        pickBeeper();
                    }
                    turnLeft();
                    move();
                    turnLeft();
                }
            }
        }
    }


    public void pickExtraBeepers() throws Exception{
//        **
//        * Karel is starting one cell leftwards from the middle of the southern line, facing West.
//        * Method makes Karel move along each line and pick all the beepers.
//        * Beeper in the south-eastern corner was removed in process of search for the middle of the southern line,
//        * so there's no need to check that corner. Beeper in the middle of the southern line shouldn't be removed.
//        **
        while (!frontIsBlocked()){
            while(beepersPresent()){
                pickBeeper();
            }
            move();
            if (frontIsBlocked() && facingEast()){
                while(beepersPresent()){
                    pickBeeper();
                }
                turnLeft();
                if (!frontIsBlocked()){
                    move();
                    turnLeft();
                }

            }
            else if(frontIsBlocked() && facingWest()){
                while(beepersPresent()){
                    pickBeeper();
                }
                turnRight();
                if (!frontIsBlocked()){
                    move();
                    turnRight();
                }

            }

        }
    }



    public void run() throws Exception{
        putBeeper();
        if (!frontIsBlocked()){
//            * if the world's southern wall has more than 1 cell
            move();
            if(!frontIsBlocked()){
//                * if the world's southern wall has more than 2 cells
                turnLeft();
                move();
                putBeeper();
//              * Karel built a single element of the "square" diagonal.
                buildSquareDiagonal();
                turnLeft();
                goTowardsTheFrontWall();
                turnLeft();
                putBeeper();
                buildSquareDiagonal();
                turnRight();
                findMiddle();
                turnRight();
                move();
                pickExtraBeepers();
            }

        }
    }

}
