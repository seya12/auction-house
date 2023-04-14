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
    var article = new ArticleRepository(em).find(promptForLong("articleId"));
    if (article == null) {
      throw new EntityNotFoundException();
    }
    return article;
  }

  private static Customer getCustomer(EntityManager em) {
    var customer = new CustomerRepository(em).find(promptForLong("customerId"));
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
    return JpaUtil.executeWithResult(em -> new CustomerRepository(em).deleteById(promptForLong("customerId")));
  }


  private static void articleInsert() {
    Article article = Article.builder()
      .name(promptFor("name"))
      .description(promptFor("description"))
      .reservePrice(promptForDouble("reservePrice"))
      .hammerPrice(promptForDouble("hammerPrice"))
      .auctionStartDate(promptForLocalDateTime("auctionStartDate"))
      .auctionEndDate(promptForLocalDateTime("auctionEndDate"))
      .status(promptForArticleStatus())
      .build();

    JpaUtil.execute(em -> new ArticleRepository(em).save(article));
  }

  private static ArticleStatus promptForArticleStatus() {
    try {
      return ArticleStatus.valueOf(promptFor("status (LISTED, AUCTION_RUNNING, SOLD, NOT_SOLD)"));
    } catch (IllegalArgumentException e) {
      System.out.println("Error during parsing! Default is used (LISTED)");
      return ArticleStatus.LISTED;
    }
  }

  private static boolean articleDelete() {
    return JpaUtil.executeWithResult(em -> new ArticleRepository(em).deleteById(promptForLong("articleId")));
  }

  private static void bidInsert() {
    Bid bid = Bid.builder()
      .bid(promptForDouble("bid"))
      .date(promptForLocalDateTime("date"))
      .build();
    bid.setCustomer(JpaUtil.executeWithResult(Main::getCustomer));
    bid.addArticle(JpaUtil.executeWithResult(Main::getArticle));

    JpaUtil.execute(em -> new BidRepository(em).save(bid));
  }


  private static boolean bidDelete() {
    return JpaUtil.executeWithResult(em -> new BidRepository(em).deleteById(promptForLong("id")));
  }

  private static Long promptForLong(String text) {
    try {
      return Long.parseLong(promptFor(text));
    } catch (NumberFormatException e) {
      System.out.println("Error during parsing! Default is used (0)");
      return 0L;
    }
  }

  private static Double promptForDouble(String text) {
    try {
      return Double.parseDouble(promptFor(text));
    } catch (NumberFormatException e) {
      System.out.println("Error during parsing! Default is used (0)");
      return 0D;
    }
  }

  private static Integer promptForInt(String text) {
    try {
      return Integer.parseInt(promptFor(text));
    } catch (NumberFormatException e) {
      System.out.println("Error during parsing! Default is used (0)");
      return 0;
    }
  }

  private static LocalDateTime promptForLocalDateTime(String text) {
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
          findArticlesByDescriptions();
          break;
        case "2":
          getArticlePrice();
          break;
        case "3":
          getTopSellers();
          break;
        case "4":
          getTopArticles();
          break;
        default:
          System.out.println("ERROR: invalid command");
          break;
      }
      System.out.println(availCmds);
      userCmd = promptFor("");
    }
  }

  private static void findArticlesByDescriptions() {
    var articles = Insights.findArticlesByDescription(
      promptFor("searchPhrase"),
      promptForDouble("maxReservePrice"),
      promptForArticleOrder());
    articles.forEach(System.out::println);
  }

  private static ArticleOrder promptForArticleOrder() {
    try {
      return ArticleOrder.valueOf(promptFor("article order (NAME, RESERVE_PRICE, HAMMER_PRICE, AUCTION_START_DATE)"));
    } catch (IllegalArgumentException e) {
      System.out.println("Error during parsing! Default is used (null)");
      return null;
    }
  }

  private static void getArticlePrice() {
    Double articlePrice = null;
    try {
      articlePrice = Insights.getArticlePrice(promptForLong("articleId"));
    } catch (ArticleNotFoundException e) {
      System.out.println("Article not found");
      return;
    }
    if (articlePrice == null) {
      System.out.println("null");
    } else {
      System.out.println(articlePrice);
    }
  }

  private static void getTopSellers() {
    var topSellers = Insights.getTopSellers(promptForInt("count"));
    topSellers.forEach(System.out::println);
  }

  private static void getTopArticles() {
    var topArticles = Insights.getTopArticles(promptForInt("count"));
    topArticles.forEach(System.out::println);
  }
}
