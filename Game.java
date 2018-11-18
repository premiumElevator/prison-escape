/**
* @author Peter Basily
* @version 2018.11.18
* This game class contains the main method as well as the logic for all the commands.
* It creates a player class and several rooms that it stores in a hashmap called allRooms
**/
import java.util.Scanner;
import java.util.HashMap;
public class Game
{
   private static Room currentRoom; //the current room the player is in

   private static Player player1; //the player object stores inventory and stealth meter.
   private Parser parser; //the parser decodes player commands.


   private HashMap<String, Room> allRooms; //a hashmap to store all the rooms in the game.

   /**
   *The main method creates a new Game object by accepting the player's name.
   **/
   public static void main(String[] args)
   {
         String playerName;
         Scanner input = new Scanner(System.in);
         boolean quitGame = false;

         System.out.println("What is your name?");
         System.out.print(">");
         playerName = input.nextLine();

         Game game = new Game(playerName);


         System.out.println("My name is " + playerName);
         game.printHelp();


         /**
         *This is the game loop, it checks if the player quit, lost his stealth meter, or reached the end of the game.
         **/
         while(quitGame == false )
         {
            if(player1.getStealthMeter() > 0)
            {
               if(currentRoom.getName().equals("outside"))
               {
                  System.out.println("I'm FREE! I'm finally FREE!");
                  System.out.println("Uh oh, looks like I made too much noise, I better get going!");
                  quitGame = true;
                  return;
               }
               System.out.println("What Will you do?\n");
               System.out.println(currentRoom.getDescription() + "\n");
               System.out.println(currentRoom.getItemString() + "\n");
               Command command = game.parser.getCommand();
               quitGame = game.processCommand(command);

            }

            else
            {
               System.out.println("Uh oh, I got caught! Looks like it's life behind bars for the rest of my life!");
               quitGame = true;
            }
         }
   }



   /**
   *The constructor instantiates a game object, a player ojbect, and adds 2 items to the player's inventory. It then calls the createRooms() method.
   **/
   public Game(String name)
   {
      player1 = new Player(name);
      Item hammer = new Item("hammer", "it's a hammer. I should probably wrap it up in something so it doesn't make too much noise.", true);
      Item note = new Item("note from friend", "The note reads: 'Hey friend, behind the BIG BOX of obstacles in your life, you will find freedom' ", true);

      player1.addItem("hammer", hammer);
      player1.addItem("note", note);


      createRooms();

   }

   /**
   *The createRooms method creates all the rooms and some of the items inside the rooms required for progress
   **/
   public void createRooms()
   {
      parser = new Parser();
      allRooms = new HashMap<>();
      Room cell = new Room("cell", "it's my cell room.");
      Room pipeRoom = new Room("pipe-room","This looks like the room with all the pipes that deliver water to the cells. It looks like I can shimmy down this pipe to the basement, but the pipe might be too hot.");
      Room basement = new Room("basement", "It's the basement");
      Room courtYard = new Room("courtyard", "The courtyard. I'm almost free, just gotta get past these gate bars...Maybe I can bend them...");
      Room outside = new Room("outside", "I'm outside, I'm FINALLY FREE!!!");
      currentRoom = cell;

      Item blanket = new Item("blanket", "it's a dusty blanket", true);
      Item sink = new Item("sink", "it's a sink", false);
      Item poster = new Item("poster", "A poster", true);


      cell.addItem("blanket", blanket);
      cell.addItem("poster", poster);


      Item hotPipe = new Item("hotwater-pipe", "it's a hot water pipe, I can't shimmy down this pipe unless I somehow find a way to cool it", false);
      pipeRoom.addItem("hotwater-pipe", hotPipe);

      Item box = new Item("big-box", "it's a big box that seems out of place...maybe I can move it", false);
      basement.addItem("big-box", box);


      Item bars = new Item("bars", "fence bars keeping me away from sweet freedom. I might be able to bend them with something I have...", false);
      courtYard.addItem("bars", bars);

      allRooms.put("cell", cell);
      allRooms.put("pipe-room", pipeRoom);
      allRooms.put("basement", basement);
      allRooms.put("courtyard", courtYard);
      allRooms.put("outside", outside);




   }


