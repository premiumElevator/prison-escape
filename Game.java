import java.util.Scanner;
import java.util.*;
public class Game
{
   private static Room currentRoom;

   private static Player player1;
   private Parser parser;


   private HashMap<String, Room> allRooms;

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



         while(quitGame == false )
         {
            if(player1.getStealthMeter() > 0)
            {
               if(currentRoom.getName().equals("outside"))
               {
                  System.out.println("I'm FREE! I'm finally FREE!");
                  System.out.println("Uh oh, looks like I made too much noise, I better get going!");
                  quitGame = true;
               }
               System.out.println("What Will you do?");
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




   public Game(String name)
   {
      player1 = new Player(name);
      Item hammer = new Item("hammer", "it's a hammer. I should probably wrap it up in something so it doesn't make too much noise.", true);
      Item note = new Item("note from friend", "The note reads: 'Hey friend, behind the BIG BOX of obstacles in your life, you will find freedom' ", true);

      player1.addItem("hammer", hammer);
      player1.addItem("note", note);


      createRooms();

   }


   public void createRooms()
   {
      parser = new Parser();
      allRooms = new HashMap<>();
      Room cell = new Room("cell", "My cell room. Contains a sink,a bed with a dusty cover, and a Shawshank Redemption poster on the wall.");
      Room pipeRoom = new Room("pipe room","This looks like the room with all the pipes that deliver water to the cells. It looks like I can shimmy down this pipe to the basement, but the pipe might be too hot.");
      Room basement = new Room("basement", "There is an oddly misplaced box in the corner of the room. Wait, what did my note say again...");
      Room courtYard = new Room("court yard", "The courtyard. I'm almost free, just gotta get past these gate bars...Maybe I can bend them...");
      Room outside = new Room("outside", "I'm outside, I'm FINALLY FREE!!!");
      currentRoom = cell;

      Item blanket = new Item("blanket", "it's a dusty blanket", true);
      Item sink = new Item("sink", "it's a sink", false);
      Item poster = new Item("poster", "A poster", true);
      //Item weakWall = new Item("crumbling wall", "A crumbling wall hidden behind the poster, I should hammer this to get out", false);

      cell.addItem("blanket", blanket);
      cell.addItem("sink", sink);
      cell.addItem("poster", poster);
      //cell.addItem("crumbling wall", weakWall);

      Item hotPipe = new Item("hot water pipe", "it's a hot water pipe, I can't shimmy down this pipe unless I somehow find a way to cool it", false);
      pipeRoom.addItem("hot water pipe", hotPipe);

      Item box = new Item("big box", "it's a big box that seems out of place...maybe I can move it", false);
      basement.addItem("big box", box);


      Item bars = new Item("fence bars", "fence bars keeping me away from sweet freedom. I might be able to bend them with something I have...", false);
      courtYard.addItem("fence bars", bars);

      allRooms.put("cell", cell);
      allRooms.put("pipe room", pipeRoom);
      allRooms.put("basement", basement);
      allRooms.put("court yard", courtYard);
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
      else if (commandWord.equals("go to")) {
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
      //work in progress
      // else if(commandWord.equals("combine"))
      // {
      //    combine(command);
      // }
      // else if(commandWord.equals("interact with"))
      // {
      //    interact(command);
      // }
      // else command not recognised.
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
      System.out.println("I am currently in my prison cell.");
      System.out.println(allRooms.get("cell").getDescription());
      System.out.println();
      System.out.println("Your command words are:");
      parser.showCommands();
      System.out.println();
      System.out.println("The 'use' command lets you use an item from your inventory with an object in the room\n");
      System.out.println("the 'combine' command combines 2 items in your inventory\n");
      System.out.println("The 'take' command takes an item in the room (so long as you can take it)\n");
      System.out.println("the 'go to' command lets you traverse between rooms.\n");
      System.out.println("The 'interact with' command lets you interact with an object in the room\n");
      System.out.println("You will need all these commands to successfully escape the prison!\n");
   }

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
//not completely done, work in progress.
   private void use(Command command)
   {
      if(!command.hasSecondWord()) {
           // if there is no second word, we don't know where to go...
           System.out.println("Use what?");
           return;
      }

      String item = command.getSecondWord();


      if (player1.getInventory().containsKey(item))
      {
          if(currentRoom.getName().equals("cell"))
          {

             if (player1.getInventory().containsKey("poster"))
             {
                if(item.equals("hammer"))
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
                else if(item.equals("blanket hammer"))
                {
                   System.out.println("Use " + command.getSecondWord() + " with what?");
                   System.out.println(currentRoom.getItemString());
                   System.out.print(">");
                   Scanner in = new Scanner(System.in);
                   String input = in.nextLine();

                   if(input.equals("crumbling wall"))
                   {
                      System.out.println("You managed to break open the wall revealing the next room. You can now freely go from the Pipe Room to the Cell");
                      allRooms.get("cell").addDirection("pipe room", allRooms.get("pipe Room"));
                      allRooms.get("pipe room").addDirection("cell", allRooms.get("cell"));
                      currentRoom = allRooms.get("pipe room");

                   }

                }

             }
             else
               System.out.println("can't do that!");
          }

          else if(currentRoom.getName().equals("pipe room"))
          {
            if(item.equals("blanket"))
            {
               System.out.println("Use " + command.getSecondWord() + " with what?");
               System.out.println(currentRoom.getItemString());
               System.out.print(">");
               Scanner in = new Scanner(System.in);
               String input = in.nextLine();

               if(input.equals("hot water pipe"))
               {
                  System.out.println("You wrapped the blanket around the hot water pipe and slid down to the basement!");
                  currentRoom = allRooms.get("basement");
               }
            }

          }

          else if(currentRoom.getName().equals("court yard"))
          {
             if(item.equals("blanket"))
             {
                System.out.println("Use " + command.getSecondWord() + " with what?");
               System.out.println(currentRoom.getItemString());
               System.out.print(">");
               Scanner in = new Scanner(System.in);
               String input = in.nextLine();

                 if(input.equals("bars"))
                 {
                    System.out.println("You wrap the blanket around the bars and start twisting. The bars bend just enough to sneak through. Good think they don't give you a lot of food in prison!");
                    currentRoom = allRooms.get("outside");
                 }
                 else
                    System.out.println("Can't do that!");
             }

          }


        }
        else
          System.out.println("You don't have that item!");



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
