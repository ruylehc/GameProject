/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author PLUCSCE
 */
public class AI {
    private GameModel gmodel;
    //private ClientModel model; will check back
    //public int[][] boardArray;
    
    // attempt to put it here so we can use default constructor
    boolean turnHolder;
    int difficulty;
    int ThreatLvl1; // pair of hausers, blocked or not -- easy
    int ThreatLvl2; // 3 hausers blocked on 1 side -- easy
    int ThreatLvl3; // open space 3 Hausers -- easy
    int ThreatLvl4; // 2 hausers, empty grid, 1
    int ThreatLvl5; // 4 Hausers blocked on 1 side -- easy
    int ThreatLvl6; // 2 hausers, space, 2 hauser
    int ThreatLvl7; // 3 hausers, space, 1+ hauser
    int ThreatLvl8;  // open space 4 Hausers
    
    String Lvl0 = "less than two connected peices_for start of game";
    String Lvl1 = "2 connected pieces, can be single blocked or open";
    String Lvl2 = "3 connected pieces, blocked on 1 side (right/bottom as code is setup currently)";
    String Lvl3 = "3 connected pieces, in open space";
    String Lvl4 = "2 connected peices, empty space, then a single piece"; // not checked for until hard AI
    String Lvl5 = "4 connected pieces blocked on 1 side (right/bottom as code is setup currently)";
    // here down are all gameWinning scenarios if not handled immediately
    String Lvl6 = "2 pieces, empty space, 2 pieces; gameWinner if not blocked" ; 
    String Lvl7 = "3 connected pieces, empty space, then 1+ pieces; GameWinner if not blocked";
    // this Case is an automatic loss, if the player makes the right move
    String Lvl8 = "4 connected pieces in open space";
    
    ArrayList <String> threats = new ArrayList <String>();
    ArrayList<String> availableArray = new ArrayList<>();
    int AI = 2; // AI will always be the second player making his tokens value = 2
    int opp = 1; // the player will always be first player so we can hard code it
    static final int empty = 0;
    int playerToken = 1; // ai is always second player
      

    /**
     * method checks the board for a cases of 5 in a row
     * @param theboard
     * @param playerToken
     * @return 
     */
     public boolean checkWin(int[][] theboard, int playerToken) {
         if (checkRowWin(theboard, playerToken) == true || checkColWin(theboard,playerToken) == true || checkDiagWin(theboard,playerToken)== true)  {
             return true;
         }
         return false;
     }
     