   private boolean processCommand(Command command)
   {
      boolean wantToQuit = false;

      if(command.isUnknown()) {
           System.out.println("I don't know what you mean...");
           return false;
      }

      String commandWord = command.getCommandWord();
      if (commandWord.equals("help")) {
         printHelp();
      }
      else if (commandWord.equals("goto")) {
         goRoom(command);
      }
      else if (commandWord.equals("quit")) {
         wantToQuit = quit(command);
      }
      else if(commandWord.equals("use"))
      {
         use(command);
      }
      else if(commandWord.equals("take"))
      {
         takeItem(command);

      }
      else if(commandWord.equals("combine"))
      {
         combine(command);
      }
      else if(commandWord.equals("inventory"))
      {
         inventory();
      }
      else if(commandWord.equals("interact-with"))
      {
         interact(command);
      }
      else if(commandWord.equals("separate"))
      {
         separate(command);
      }
      else if(commandWord.equals("inspect"))
      {
         inspect(command);
      }

      return wantToQuit;
   }

   /*
   *the following methods are all the commands for the game.*
   */

   private void takeItem(Command command)
   {
      if(!command.hasSecondWord()) {
         // if there is no second word, we don't know where to go...
         System.out.println("take what?");
         return;
      }

      String name = command.getSecondWord();


      if(currentRoom.getItem(name) == null)
      {
         System.out.println("can't do that!");
         return;
      }


      if(currentRoom.getItem(name).getCanTake()==true)
      {
         player1.addItem(currentRoom.getItem(name).getName(), currentRoom.getItem(name));
         currentRoom.removeItem(name);
         System.out.println("added " + name + " to your inventory.");
         if(name.equals("poster"))
         {
            Item weakWall = new Item("crumbling wall", "A crumbling wall was hidden behind the poster, I should hammer this to get out", false);
            allRooms.get("cell").addItem("crumbling wall",weakWall);
            System.out.println(weakWall.getDescription());
            return;
         }

         return;

      }


   }

   private void printHelp()
   {
      System.out.println("Today is the day I escape from prison.");
      System.out.println("I am currently in " + currentRoom.getName());
      System.out.println("I notice the following items: \n" + currentRoom.getItemString());
      System.out.println();
      System.out.println("Your command words are:");
      parser.showCommands();
      System.out.println();
      System.out.println("The 'use' command lets you use an item from your inventory with an object in the room\n");
      System.out.println("the 'combine' command combines 2 items in your inventory\n");
      System.out.println("The 'take' command takes an item in the room (so long as you can take it)\n");
      System.out.println("the 'go to' command lets you traverse between rooms.\n");
      System.out.println("The 'interact-with' command lets you interact with an object in the room\n");
      System.out.println("The 'inventory' command lists your inventory\n");
      System.out.println("The separate commannd allows you to separate items in your inventory\n");
      System.out.println("The 'inspect' command allows you to inspect items in your inventory\n");
      System.out.println();
      System.out.println("You will need all these commands to successfully escape the prison!\n");
   }
   /**
   *The inventory method prints a string with the player's inventory.
   **/
   private void inventory()
   {

      System.out.println(player1.getInventoryString());
      return;

   }

