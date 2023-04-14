package swt.auction.main;

import jakarta.persistence.*;
import swt.auction.entities.*;
import swt.auction.entities.embeddables.*;
import swt.auction.entities.enums.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

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
    JpaUtil.getEntityManagerFactory();
    in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: crud, insights, quit";
    System.out.println(availCmds);
    String userCmd = promptFor("");

    try {
      while (!userCmd.equals("quit")) {
        switch (userCmd) {
          case "crud" -> crud();
          case "insights" -> insights();
          default -> System.out.println("ERROR: invalid command");
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
                       " (bid insert), 6 (bid delete), 7 (add seller), 8 (remove seller), 9 (add buyer), 10 (remove " +
                       "buyer),  quit";
    System.out.println(availCmds);

    String userCmd = promptFor("");
    while (!"quit".equals(userCmd)) {
      try {
        switch (userCmd) {
          case "1" -> customerInsert();
          case "2" -> System.out.println(customerDelete() ? "Success" : "Delete failed");
          case "3" -> articleInsert();
          case "4" -> System.out.println(articleDelete() ? "Success" : "Delete failed");
          case "5" -> bidInsert();
          case "6" -> System.out.println(bidDelete() ? "Success" : "Delete failed");
          case "7" -> JpaUtil.execute(em -> {
            Customer customer = getCustomer(em);
            Article article = getArticle(em);

            article.addSeller(customer);
          });
          case "8" -> JpaUtil.execute(em -> { //TODO: Testcase - currently article is deleted
            var article = getArticle(em);

            article.removeSeller();
          });
          case "9" -> JpaUtil.execute(em -> {
            Customer customer = getCustomer(em);
            Article article = getArticle(em);

            article.addBuyer(customer);
          });
          case "10" -> JpaUtil.execute(em -> {
            var article = getArticle(em);

            article.removeBuyer();
          });
          default -> System.out.println("ERROR: invalid command");
        }
      } catch (EntityNotFoundException e) {
        System.out.println("Entity not found!");
      }
      System.out.println(availCmds);
      userCmd = promptFor("");
    }
  }

  private static Article getArticle(EntityManager em) {
    var article = new ArticleRepository(em).find(parseLong("articleId"));
    if (article == null) {
      throw new EntityNotFoundException();
    }
    return article;
  }

  private static Customer getCustomer(EntityManager em) {
    var customer = new CustomerRepository(em).find(parseLong("customerId"));
    if (customer == null) {
      throw new EntityNotFoundException();
    }
    return customer;
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
    return JpaUtil.executeWithResult(em -> new CustomerRepository(em).deleteById(parseLong("customerId")));
  }


  private static void articleInsert() {
    Article article = Article.builder()
      .name(promptFor("name"))
      .description(promptFor("description"))
      .reservePrice(parseDouble("reservePrice"))
      .hammerPrice(parseDouble("hammerPrice"))
      .auctionStartDate(parseDateTime("auctionStartDate"))
      .auctionEndDate(parseDateTime("auctionEndDate"))
      .status(parseArticleStatus())
      .build();

    JpaUtil.execute(em -> new ArticleRepository(em).save(article));
  }

  private static ArticleStatus parseArticleStatus() {
    try {
      return ArticleStatus.valueOf(promptFor("status (LISTED, AUCTION_RUNNING, SOLD, NOT_SOLD)"));
    } catch (IllegalArgumentException e) {
      System.out.println("Error during parsing! Default is used (LISTED)");
      return ArticleStatus.LISTED;
    }
  }

  private static boolean articleDelete() {
    return JpaUtil.executeWithResult(em -> new ArticleRepository(em).deleteById(parseLong("articleId")));
  }

  private static void bidInsert() {
    Bid bid = Bid.builder()
      .bid(parseDouble("bid"))
      .date(parseDateTime("date"))
      .build();
    bid.setCustomer(JpaUtil.executeWithResult(Main::getCustomer));
    bid.addArticle(JpaUtil.executeWithResult(Main::getArticle));

    JpaUtil.execute(em -> new BidRepository(em).save(bid));
  }


  private static boolean bidDelete() {
    return JpaUtil.executeWithResult(em -> new BidRepository(em).deleteById(parseLong("id")));
  }

  private static Long parseLong(String text) {
    try {
      return Long.parseLong(promptFor(text));
    } catch (NumberFormatException e) {
      System.out.println("Error during parsing!");
      return parseLong(text);
    }
  }

  private static Double parseDouble(String text) {
    try {
      return Double.parseDouble(promptFor(text));
    } catch (NumberFormatException e) {
      System.out.println("Error during parsing!");
      return parseDouble(text);
    }
  }


  private static LocalDateTime parseDateTime(String text) {
    try {
      return LocalDateTime.parse(promptFor(text + " (2007-12-03T10:15:30)"));
    } catch (DateTimeParseException e) {
      System.out.println("Error during parsing! Default is used (now)");
      return LocalDateTime.now();
    }
  }


  private static void insights() {
    String availCmds =
      "commands: 1 (findArticlesByDescriptions), 2 (getArticlePrice), 3 (getTopSellers), 4 " + "(getTopArticles), quit";
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