     /**
     * checks the Rows of the board to see if there are cases of 3+ in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public void checkRow(int[][] theboard) {
        //DEBUG
        System.out.println("check row activate");

        int size = theboard.length;

        for (int r = 0; r < size; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {

                if(theboard[r][c]== opp){
                    
                    threats.add("0" + "_" + r + "_" + c + "_" + 0 + "_" + 0 + "_" + (-1) + "_" + (-1) + "_"); // this is the 0 threat case and should queue the AI to make an offensive move
                }
                
                 if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == empty && theboard[r][c-1] == empty && c-1 >= 0 ) // threat level 1  - 2 in a row, open space 
                {
                    threats.add("1" + "_" + r + "_" + c + "_" + r + "_" + (c + 1) + "_" + (-1) + "_" + (-1) + "_");
                }  if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == AI && theboard[r][c-1] == empty && c-1 >= 0 ) {
                   // if (c - 1 >= 0 && theboard[r][c - 1] != AI ) // if we are not blocking the left block add as a threat
                    //{
                        threats.add("1" + "_" + r + "_" + c + "_" + r + "_" + (c + 1) + "_" + r + "_" + (c + 2) + "_");
                   // }
                }  if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == empty && theboard[r][c-1] != opp && c-1 >= 0) //threat level 7 - open space 3 in a row
                {
                    threats.add("7" + "_" + r + "_" + c + "_" + r + "_" + (c + 2) + "_" + (-1) + "_" + (-1) + "_");
                }  if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == AI && theboard[r][c-1] != opp && c-1 >= 0) //threat level 2 - 3 in a row blocked on right side
                {
                    if (c - 1 >= 0 && theboard[r][c - 1] != AI) // if we are not blocking the left block add as a threat
                    {
                        threats.add("2" + "_" + r + "_" + c + "_" + r + "_" + (c + 2) + "_" + r + "_" + (c + 3) + "_");
                    }
                } if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == opp && theboard[r][c + 4] == empty ) { // threat level 8 - 4 in a row; open space

                    threats.add("8" + "_" + r + "_" + c + "_" + r + "_" + (c + 3) + "_" + (-1) + "_" + (-1) + "_");
                }  if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == opp && theboard[r][c + 4] == AI && theboard[r][c-1] == empty && c-1 >= 0 ) //threat level 5 - 4 in a row blocked on right side         
                {
                    if (c - 1 >= 0 && theboard[r][c - 1] != AI) //if we are not blocking left block, add as threat
                    {
                        threats.add("5" + "_" + r + "_" + c + "_" + (r + 3) + "_" + (c + 3) + "_" + (r + 4) + "_" + (c + 4) + "_");
                    }

                }

            }// end inner for loop,

        } // end outer loop
      
    }

    /**
     * checks the Cols of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public void checkCol(int[][] theboard) {
        //DEBUG
        System.out.println("check col activate");
        
        int size = theboard.length;

        for (int c = 0; c < size; c++) { // moves through the rows once col -4 have been checked
            for (int r = 0; r < size - 4; r++) {

                if (theboard[r][c] == opp) {
                    
                    threats.add("0" + "_" + r + "_" + c + "_" + 0 + "_" + 0 + "_" + (-1) + "_" + (-1) + "_"); // this is the 0 threat case and should queue the AI to make an offensive move
                }

                
                if (theboard[r][c] == opp && theboard[r + 1][c] == opp && theboard[r + 2][c] == empty && theboard[r - 1][c] == empty && r - 1 >= 0) // threat level 1  - 2 in a col, open space 
                {
                    
                    threats.add("1" + "_" + r + "_" + c + "_" + (r + 1) + "_" + c + "_" + (-1) + "_" + (-1) + "_");
                }
                if (theboard[r][c] == opp && theboard[r + 1][c] == opp && theboard[r + 2][c] == AI && theboard[r - 1][c] == empty && r - 1 >= 0) {
                    // threat level 1 - 2 in a row with a block on the right
                    //if (r - 1 >= 0 && theboard[r - 1][c] == empty) // if we are not blocking the left block, add as a threat
                    //{
                    System.out.println("the cells checked" + theboard[r - 1][c]);
                    threats.add("1" + "_" + r + "_" + c + "_" + (r + 1) + "_" + c + "_" + (r + 2) + "_" + c + "_");
                    //}
                }
                if (theboard[r][c] == opp && theboard[r + 1][c] == opp && theboard[r + 2][c] == opp && theboard[r + 3][c] == empty && theboard[r - 1][c] == empty && r - 1 >= 0) {
                    //threat level 7 - open space 3 in a col
                    System.out.println("the cells checked" + theboard[r - 1][c]);
                    threats.add("7" + "_" + r + "_" + c + "_" + (r + 2) + "_" + c + "_" + (-1) + "_" + (-1) + "_");
                }
                if (theboard[r][c] == opp && theboard[r + 1][c] == opp && theboard[r + 2][c] == opp && theboard[r + 3][c] == AI && theboard[r - 1][c] == empty && r - 1 >= 0) {
                    if (r - 1 >= 0 && theboard[r - 1][c] != AI) //if we are not blocking left block, add as threat
                    //threat level 2 - 3 in a col blocked on right side
                    {
                        threats.add("2" + "_" + r + "_" + c + "_" + (r + 2) + "_" + c + "_" + (r + 3) + "_" + c + "_");
                    }
                }
                if (theboard[r][c] == opp && theboard[r + 1][c] == opp && theboard[r + 2][c] == opp && theboard[r + 3][c] == opp && theboard[r + 4][c] == empty) {
                    // threat level 8 - 4 in a col; open space
                    threats.add("8" + "_" + r + "_" + c + "_" + (r + 3) + "_" + c + "_" + (-1) + "_" + (-1) + "_");
                }
                if (theboard[r][c] == opp && theboard[r + 1][c] == opp && theboard[r + 2][c] == opp && theboard[r + 3][c] == opp && theboard[r + 4][c] == AI && theboard[r - 1][c] == empty && r - 1 >= 0) {
                    if (r - 1 >= 0 && theboard[r - 1][c] != AI) //if we are not blocking left block, add as threat
                    //threat level 5 - - 4 in a row blocked on right side
                    {
                        threats.add("5" + "_" + r + "_" + c + "_" + (r + 3) + "_" + c + "_" + (r + 4) + "_" + c + "_");
                    }
                } // endsi else ifs 
            } // end inner for loop     
        } // end outer for loop

    } // end checkCol method

    /**
     * checks the diagonals of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public void checkDiag(int[][] theboard) {
        //DEBUG
        System.out.println("check Diag activate");
        
        int size = theboard.length;

        for (int r = 0; r < size - 4; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {

                if (c > 3) { // this means we are 4 blocks in which means there is now the possiblity of a diagonal threat coming from left to right
                    //we still search from right to left but going down and left 

                    if (theboard[r][c] == opp) {
                        System.out.println("this si the check row single position check");
                        threats.add("0" + "_" + r + "_" + c + "_" + 0 + "_" + 0 + "_" + (-1) + "_" + (-1) + "_"); // this is the 0 threat case and should queue the AI to make an offensive move
                    }

                    if (theboard[r][c] == opp && theboard[r - 1][c + 1] == opp && theboard[r - 2][c + 2] == empty && theboard[r + 1][c - 1] == empty && r + 1 < size && c - 1 >= 0) {
                        // threat level 1 - 2 in a diag, open space 
                        threats.add("1" + "_" + r + "_" + c + "_" + (r - 1) + "_" + (c + 1) + "_" + (-1) + "_" + (-1) + "_");
                    }
                    if (theboard[r][c] == opp && theboard[r - 1][c + 1] == opp && theboard[r - 2][c + 2] == AI && theboard[r + 1][c - 1] == empty && r + 1 < size && c - 1 >= 0) {
                        // threat level 1 - 2 in diag blocked on left bottom
                        //if (c + 1 < size && r - 1 >= 0 && theboard[r - 1][c + 1] != AI) // if we are not blocking the top right block add as a threat
                        //{
                        threats.add("1" + "_" + r + "_" + c + "_" + (r - 1) + "_" + (c + 1) + "_" + (r - 2) + "_" + (c + 2) + "_");
                        //}
                    }
                    if (theboard[r][c] == opp && theboard[r - 1][c + 1] == opp && theboard[r - 2][c + 2] == opp && theboard[r - 3][c + 3] == empty && theboard[r + 1][c - 1] == empty && r + 1 < size && c - 1 >= 0) {
                        //threat level 7 - open space 3 in a diag
                        threats.add("7" + "_" + r + "_" + c + "_" + (r - 2) + "_" + (c + 2) + "_" + (-1) + "_" + (-1) + "_");
                    }
                    if (theboard[r][c] == opp && theboard[r - 1][c + 1] == opp && theboard[r - 2][c + 2] == opp && theboard[r - 3][c + 3] == AI && theboard[r + 1][c - 1] == empty && r + 1 < size && c - 1 >= 0) {
                        //threat level 2 - 3 in a diag blocked on left bottom side
                        //if (c + 1 < size && r - 1 >= 0 && theboard[r - 1][c + 1] != AI) // if we are not blocking the right top block add as a threat
                        //{
                            threats.add("2" + "_" + r + "_" + c + "_" + (r - 2) + "_" + (c + 2) + "_" + (r - 3) + "_" + (c + 3) + "_");
                       // }
                    }
                    if (theboard[r][c] == opp && theboard[r - 1][c + 1] == opp && theboard[r - 2][c + 2] == opp && theboard[r - 3][c + 3] == opp && theboard[r - 4][c + 4] == empty) {
                        // threat level 8 - - 4 in a diag; open space
                        threats.add("8" + "_" + r + "_" + c + "_" + (r - 3) + "_" + (c + 3) + "_" + (-1) + "_" + (-1) + "_");
                    }
                    if (theboard[r][c] == opp && theboard[r - 1][c + 1] == opp && theboard[r - 2][c + 2] == opp && theboard[r - 3][c + 3] == opp && theboard[r - 4][c + 4] == AI && theboard[r + 1][c - 1] == empty && r + 1 < size && c - 1 >= 0) {
                        //threat level 5 - 4 in a row blocked on left bottom side
                       // if (c + 1 < size && r - 1 >= 0 && theboard[r - 1][c + 1] != AI) //if we are not blocking top right block, add as threat
                        //threat level 5 
                        //{
                            threats.add("5" + "_" + r + "_" + c + "_" + (r - 3) + "_" + (c + 3) + "_" + (r - 4) + "_" + (c + 4) + "_");
                       // }
                    } // end else if's

                }

                // all these cases are for a diagonal line going down from left to right, 
                if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == empty && theboard[r - 1][c - 1] == empty && r - 1 >= 0 && c - 1 >= 0) {
                    // threat level 1 - 2 in a diag, open space 
                    threats.add("1" + "_" + r + "_" + c + "_" + (r + 1) + "_" + (c + 1) + "_" + (-1) + "_" + (-1) + "_");
                }
                if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == AI && theboard[r - 1][c - 1] == empty && r - 1 >= 0 && c - 1 >= 0) {
                        // threat level 1 - 2 in diag blocked on right
                    //if (c - 1 >= 0 && r - 1 >= 0 && theboard[r - 1][c - 1] != AI) // if we are not blocking the left block add as a threat
                    //{
                    threats.add("1" + "_" + r + "_" + c + "_" + (r + 2) + "_" + (c + 2) + "_" + (r + 3) + "_" + (c + 3) + "_");
                    //}
                }
                if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == empty && theboard[r - 1][c - 1] == empty && r - 1 >= 0 && c - 1 >= 0) {
                    //threat level 7 - open space 3 in a diag
                    threats.add("7" + "_" + r + "_" + c + "_" + (r + 2) + "_" + (c + 2) + "_" + (-1) + "_" + (-1) + "_");
                }
                if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == AI && theboard[r - 1][c - 1] == empty && r - 1 >= 0 && c - 1 >= 0) {
                    //threat level 2 - 3 in a diag blocked on right side
                    if (c - 1 >= 0 && r - 1 >= 0 && theboard[r - 1][c - 1] != AI) // if we are not blocking the left block add as a threat
                    {
                        threats.add("2" + "_" + r + "_" + c + "_" + (r + 2) + "_" + (c + 2) + "_" + (r + 3) + "_" + (c + 3) + "_");
                    }
                }
                if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == opp && theboard[r + 4][c + 4] == empty) {
                    // threat level 8 - - 4 in a diag; open space
                    threats.add("8" + "_" + r + "_" + c + "_" + (r + 3) + "_" + (c + 3) + "_" + (-1) + "_" + (-1) + "_");
                }
                if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == opp && theboard[r + 4][c + 4] == AI && theboard[r - 1][c - 1] == empty && r - 1 >= 0 && c - 1 >= 0) {
                    //threat level 5 - 4 in a row blocked on right side
                    if (c - 1 >= 0 && r - 1 >= 0 && theboard[r][c - 1] != AI) //if we are not blocking left block, add as threat
                    //threat level 5 
                    {
                        threats.add("5" + "_" + r + "_" + c + "_" + (r + 3) + "_" + (c + 3) + "_" + (r + 4) + "_" + (c + 4) + "_");
                    }
                } // end else ifs
            }// end inner for loop 
        } // end outer for loop
    }
        
        
        
    
    /**
     * based on the list of threats it handles the most severe
     * @param theboard the 2d array so it can play a marker when it finds the best position to move
     */
    public void threatDetect(int[][] theboard){
        
        int size = theboard.length;
        int startx; 
        int endx; 
        int starty; 
        int endy; 
        int blockx; // right block 
        int blocky; // right block
        String direction;  
        String highestThreat = "";
        int lvl = 0;
        int temp = 0;
        String[] split;
        
        //System.out.println("the first element in Threats list: " + threats.get(0));
        for (int i = 0; i < threats.size(); i++) { // for loop doesn't handle threats of equal level
            
            String level = threats.get(i);
            //debugging 
            System.out.println("the first element in Threats list: " + threats.get(i));
            
            String[] tempS = level.split("_");
            temp = Integer.parseInt(tempS[0]);
            if (lvl <= temp) {
                lvl = temp;
                highestThreat = threats.get(i);
            }
        }
        
        
        System.out.println("the highet threat String: " + highestThreat);
        split = highestThreat.split("_");
        // now have the highest level threat in the string set start and end points
        startx = Integer.parseInt(split[1]);
        starty = Integer.parseInt(split[2]);
        endx = Integer.parseInt(split[3]);
        endy = Integer.parseInt(split[4]);
        blockx = Integer.parseInt(split[5]);
        blocky = Integer.parseInt(split[6]);
        
        // check dimensions of the threat.
        int DeltaX = endx - startx;
        int DeltaY = endy - starty;
       if (DeltaX == 0 && DeltaY != 0) {
            direction = "col";
        } else if (DeltaY == 0 && DeltaX != 0) {
             direction = "row";
        } else if (DeltaX > 0 && DeltaY > 0){
             direction = "diagLtoR";
        }
        else if (DeltaX < 0 && DeltaY > 0){
            direction = "diagRtoL";
        }
        else
            direction = "single";

        
        /*
        if(difficulty == 1 ){
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){ //iterate through board
                    if (theboard[i][j] == 1){ // if we detect opposing player token
                        if (theboard[i][j-1] == 0 && j > 0 ){ //if the column before token on the same row is empty and in bounds, place a move
                            makeMove(i, j-1,theboard);
                        break;
                        }
                        else if (theboard[i][j+1]  == 0 && j < size-1){ //same row, token after 
                            makeMove(j, j+1,theboard);
                        break;
                        }
                        else if (theboard[i-1][j] == 0 && i > 0){ //same column, row before
                            makeMove(i-1, j,theboard);
                        break;
                        }
                        else if (theboard[i+1][j] == 0 && i < size-1){ //same column, row after
                            makeMove(i+1, j,theboard);
                        break;
                        }
                        else if (theboard[i-1][j-1] == 0 && j > 0 && i >0){
                            makeMove(i-1,j-1,theboard);
                        break;
                        }
                        else if (theboard[i+1][j+1] == 0 && j < size-1 && i < size-1){
                            makeMove(i+1, j+1,theboard);
                        break;
                        }
                        else if (theboard[i-1][j+1] == 0 && j < size-1 && i > 0){
                            makeMove(i-1,j+1,theboard);
                        break;
                        }
                        else if (theboard[i+1][j-1] == 0 && i < size-1 && j > 0){
                            makeMove(i+1, j-1,theboard);
                        break;
                        }
                  break;
                    }
                 
                } 
                
            }
        }
        
        
        else*/ if( difficulty >1){
         // we know the direction of the threat, now we check adjacent blocks
        switch(direction){
            case "col":
                if (blockx != -1 || blocky != -1) { // bottom side IS blocked; check top side
                    if (starty - 1 >= 0 && theboard[startx][starty - 1] == empty) {
                        makeMove(startx, starty - 1, theboard);
                    }
                } //the bottom side is NOT blocked; check if TOP side is
                else if (starty - 1 >= 0 && endy + 1 < size && theboard[startx][starty - 1] == AI) { // top side IS blocked
                    makeMove(endx, endy + 1, theboard); // block on bottom
                } // neither side is blocked, this dum-dum will choose top every time
                else if (starty - 1 >= 0) { // checks out of bounds condition   
                    makeMove(startx, starty - 1, theboard); // always blocks top if not blocked
                }

                break;

                
            case "row":
                if (blockx != -1 || blocky != -1) { // right side IS blocked; check left side
                    if (startx-1 >= 0 && theboard[startx-1][starty] == empty && theboard[startx-1][starty] != AI) {
                        makeMove(startx-1, starty,theboard);
                    }
                } //the right side is NOT blocked; check if left side is
                else if (startx - 1 >= 0 && endx +1 < size && theboard[startx-1][starty] == AI)  { // left side IS blocked
                    makeMove(endx + 1, endy,theboard); // block on right
                } // neither side is blocked, this dum-dum will choose left every time
                else if (startx - 1 >= 0){ // checks out of bounds condition   
                    makeMove(startx-1, starty,theboard); // always blocks left if not blocked
                }
                
                
                break;
            
                
            case "diagLtoR":
                if (blockx != -1 || blocky != -1) { // top right side is blocked; check bottom left
                    if (startx-1 >= 0 && starty-1 >= 0 && theboard[startx-1][starty-1] == empty){
                        makeMove(startx-1, starty-1,theboard); // make move bottom left
                    }    
                }
                else if (startx-1 >= 0 && starty-1 >= 0 && endx +1 <size && endy +1 < size && theboard[startx-1][starty-1] == AI){ // we're blocking bottom left
                    makeMove(endx+1, endy+1,theboard); // make move top right
                }
                else if (starty - 1 >= 0 && startx-1 >= 0){ //default - open blocks on both ends make move on top left/right
                    makeMove(startx-1, starty-1,theboard);
                }
                
                break;
                
            case "diagRtoL":
                if (blockx != -1 || blocky != -1){ // bottom left is blocked check right
                    if (startx+1 < size && starty-1 >= 0 && theboard[startx+1][starty-1] == empty){ //top right block is open make move
                        makeMove(startx+1, starty-1,theboard);
                    }
                }
                else if (startx+1 < size && starty-1 >=0 && endx-1 >=0 && endy+1 <size && theboard[startx+1][starty+1] == AI){ //we're blocking top right
                        makeMove(endx-1, endy+1,theboard); //make move bottom left
                }
                else if (starty - 1 >=0 && startx + 1 < size){ //default open blocks on both ends make move top right
                        makeMove(startx+1, starty-1,theboard);
                }
                break;
                 
            case "single": 
                /*
                if(startx+1 < size && theboard[startx+1][starty] == empty) // moves to the right of piece
                    makeMove(startx+1, starty, theboard);
                else if(startx-1 >=0 && theboard[startx-1][starty] == empty) // moves to the left of the piece
                    makeMove(startx-1, starty, theboard);
                else if (starty+1 < size && theboard[startx][starty+1] == empty) // moves below piece
                    makeMove(startx, starty+1, theboard);
                */
                makerandomMove(theboard);
                   break;   
        }
   
    }
        threats.clear();
}    
    