   /**
   *The goRoom command checks if the room is a valid exit and then changes the currentRoom pointor to the room the player selected
   **/
   private void goRoom(Command command)
   {
      if(!command.hasSecondWord()) {
           //if there is no second word, we don't know where to go...
           System.out.println("Go where?");
           return;
      }

      String room = command.getSecondWord();

      //Try to leave current room.
      Room nextRoom = currentRoom.getDirection(room);

      if (nextRoom == null) {
           System.out.println("There is no exit!");
      }
      else {
           currentRoom = nextRoom;
           System.out.println(currentRoom.getLongDescription());
      }
   }
   /**
   The inspect method checks if an item is in the player's inventory, then prints the description.
   **/
   private void inspect(Command command)
   {
      if(!command.hasSecondWord()) {
         //if there is no second word, we don't know where to go...
         System.out.println("What did you want to inspect?");
         return;
      }
      String item = command.getSecondWord();

      //if the item exists in the player's inventory...
      if(player1.getInventory().containsKey(item))
      {
         System.out.println(player1.inspectItem(item));
      }
      //else tell the player they don't have the item and print the inventory.
      else
      {
         System.out.println("You don't have that item!");
         inventory();
      }
   }
   /**
   *The interact method checks if you are in the valid room and then lets you interact with items in that room.
   **/
   private void interact(Command command)
   {
      if(!command.hasSecondWord()) {
         //if there is no second word, we don't know where to go...
         System.out.println("Interact with what?");
         return;
      }

      String item = command.getSecondWord();
      //if the item returned isn't null
      if(currentRoom.getItem(item) != null)
      {
         //if you are in the basement
         if(currentRoom.getName().equals("basement"))
         {
            if(item.equals("big-box"))
            {
               System.out.println("You move the big-box to reveal a hole in the wall.");
               allRooms.get("basement").removeItem("big-box");
               Item hole = new Item("hole", "A small hole in the wall that I can crawl through", false);
               allRooms.get("basement").addItem("hole", hole);
            }
            if(item.equals("hole"))
            {
               System.out.println("You crawl through the small hole and end up in the courtyard.");
               currentRoom = allRooms.get("courtyard");
            }
         }
      }
   }
   /**
   The separate method checks for separatable items, removes them from inventory, and adds their components back.
   **/
   private void separate(Command command)
   {
      if(!command.hasSecondWord()) {
         //if there is no second word, we don't know where to go...
         System.out.println("Separate what?");
         return;
      }

      String item = command.getSecondWord();
      //if it's a valid item
      if(item.equals("blanket-hammer"))
      {
         //if the item is in your inventory
         if(player1.getInventory().containsKey("blanket-hammer"))
         {
            Item hammer = new Item("hammer", "it's a hammer. I should probably wrap it up in something so it doesn't make too much noise.", true); //recreates hammer
            Item blanket = new Item("blanket", "it's a dusty blanket", true); //recreates blanket
            player1.addItem("blanket", blanket); //adds blanket to inventory
            player1.addItem("hammer", hammer); //add hammer to inventory
            player1.removeItem("blanket-hammer"); //removes blanket-hammer from inventory
            return;

         }
         //you don't have that item...
         else
            System.out.println("You don't have that item!");
            return;
      }
      //or you can't separate it.
      else
         System.out.println("Can't do that!");
         return;
   }
   /**
   The use command lets you use an item from the player inventory with an item in the room
   **/
   private void use(Command command)
   {
      if(!command.hasSecondWord()) {
           // if there is no second word, we don't know where to go...
           System.out.println("Use what?");
           return;
      }

      String item = command.getSecondWord();

      //if the player has the item
      if (player1.getInventory().containsKey(item))
      {
         //if the room you are in is the cell
          if(currentRoom.getName().equals("cell"))
          {

             if (player1.getInventory().containsKey("poster")) //if you take the poster off the wall to reveal the crumbling wall behind it.
             {
                if(item.equals("hammer")) //if you don't combine the hammer with the towel, you lose stealth
                {
                   System.out.println("Use " + command.getSecondWord() + " with what?");
                   System.out.println(currentRoom.getItemString());
                   System.out.print(">");
                   Scanner in = new Scanner(System.in);
                   String input = in.nextLine();
                   if(input.equals("crumbling wall"))
                   {
                      System.out.println("Uh oh, I made a lot of noise, looks like a guard noticed!");
                      player1.lostStealth();
                   }


                }
                else if(item.equals("blanket-hammer")) //if you do, you destroy theh wall to reveal the pipe room
                {
                   System.out.println("Use " + command.getSecondWord() + " with what?");
                   System.out.println(currentRoom.getItemString());
                   System.out.print(">");
                   Scanner in = new Scanner(System.in);
                   String input = in.nextLine();

                   if(input.equals("crumbling wall"))
                   {
                      System.out.println("You managed to break open the wall revealing the next room. You can now freely go from the pipe-room to the Cell");
                      allRooms.get("cell").addDirection("pipe-room", allRooms.get("pipe-room"));
                      allRooms.get("pipe-room").addDirection("cell", allRooms.get("cell"));
                      currentRoom = allRooms.get("pipe-room");

                   }

                }

             }
             else
               System.out.println("can't do that!");
          }

          else if(currentRoom.getName().equals("pipe-room")) //if you're in the pipe room
          {
            if(item.equals("blanket")) //and use the blanket
            {
               System.out.println("Use " + command.getSecondWord() + " with what?");
               System.out.println(currentRoom.getItemString());
               System.out.print(">");
               Scanner in = new Scanner(System.in);
               String input = in.nextLine();

               if(input.equals("hotwater-pipe")) //on the hotwater-pipe
               {
                  System.out.println("You wrapped the blanket around the hotwater-pipe and slid down to the basement!");
                  currentRoom = allRooms.get("basement"); //you change your location to the basement
               }
            }

          }

          else if(currentRoom.getName().equals("courtyard")) //if you're in the courtyard
          {
             if(item.equals("blanket")) //and use the blanket
             {
               System.out.println("Use " + command.getSecondWord() + " with what?");
               System.out.println(currentRoom.getItemString());
               System.out.print(">");
               Scanner in = new Scanner(System.in);
               String input = in.nextLine();

                 if(input.equals("bars")) //on the bars
                 {
                    System.out.println("You wrap the blanket around the bars and start twisting. The bars bend just enough to sneak through. Good think they don't give you a lot of food in prison!");
                    currentRoom = allRooms.get("outside"); //you break free and win the game
                 }
                 else
                    System.out.println("Can't do that!");
             }

          }


        }
        else
          System.out.println("You don't have that item!");



   }

