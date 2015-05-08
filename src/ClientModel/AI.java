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
    ArrayList <String> threats = new ArrayList <String>();
    static final int P1 = 1;
    static final int P2 = 2;
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

                if (theboard[r][c] == P2 && theboard[r][c+1] == P2 && theboard[r][c+2] == empty) 
                    // threat level 1  - 2 in a row, open space 
                    threats.add("1" + r + c + r + (c+1) + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r][c+1] == P2 && theboard[r][c+2] == P1)
                    if(c-1 >= 0 && theboard[r][c-1] != P1 ) // if we are not blocking the left block add as a threat
                    threats.add("1" + r + c + r + (c+1) + r + (c+2) ); 
                else if (theboard[r][c] == P2 && theboard[r][c+1] == P2 && theboard[r][c+2] == P2 && theboard[r][c+3] == empty )
                    //threat level 7 - open space 3 in a row
                    threats.add("7" + r + c + r + (c+2) + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r][c+1] == P2 && theboard[r][c+2] == P2 && theboard[r][c+3] == P1 )
                    //threat level 2 - 3 in a row blocked on right side
                    if(c-1 >= 0 && theboard[r][c-1] != P1 ) // if we are not blocking the left block add as a threat
                    threats.add("2" + r + c + r + (c+2) + r + (c+3) );
                else if (theboard[r][c] == P2 && theboard[r][c+1] == P2 && theboard[r][c+2] == P2 && theboard[r][c+3] == P2 && theboard[r][c+4] == empty )
                    // threat level 8 - 4 in a row; open space
                    threats.add("8" + r + c + r + (c+3) + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r][c+1] == P2 && theboard[r][c+2] == P2 && theboard[r][c+3] == P2 && theboard[r][c+4] == P1)
                    //threat level 5 - 4 in a row blocked on right side         
                    if (c - 1 >= 0 && theboard[r][c - 1] != P1) //if we are not blocking left block, add as threat
                            {
                                threats.add("5" + r + c + (r + 3) + (c + 3) + (r + 4) + (c + 4));
                            }
                
            }     
        }

        
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

                if (theboard[r][c] == P2 && theboard[r+1][c] == P2 && theboard[r+2][c] == empty)
                   // threat level 1  - 2 in a col, open space 
                    threats.add("1" + r + c + (r+1) + c + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r+1][c] == P2 && theboard[r+2][c] == P1)
                    // threat level 1 - 2 in a row with a block on the right
                    if(c-1 >= 0 && theboard[r-1][c] != P1 ) // if we are not blocking the left block, add as a threat
                    threats.add("1" + r + c + (r+1) + c + (r+2) + c ); 
                else if (theboard[r][c] == P2 && theboard[r+1][c] == P2 && theboard[r+2][c] == P2 && theboard[r+3][c] == empty )
                    //threat level 7 - open space 3 in a col
                    threats.add("7" + r + c + (r+2) + c + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r+1][c] == P2 && theboard[r+2][c] == P2 && theboard[r+3][c] == P1 )
                    if(r-1 >= 0 && theboard[r-1][c] != P1 ) //if we are not blocking left block, add as threat
                      //threat level 2 - 3 in a col blocked on right side
                    threats.add("2" + r + c + (r+2) + c + (r+3) + c );
                else if (theboard[r][c] == P2 && theboard[r+1][c] == P2 && theboard[r+2][c] == P2 && theboard[r+3][c] == P2 && theboard[r+4][c] == empty )
                    // threat level 8 - 4 in a col; open space
                    threats.add("8" + r + c + (r+3) + c + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r+1][c] == P2 && theboard[r+2][c] == P2 && theboard[r+3][c] == P2 && theboard[r+4][c] == P1)
                    if(r-1 >= 0 && theboard[r-1][c] != P1 ) //if we are not blocking left block, add as threat
                    //threat level 5 - - 4 in a row blocked on right side
                    threats.add("5" + r + c + (r+3) + c + (r+4) + c );
              
            }     
        }
    }

    /**
     * checks the diagonals of the board to see if there is 5 in a row
     *
     * @param theboard the 2d array to be scanned
     * @return true if the board contains 5 in a row
     */
    public void checkDiag(int[][] theboard, int playerToken) {

        int size = theboard.length;
            
        for (int r = 0; r < size; r++) { // moves through the rows once col -4 have been checked
            for (int c = 0; c < size - 4; c++) {

                if (theboard[r][c] == P2 && theboard[r+1][c+1] == P2 && theboard[r+2][c+2] == empty)
                    // threat level 1 - 2 in a diag, open space 
                    threats.add("1" + r + c + (r+1) + (c+1) + -1 + -1 );
                else if (theboard[r][c] == P2 && theboard[r+1][c+1] == P2 && theboard[r+2][c+2] == P1)
                    // threat level 1 - 2 in diag blocked on right
                    if(c-1 >= 0 && r-1 >= 0 && theboard[r-1][c-1] != P1 ) // if we are not blocking the left block add as a threat
                    threats.add("1" + r + c + (r+2) + (c+2) + (r+3) + (c+3) ); 
                else if (theboard[r][c] == P2 && theboard[r+1][c+1] == P2 && theboard[r+2][c+2] == P2 && theboard[r+3][c+3] == empty )
                    //threat level 7 - open space 3 in a diag
                    threats.add("7" + r + c + (r+3) + (c+3) + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r+1][c+1] == P2 && theboard[r+2][c+2] == P2 && theboard[r+3][c+3] == P1 )
                    //threat level 2 - 3 in a diag blocked on right side
                    if(c-1 >= 0 && r-1 >= 0 && theboard[r-1][c-1] != P1 ) // if we are not blocking the left block add as a threat
                      
                    threats.add("2" + r + c + (r+2) + (c+2) + (r+3) + (c+3) );
                else if (theboard[r][c] == P2 && theboard[r+1][c+1] == P2 && theboard[r+2][c+2] == P2 && theboard[r+3][c+3] == P2 && theboard[r+4][c+4] == empty )
                    // threat level 8 - - 4 in a diag; open space
                    threats.add("8" + r + c + (r+3) + (c+3) + (-1) + (-1) );
                else if (theboard[r][c] == P2 && theboard[r+1][c+1] == P2 && theboard[r+2][c+2] == P2 && theboard[r+3][c+3] == P2 && theboard[r+4][c+4] == P1)
                    //threat level 5 - 4 in a row blocked on right side
                    if(c-1 >= 0 && r-1 >= 0 && theboard[r][c-1] != P1 ) //if we are not blocking left block, add as threat
                    //threat level 5 
                    threats.add("5" + r + c + (r+3) + (c+3) + (r+4) + (c+4));
              
            }     
        }
        

        
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
        } else {
             direction = "diag";
        }
         // we know the direction of the threat, now we check adjacent blocks
        switch(direction){
            case "col":
                if (blockx != -1 || blocky != -1) { // bottom side IS blocked; check top side
                    if (starty - 1 >= 0 && theboard[startx][starty - 1] == empty) {
                        makeMove(startx, starty - 1);
                    }
                } //the bottom side is NOT blocked; check if TOP side is
                if (starty - 1 >= 0 && theboard[startx][starty - 1] == P1)  { // top side IS blocked
                    makeMove(endx, endy + 1); // block on bottom
                } // neither side is blocked, this dum-dum will choose top every time
                if (starty - 1 >= 0){ // checks out of bounds condition   
                    makeMove(startx, starty - 1);
                }

                break;

                
            case "row":
                
                
                
                break;
            
                
            case "diag":
                
                
                break;
                
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
