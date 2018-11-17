/**
@author Peter Basily
@version 11/5/2018
This class is the player class. It has an inventory, and a stealth meter.
**/

import java.util.HashMap;
import java.util.*;


public class Player
{
   private String name;
   private int stealthMeter;
   private HashMap<String, Item> inventory;


/**
@param name -A string value of the player name
creates a player class with a name, inventory and stealth meter
**/
   public Player(String name)
   {
      this.name = name;
      this.stealthMeter = 10;
      inventory = new HashMap<>();
   }
/**
@return int value of stealth meter.
**/

   public int getStealthMeter()
   {
      return stealthMeter;
   }
/**
@param itemName is a string value for the key in the hashmap for the item
@param item is the actual item being stored in the hashmap
**/
   public void addItem(String itemName, Item item)
   {
      inventory.put(itemName, item);
   }

   public void removeItem(String itemName)
   {
      inventory.remove(itemName);
   }

/**
reduces stealth meter by 1 if you make noise
**/
   public void lostStealth()
   {
      stealthMeter--;
   }
/**
@param item is a string value of the key in the hashmap.
@return item description
**/
   public String inspectItem(String item)
   {
      if(inventory.containsKey(item))
         return " " + inventory.get(item).getDescription() + "/n";
      else
         return "no such item";
   }
   public Item getItem(String item)
   {
      return inventory.get(item);
   }
/**
@return string value of your Inventory
**/
   public String getInventoryString()
   {

       String returnString = "Inventory:";
       Set<String> keys = inventory.keySet();
       for(String item : keys)
       {
          returnString += " " + item;
       }
          return  returnString;


      }
   public HashMap getInventory()
   {
      return inventory;
   }
}
