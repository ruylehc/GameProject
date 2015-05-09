/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author PLUCSCE
 */
public class AI {
    
    int difficulty = 0;
    int ThreatLvl1; // pair of hausers, blocked or not -- easy
    int ThreatLvl2; // 3 hausers blocked on 1 side -- easy
    int ThreatLvl3; // open space 3 Hausers -- easy
    int ThreatLvl4; // 2 hausers, empty grid, 1
    int ThreatLvl5; // 4 Hausers blocked on 1 side -- easy
    int ThreatLvl6; // 2 hausers, space, 2 hauser
    int ThreatLvl7; // 3 hausers, space, 1+ hauser
    int ThreatLvl8;  // open space 4 Hausers
    
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
    int AI = 2; // AI will always be the second player making his tokens value = 2
    int opp = 1; // the player will always be first player so we can hard code it
    static final int empty = 0;
    
    
    
    /**
     * methods sets the difficulty rating
     * 1 = easy, 2 = medium, 3 = impossible
     * @param value 
     */
    public void setDifficulty(int value){
        difficulty = value; 
        
    }
    
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

        int size = theboard.length;

        for (int r = 0; r < size; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {

                if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == empty) // threat level 1  - 2 in a row, open space 
                {
                    threats.add("1" + r + c + r + (c + 1) + (-1) + (-1));
                } else if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == AI) {
                    if (c - 1 >= 0 && theboard[r][c - 1] != AI) // if we are not blocking the left block add as a threat
                    {
                        threats.add("1" + r + c + r + (c + 1) + r + (c + 2));
                    }
                } else if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == empty) //threat level 7 - open space 3 in a row
                {
                    threats.add("7" + r + c + r + (c + 2) + (-1) + (-1));
                } else if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == AI) //threat level 2 - 3 in a row blocked on right side
                {
                    if (c - 1 >= 0 && theboard[r][c - 1] != AI) // if we are not blocking the left block add as a threat
                    {
                        threats.add("2" + r + c + r + (c + 2) + r + (c + 3));
                    }
                } else if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == opp && theboard[r][c + 4] == empty) { // threat level 8 - 4 in a row; open space

                    threats.add("8" + r + c + r + (c + 3) + (-1) + (-1));
                } else if (theboard[r][c] == opp && theboard[r][c + 1] == opp && theboard[r][c + 2] == opp && theboard[r][c + 3] == opp && theboard[r][c + 4] == AI) //threat level 5 - 4 in a row blocked on right side         
                {
                    if (c - 1 >= 0 && theboard[r][c - 1] != AI) //if we are not blocking left block, add as threat
                    {
                        threats.add("5" + r + c + (r + 3) + (c + 3) + (r + 4) + (c + 4));
                    }

                }// ende else if's
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

        int size = theboard.length;

         for (int c = 0; c < size; c++) { // moves through the rows once col -4 have been checked
            for (int r = 0; r < size - 4; r++) {

                if (theboard[r][c] == opp && theboard[r+1][c] == opp && theboard[r+2][c] == empty)
                   // threat level 1  - 2 in a col, open space 
                    threats.add("1" + r + c + (r+1) + c + (-1) + (-1) );
                else if (theboard[r][c] == opp && theboard[r+1][c] == opp && theboard[r+2][c] == AI){
                    // threat level 1 - 2 in a row with a block on the right
                    if(c-1 >= 0 && theboard[r-1][c] != AI ) // if we are not blocking the left block, add as a threat
                    threats.add("1" + r + c + (r+1) + c + (r+2) + c ); 
                }else if (theboard[r][c] == opp && theboard[r+1][c] == opp && theboard[r+2][c] == opp && theboard[r+3][c] == empty ){
                    //threat level 7 - open space 3 in a col
                    threats.add("7" + r + c + (r+2) + c + (-1) + (-1) );
                }else if (theboard[r][c] == opp && theboard[r+1][c] == opp && theboard[r+2][c] == opp && theboard[r+3][c] == AI ){
                    if(r-1 >= 0 && theboard[r-1][c] != AI ) //if we are not blocking left block, add as threat
                      //threat level 2 - 3 in a col blocked on right side
                    threats.add("2" + r + c + (r+2) + c + (r+3) + c );
                }else if (theboard[r][c] == opp && theboard[r+1][c] == opp && theboard[r+2][c] == opp && theboard[r+3][c] == opp && theboard[r+4][c] == empty ){
                    // threat level 8 - 4 in a col; open space
                    threats.add("8" + r + c + (r+3) + c + (-1) + (-1) );
                }else if (theboard[r][c] == opp && theboard[r+1][c] == opp && theboard[r+2][c] == opp && theboard[r+3][c] == opp && theboard[r+4][c] == AI){
                    if(r-1 >= 0 && theboard[r-1][c] != AI ) //if we are not blocking left block, add as threat
                    //threat level 5 - - 4 in a row blocked on right side
                    threats.add("5" + r + c + (r+3) + c + (r+4) + c );
                }// end else if's
            } // end inner for loop     
        } // end outer for loop
    } // end checkCol method

    /**
     * checks the diagonals of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public void checkDiag(int[][] theboard, int playerToken) {

        int size = theboard.length;
            
        for (int r = 0; r < size-4; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {
                
                if(c>3){ // this means we are 4 blocks in which means there is now the possiblity of a diagonal threat coming from left to right
                    //we still search from right to left but going down and left 
                if (theboard[r][c] == opp && theboard[r+1][c-1] == opp && theboard[r+2][c-2] == empty){
                    // threat level 1 - 2 in a diag, open space 
                    threats.add("1" + r + c + (r+1) + (c-1) + -1 + -1 );
                }else if (theboard[r][c] == opp && theboard[r+1][c-1] == opp && theboard[r+2][c-2] == AI  ){
                    // threat level 1 - 2 in diag blocked on left bottom
                    if(c+1 >= 0 && r-1 >= 0 && theboard[r-1][c+1] != AI ) // if we are not blocking the top right block add as a threat
                    threats.add("1" + r + c + (r+1) + (c-1) + (r+2) + (c-2) ); 
                }else if (theboard[r][c] == opp && theboard[r+1][c-1] == opp && theboard[r+2][c-2] == opp && theboard[r+3][c-3] == empty ){
                    //threat level 7 - open space 3 in a diag
                    threats.add("7" + r + c + (r+2) + (c-2) + (-1) + (-1) );
                }else if (theboard[r][c] == opp && theboard[r+1][c-1] == opp && theboard[r+2][c-2] == opp && theboard[r+3][c-3] == AI ){
                    //threat level 2 - 3 in a diag blocked on left bottom side
                    if(c+1 >= 0 && r-1 >= 0 && theboard[r-1][c+1] != AI ) // if we are not blocking the right top block add as a threat
                      
                    threats.add("2" + r + c + (r+2) + (c-2) + (r+3) + (c-3) );
                }else if (theboard[r][c] == opp && theboard[r+1][c-1] == opp && theboard[r+2][c-2] == opp && theboard[r+3][c-3] == opp && theboard[r+4][c-4] == empty ){
                    // threat level 8 - - 4 in a diag; open space
                    threats.add("8" + r + c + (r+3) + (c-3) + (-1) + (-1) );
                }else if (theboard[r][c] == opp && theboard[r+1][c-1] == opp && theboard[r+2][c-2] == opp && theboard[r+3][c-3] == opp && theboard[r+4][c-4] == AI){
                    //threat level 5 - 4 in a row blocked on left bottom side
                    if(c+1 >= 0 && r-1 >= 0 && theboard[r-1][c+1] != AI ) //if we are not blocking top right block, add as threat
                    //threat level 5 
                    threats.add("5" + r + c + (r+3) + (c-3) + (r+4) + (c-4));
                } // end else if's
     
                } else // end check left and down case
                
                
                
                
                // all these cases are for a diagonal line going down from left to right, 
                if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == empty) {
                    // threat level 1 - 2 in a diag, open space 
                    threats.add("1" + r + c + (r + 1) + (c + 1) + -1 + -1);
                } else if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == AI) {
                    // threat level 1 - 2 in diag blocked on right
                    if (c - 1 >= 0 && r - 1 >= 0 && theboard[r - 1][c - 1] != AI) // if we are not blocking the left block add as a threat
                    {
                        threats.add("1" + r + c + (r + 2) + (c + 2) + (r + 3) + (c + 3));
                    }
                } else if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == empty) {
                    //threat level 7 - open space 3 in a diag
                    threats.add("7" + r + c + (r + 3) + (c + 3) + (-1) + (-1));
                } else if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == AI) {
                    //threat level 2 - 3 in a diag blocked on right side
                    if (c - 1 >= 0 && r - 1 >= 0 && theboard[r - 1][c - 1] != AI) // if we are not blocking the left block add as a threat
                    {
                        threats.add("2" + r + c + (r + 2) + (c + 2) + (r + 3) + (c + 3));
                    }
                } else if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == opp && theboard[r + 4][c + 4] == empty) {
                    // threat level 8 - - 4 in a diag; open space
                    threats.add("8" + r + c + (r + 3) + (c + 3) + (-1) + (-1));
                } else if (theboard[r][c] == opp && theboard[r + 1][c + 1] == opp && theboard[r + 2][c + 2] == opp && theboard[r + 3][c + 3] == opp && theboard[r + 4][c + 4] == AI) {
                    //threat level 5 - 4 in a row blocked on right side
                    if (c - 1 >= 0 && r - 1 >= 0 && theboard[r][c - 1] != AI) //if we are not blocking left block, add as threat
                    //threat level 5 
                    {
                        threats.add("5" + r + c + (r + 3) + (c + 3) + (r + 4) + (c + 4));
                    }
                } // end else if's
            } // end inner for loop     
        } // end outer for loop
        

        
    }
    
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
      
        for (int i = 0; i < threats.size(); i++) { // for loop doesn't handle threats of equal level
            char level = threats.get(i).charAt(0);
            temp = (int) level;
            if (lvl < temp) {
                lvl = temp;
                highestThreat = threats.get(i);
            }
        }
        // now have the highest level threat in the string set start and end points
        startx = (int) highestThreat.charAt(1);
        endx = (int) highestThreat.charAt(2);
        starty = (int) highestThreat.charAt(3);
        endy = (int) highestThreat.charAt(4);
        blockx = (int) highestThreat.charAt(5);
        blocky = (int) highestThreat.charAt(6);
        
        // check dimensions of the threat.
        int DeltaX = endx - startx;
        int DeltaY = endy - starty;
        if (DeltaX == 0) {
            direction = "col";
        } else if (DeltaY == 0) {
             direction = "row";
        } else if (DeltaX > 0){
             direction = "diagLtoR";
        }
        else 
            direction = "diagRtoL";
        
         // we know the direction of the threat, now we check adjacent blocks
        switch(direction){
            case "col":
                if (blockx != -1 || blocky != -1) { // bottom side IS blocked; check top side
                    if (starty - 1 >= 0 && theboard[startx][starty - 1] == empty) {
                        makeMove(startx, starty - 1);
                    }
                } //the bottom side is NOT blocked; check if TOP side is
                
                if (starty - 1 >= 0 && theboard[startx][starty - 1] == AI)  { // top side IS blocked
                    makeMove(endx, endy + 1); // block on bottom
                } // neither side is blocked, this dum-dum will choose top every time
                
                if (starty - 1 >= 0){ // checks out of bounds condition   
                    makeMove(startx, starty - 1);
                }

                break;

                
            case "row":
                if (blockx != -1 || blocky != -1) { // right side IS blocked; check left side
                    if (startx-1 >= 0 && theboard[startx-1][starty] == empty) {
                        makeMove(startx-1, starty);
                    }
                } //the right side is NOT blocked; check if left side is
                if (starty - 1 >= 0 && theboard[startx][starty - 1] == AI)  { // left side IS blocked
                    makeMove(endx + 1, endy); // block on right
                } // neither side is blocked, this dum-dum will choose left every time
                if (starty - 1 >= 0){ // checks out of bounds condition   
                    makeMove(startx, starty - 1);
                }
                
                
                break;
            
                
            case "diagLtoR":
                if (blockx != -1 || blocky != -1) { // top right side is blocked; check bottom left
                    if (startx-1 >= 0 && starty-1 >= 0 && theboard[startx-1][starty-1] == empty){
                        makeMove(startx-1, starty-1); // make move bottom left
                    }    
                }
                if (startx-1 >= 0 && starty-1 >= 0 && theboard[startx-1][starty-1] == AI){ // we're blocking bottom left
                    makeMove(endx+1, endy+1); // make move top right
                }
                if (starty - 1 >= 0 && startx-1 >= 0){ //default open blocks on both ends make move bottom left
                    makeMove(startx-1, starty-1);
                }
                
                break;
                
            case "diagRtoL":
                if (blockx != -1 || blocky != -1){ // bottom left is blocked check right
                    if (startx+1 >= 0 && starty+1 >= 0 && theboard[startx+1][starty+1] == empty){ //top right block is open make move
                        makeMove(startx+1, starty+1);
                    }
                if (startx+1 >= 0 && starty+1 >=0 && theboard[startx+1][starty+1] == AI){ //we're blocking top right
                        makeMove(endx-1, endy-1); //make move bottom left
                }
                if (starty + 1 >=0 && startx + 1 >= 0){ //default open blocks on both ends make move top right
                        makeMove(startx+1, starty+1);
                }
                break;
                } 
            default: // shouldnt get here, exit
                   break;   
        }
   
    }
    
    
    
    /**
     * checks the Rows of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
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

    private void makeMove(int row, int col) {
        
        
        
    }
    
}
