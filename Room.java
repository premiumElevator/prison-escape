/**
*@author Peter Basily
*@version 2018.11.18
**/
import java.util.HashMap;
import java.util.Set;

/**
the room object stores items, the room name, and room description.
**/
public class Room
{
   private String description, name;
   private HashMap<String, Item> itemsInRoom;
   private HashMap<String, Room> connectedRooms;

   /**
   @param name the room name
   @param description the room description
   **/
   public Room(String name, String description)
   {
      this.name = name;
      this.description = description;
      itemsInRoom = new HashMap<>();
      connectedRooms = new HashMap<>(); //exits for the room
   }
   /**
   adds an item to the room.
   @param name name of the item
   @param item the item you want to put in the room.
   **/
   public void addItem(String name, Item item)
   {
      itemsInRoom.put(name, item);
   }
   /**
   removes an item in the room
   @param item the key for the item you want to remove in the HashMap
   **/
   public void removeItem(String item)
   {
      itemsInRoom.remove(item);
   }
   /**
   gets an item in the room
   @param item is the key of the item in the hashmap that you want to remove
   **/
   public Item getItem(String item)
   {
      if(itemsInRoom.containsKey(item))
      {
         return itemsInRoom.get(item);
      }
      else
      return null;
   }
   /**
   @param name name of the room is the key in the hashmap for that room
   returns the room the player wants to go to.
   **/
   public Room getDirection(String name)
   {
      return connectedRooms.get(name);
   }
   //adds a new room in the connected rooms.
   public void addDirection(String direction, Room room)
   {
      connectedRooms.put(direction, room);
   }
   //returns a string description of the room
   public String getDescription()
   {
      return description;
   }
   //returns the name of the room
   public String getName()
   {
      return name;
   }
   //returns the name, description, and item string of the room.
   public String getLongDescription()
   {
      return "You are in the" + name + "\n" + description + "\n You notice the following items in the room: \n" + getItemString();
   }
   //returns a string with all the items in the room
   public String getItemString()
   {
      if(itemsInRoom.size() == 0)
      {
        return "there are no items in this room";
      }
      else
      {
          String returnString = "Items: ";
          Set<String> keys = itemsInRoom.keySet();
          for(String item : keys)
          {
             returnString += item + ", ";
          }
             return  returnString;


      }
   }
   //returns a string with all the exits of the room. 
   public String getDirectionString()
   {
      if(connectedRooms.size() == 0)
      {
        return "There seems to be nowhere to go...";
      }
      else
      {
          String returnString = "Directions: ";
          Set<String> keys = connectedRooms.keySet();
          for(String item : keys)
          {
             returnString += item + ", ";
          }
             return  returnString;


      }
   }



}
