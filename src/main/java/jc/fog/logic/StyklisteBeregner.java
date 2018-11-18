/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

/**
 *
 * @author Claus
 */
public class StyklisteBeregner
{
    // NOTE TO SELF - vi skal have en klasse/hashmap som repræsenterer styklisten...
    // Den skal overføres til udregningerne...
    
    protected static int udregnSpær(int bredde, int laengde, int haeldning)
    {
        /*
        PSEUDO:
        business rule: spær understøttes hver 3. mtr.
        hvis hældning er 0:
            samme træ som rem.
        
        
        */
        return 0;
    }
            
    protected static int udregnStolper(int bredde, int laengde, int hoejde, int remBrud)
    {
        /*
        PSEUDO:
        1 stolpe i hvert hjørne
        1 stolpe hver gang remmen brydes. (remBrud)
        Hvis skur:
        1 stolpe i 2 hjørner
        1 stolpe i hjørne, hvis skur ikke er i fuld bredde.
        1 stolpe efter dør
        1 stolpe på midt på de 2 langsider, evt. hvis over x mtr.
        Stolper i midten?
        */
        return 0;
    }
}