    /**
     * checks the Rows of the board to see if there is 5 in a row
     * @param theboard the 2d array to be scanned
     * @param playerToken
     * @return true if the board contains 5 in a row
     */
    public boolean checkRowWin(int[][] theboard, int playerToken) {

        int size = theboard.length;

        for (int r = 0; r < size; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {
                if (theboard[r][c] == playerToken && theboard[r][c + 1] == playerToken && theboard[r][c + 2] == playerToken
                        && theboard[r][c + 3] == playerToken && theboard[r][c + 4] == playerToken) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * checks the Cols of the board to see if there is 5 in a row
     * @param theboard the 2d array to be scanned
     * @param playerToken
     * @return true if the board contains 5 in a row
     */   
    public boolean checkColWin(int[][] theboard, int playerToken) {

        int size = theboard.length;

        for (int c = 0; c < size; c++) { // moves through the rows once col -4 have been checked
            for (int r = 0; r < size - r; r++) {
                if (theboard[r][c] == playerToken && theboard[r + 1][c] == playerToken && theboard[r + 2][c] == playerToken
                        && theboard[r + 3][c] == playerToken && theboard[r + 4][c] == playerToken) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * checks the diagonals of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public boolean checkDiagWin(int[][] theboard, int playerToken) {

        int size = theboard.length;

        for (int r = 0; r < size - 4; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {
                if (theboard[r][c] == playerToken && theboard[r + 1][c + 1] == playerToken && theboard[r + 2][c + 2] == playerToken
                        && theboard[r + 3][c + 3] == playerToken && theboard[r + 4][c + 4] == playerToken) {
                    return true;
                    // this checks the left and down diagnoal only when we are on the 4th col or further
                } else if (c > 3 && theboard[r][c] == playerToken && theboard[r + 1][c - 1] == playerToken
                        && theboard[r + 2][c - 2] == playerToken && theboard[r + 3][c - 3] == playerToken
                        && theboard[r + 4][c - 4] == playerToken) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * places an AI token on the board in the given value
     * @param row row of move   
     * @param col col of move
     * @param boardArray the 2d array that is the game board
     */
    private void makeMove(int row, int col, int[][] boardArray) {
        if( boardArray[row][col]!=1 && boardArray[row][col]!=2 )
            boardArray[row][col] = 2; 
        else 
            System.out.println("tried to place maker over an opponents marker" + row + " " + col);
        // for debugging purposes
   
    }

    /**
     * this method makes a move for the AI, will be called immediate after the player makes a move
     * @param board - the 2d array from gameModel 
     */
    public void aiMove(int[][] board){
        
        populateArray(board);
        
        
           
        if(checkWin(board,1)==true){
            // AI has lost, exit smoothly
        }
        else {
            if (this.difficulty == 1)
                this.executeEasy(board);
            
            checkRow(board);
            checkCol(board);
            checkDiag(board);
            threatDetect(board);
        }
          
    }
   
   
    /**
     * this method is the easy AI and makes a random move from available spots
     * @param board the game board
     */
    public void executeEasy(int[][] board){     
         makerandomMove(board);
    }
    
    
    /**
     * fills the array with all open spots on the board
     * @param original the empty board
     */
    public void populateArray(int[][] original) {
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original.length; j++) {
                if (original[i][j] == 0) {
                    String newEntry = new String(i + "_" + j + "_");
                    availableArray.add(newEntry);
                }
            }
        }
    }
    /**
     * removes an entry from the board that happens when a player makes a move
     * @param r the row
     * @param c  the col
     */
    public void removeAvailable(int r, int c) {
        String searchEntry = new String("" + r + "_" + c + "_");
        for (int k = 0; k < availableArray.size(); k++) {
            if (searchEntry.equals(availableArray.get(k))) {
                availableArray.remove(k);
            }
        }
    }
    /**
     * makes a random move by picking a random open spot and makes an AI move there
     * @param board 
     */
    public void makerandomMove(int[][]board) {
        Random randomNumber = new Random();
        int randomIndex = randomNumber.nextInt(availableArray.size() - 1);
        String grab = new String(availableArray.get(randomIndex));
        String[] splitString = grab.split("_");
        int newRow = Integer.parseInt(splitString[0]);
        int newColumn = Integer.parseInt(splitString[1]);
        makeMove(newRow, newColumn, board);
        this.removeAvailable(newRow, newColumn);
    }
    
    /**
     * method sets the difficultly of the AI, currently there is only easy and normal
     * @param level 
     */
    public void setDifficulty(String level){
        switch(level){
            
            case "Easy":
                difficulty = 1;               
                break;
            case "Normal":
                difficulty = 2;
                break;
            case "Hard": 
                difficulty = 3;
                break;
            default:
                difficulty = 1;
                break;                
        }  
        System.out.println("The difficulty from the AI class" + difficulty);
    }
    
    /**
     * This will set the Model into the controller.
     * @param model ClientModel.
     */
    public void setGModel(GameModel gmodel) {
        this.gmodel = gmodel;
    } // end setModel.
}
