package swt.auction.main;

import swt.auction.entities.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.io.*;

public class Main {

  private static BufferedReader in;

  static String promptFor(String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    } catch (Exception e) {
      return promptFor(p);
    }
  }

  public static void main(String[] args) {
    in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: crud, insights, quit";
    System.out.println(availCmds);
    String userCmd = promptFor("");

    try {
      while (!userCmd.equals("quit")) {
        switch (userCmd) {
          case "crud":
            crud();
            break;

          case "insights":
            insights();
            break;

          default:
            System.out.println("ERROR: invalid command");
            break;
        }
        System.out.println(availCmds);
        userCmd = promptFor("");
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      JpaUtil.closeEntityManagerFactory();
    }
  }


  private static void crud() {
    String availCmds = "commands: 1 (customer insert), 2 (customer delete), 3 (article insert), 4 (article delete), 5" +
                       " (bid insert), 6 (bid delete), quit";
    System.out.println(availCmds);

    String userCmd = promptFor("");
    while (!"quit".equals(userCmd)) {
      switch (userCmd) {
        case "1":
          customerInsert();
          break;
        case "2":
          System.out.println(

                            );
        case "3":
          break;
        case "4":
          break;
        case "5":
          break;
        case "6":
          break;
        default:
          System.out.println("ERROR: invalid command");
          break;
      }
      System.out.println(availCmds);
      userCmd = promptFor("");
    }
  }

  private static void customerInsert() {
    Customer customer = Customer.builder()
      .firstName(promptFor("firstName"))
      .lastName(promptFor("lastName"))
      .email(promptFor("email"))
      .shippingAddress(new Address(promptFor("shippingCode"), promptFor("shippingCity"), promptFor("shippingStreet")))
      .paymentAddress(new Address(promptFor("paymentCode"), promptFor("paymentCity"), promptFor("paymentStreet")))
      .build();
    JpaUtil.execute(em -> new CustomerRepository(em).save(customer));
  }

  private static boolean customerDelete() {
    var id = Long.parseLong(promptFor("ID"));
    return JpaUtil.executeWithResult(em -> new ArticleRepository(em).deleteById(id));
  }

  private static void insights() {
    String availCmds = "commands: 1 (findArticlesByDescriptions), 2 (getArticlePrice), 3 (getTopSellers), 4 " +
                       "(getTopArticles), quit";
    System.out.println(availCmds);

    String userCmd = promptFor("");
    while (!"quit".equals(userCmd)) {
      switch (userCmd) {
        case "1":
          break;
        case "2":
          break;
        case "3":
          break;
        case "4":
          break;
        default:
          System.out.println("ERROR: invalid command");
          break;
      }
      System.out.println(availCmds);
      userCmd = promptFor("");
    }
  }

}
