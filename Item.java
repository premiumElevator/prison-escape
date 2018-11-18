/**
@author Peter Basily
@version 2018.11.18
**/

public class Item
{
   private String name, description; //name and description of the item

   private boolean canTake; //boolean declaring whether the player can add the item to their inventory

   public Item(String name, String description, boolean canTake)
   {
      this.name = name;
      this.description = description;
      this.canTake = canTake;

   }
   //returns the item name
   public String getName()
   {
      return name;
   }
   //returns the item description
   public String getDescription()
   {
      return description;
   }
   //returns the cantake boolean
   public boolean getCanTake()
   {
      return canTake;
   }
}
