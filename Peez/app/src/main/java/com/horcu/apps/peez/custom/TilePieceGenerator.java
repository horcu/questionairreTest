package com.horcu.apps.peez.custom;

/**
 * Created by hcummings on 2/2/2016.
 */
public class TilePieceGenerator {

//    piece GF - 3 total
//
//    piece BA - 5 total
//
//    piece MT - 6 total
//
//    piece GH - 4 total
//
//    piece MO - 18 total

//    \\Psuedo//
//
//    prerequisite:
//            1. Make a map with the List name as the key and a value representing the amount of tiles possible for this list as the value [GFList, 3 ; BAList, 5 etc...]
//
//            for each spot [0-35]
//            for spot 0
//    a  select randomly from a sea of number [1,2,3,4,5]  => each number represents a type of piece {1 reps piece aliased as GF}
//    b     number is 1
//    c	      set tile one to use a GF piece
//    d		      reduce GFList value by one [GFList.value --]
//    e			      check for lists with at least one item left. {If the list is empty then no more tiles can use that piece}
//    f				        if list is empty remove number representing list [1]
//    repeat step a without this number in the list [2,3,4,5]

    //Write code here

    //a. BuildNumbersList() - this is the list of numbers to choose from randomly to determine the type of piece to occupy the given tile

    //b.	BuildMap() - this will build the map that holds the list name as the key and its corresponding amount of spaces to occupy as the value

    //c. GetRandomNumber() - Choose number randomly when given the specific numbers in range

    //g. RemoveOneFromListValue() - When passed the list it will reduce the amount of remaining spots to occupy by one

    //e. CheckEnsureListsHaveItems() - Checks to ensure that each list in the map has at least one space left to occupy

    //f. RemoveListNumberFromOptions() - if the value in the map for amy list is 0 then remove that item from the map
}