   /**
   * the combine method checks if the two valid items are in the player inventory, removes them from inventory, and creates and adds the new item to inventory.
   **/
   private void combine(Command command)
   {
      if(!command.hasSecondWord()) {
           // if there is no second word, we don't know where to go...
           System.out.println("Use what?");
           return;
      }

      String item = command.getSecondWord();

      if(player1.getInventory().containsKey(item))
      {
         if(item.equals("hammer"))
         {
            System.out.println("Combine " + command.getSecondWord() + " with what?");
            System.out.println(player1.getInventoryString());
            System.out.print(">");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if(input.equals("blanket"))
            {
               if(player1.getInventory().containsKey("blanket"))
               {
                  player1.removeItem("hammer");
                  player1.removeItem("blanket");
                  Item blanketHammer = new Item("blanket hammer", "It's a hammer covered by a blanket to make less noise", true);
                  player1.addItem("blanket-hammer", blanketHammer);
                  System.out.println("You combined both items to make the 'blanket-hammer'");
               }
            }
         }
         else if(item.equals("blanket"))
         {
            System.out.println("Combine " + command.getSecondWord() + " with what?");
            System.out.println(player1.getInventoryString());
            System.out.print(">");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if(input.equals("hammer"))
            {
               if(player1.getInventory().containsKey("hammer"))
               {
                  player1.removeItem("hammer");
                  player1.removeItem("blanket");
                  Item blanketHammer = new Item("blanket hammer", "It's a hammer covered by a blanket to make less noise", true);
                  player1.addItem("blanket-hammer", blanketHammer);
                  System.out.println("You combined both items to make the 'blanket-hammer'");
               }
            }
         }
      }


   }

   private boolean quit(Command command)
   {
      if(command.hasSecondWord()) {
           System.out.println("Quit what?");
           return false;
      }
      else {
           return true;  // signal that we want to quit
      }
   }

}
