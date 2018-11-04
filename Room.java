import java.util.ArrayList;
import java.util.*;


public class Room
{
   private String description;
   private HashMap<String, Item> itemsInRoom;

   public Room(String description)
   {
      this.description = description;
      itemsInRoom = new HashMap<>();
   }

   public Item getItem(String item)
   {
      if(itemsInRoom.containsKey(item))
      {
         return itemsInRoom.get(item);
      }
      else
         return null;
   }

   public void removeItem(String item)
   {
      itemsInRoom.remove(item);
   }

   public void addItem(String name, Item item)
   {
      itemsInRoom.put(name, item);
   }

   public String getDescription()
   {
      return description;
   }

   public String getLongDescription()
   {
      return "You are in " + description + "\n You notice the following items in the room: \n" + getItemString();
   }

   public String getItemString()
   {
      if(itemsInRoom.size() == 0)
      {
        return "there are no items in this room";
      }
      else
      {
          String returnString = "Items:";
          Set<String> keys = itemsInRoom.keySet();
          for(String item : keys)
          {
             returnString += " " + item;
          }
             return  returnString;


      }
   }


}
