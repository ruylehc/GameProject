/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientModel;

/**
 *
 * @author PLUCSCE
 */
public class testPlayercounter {
    
    public static void main (String args[]){
    GameModel gm = new GameModel();
    int test = 10;
    for (int i=0; i<test; i++){
    boolean testcounter1first = gm.p1turncounter;
    boolean testcounter2first = gm.p2turncounter;
    gm.updateMoveCounter();
    System.out.println(testcounter1first);
    System.out.println(testcounter2first + "\n");
  
    }
}
}
