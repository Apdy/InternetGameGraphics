package basicGame;
 
import java.io.IOException;
import java.util.Scanner;
 
public class Main {
 
        public static String[] getSearchText(Scanner sc) {
                String[] searchStrings = new String[4];
 
                System.out.println("Welcome!");
                System.out.println("Please enter a search term for background images: ");
                searchStrings[0] = sc.nextLine();
                System.out.println("Please enter a search term for your main character image: ");
                searchStrings[1] = sc.nextLine();
                System.out.println("Please enter a search term for your character weapon: ");
                searchStrings[2] = sc.nextLine();
                System.out.println("Please enter a search term for your in-game enemies: ");
                searchStrings[3] = sc.nextLine();
 
                return searchStrings;
        }
 
        public static String[] getImageURLs(TryAPIImage searcher, String[] searches) throws IOException {
                String[] imageURLs = new String[4];
                System.out.println("Getting image URLs...");
                for (int i = 0; i < 4; i++) {
                        imageURLs[i] = searcher.doSearch(searches[i]);
                }
                return imageURLs;
        }
 
        public static void main(String[] args) throws IOException {
                System.setProperty("jsse.enableSNIExtension", "false");
                Scanner sc = new Scanner(System.in);
                TryAPIImage searcher = new TryAPIImage();
                Game game = new Game();
 
                String[] searchStrings = getSearchText(sc);
                String[] imageURLs = getImageURLs(searcher, searchStrings);
 
                boolean goodImages = false;
                while (goodImages == false) {
                        System.out.println("Please check all the image URLs and ensure they are the images you want.");
                        System.out.println("Do you wish to enter any of your own URLs? (y/n)");
                        String response = sc.nextLine().toLowerCase();
                        if (response.equals("y")) {
                                System.out.println("Is this a: background image (type 1), mainchar image (type 2), or weapon image (type 3)?");
                                String type = sc.nextLine();
                                System.out.println("Please enter the url you wish to use.");
                                String newURL = sc.nextLine();
                                imageURLs[Integer.parseInt(type)] = newURL;
                        }
                        else {
                                goodImages = true;
                        }
                }
 
                game.startGame(imageURLs);
 
                sc.close();
        }
 
}
 
