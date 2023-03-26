package swt.auction.main;

import swt.auction.entities.*;
import swt.auction.repositories.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import java.io.*;
import java.time.format.*;

public class Main {

  static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    } catch (Exception e) {
      return promptFor(in, p);
    }
  }

  static Customer readCustomerFromCmdLine() {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String firstName = promptFor(in, "firstName");
    String lastName = promptFor(in, "lastName");
//    LocalDate dateOfBirth = LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter);

    return new Customer();
  }


  public static void main(String[] args) {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: quit, list, insert, findById, " +
                       "findByLastName, update, delete";

    System.out.println("Hibernate Employee Admin");
    System.out.println(availCmds);
    String userCmd = promptFor(in, "");

    Repository<Customer> customerRepo = new CustomerRepository(JpaUtil.getTransactionalEntityManager());

    try {

      while (!userCmd.equals("quit")) {

        switch (userCmd) {

          case "list":
            for (Customer customer : customerRepo.findAll()) {
              System.out.println(customer);
            }
            break;

          case "findById":
            System.out.println(customerRepo.find(Long.parseLong(promptFor(in, "id"))));
            break;


          case "insert":
            try {
              customerRepo.save(readCustomerFromCmdLine());
            } catch (DateTimeParseException e) {
              System.err.println("Invalid date format.");
            }

            break;

//          case "update":
//            try {
//              boolean success =
//                updateEmployee(
//                  Long.parseLong(promptFor(in, "id")),
//                  promptFor(in, "firstName"),
//                  promptFor(in, "lastName"),
//                  LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter));
//              System.out.println(success ? "employee updated" : "employee not found");
//            } catch (DateTimeParseException e) {
//              System.err.println("Invalid date format.");
//            }
//            break;

          case "delete":
            boolean success = customerRepo.deleteById(Long.parseLong(promptFor(in, "id")));
            System.out.println(success ? "employee deleted" : "employee not found");
            break;

          default:
            System.out.println("ERROR: invalid command");
            break;
        } // switch

        System.out.println(availCmds);
        userCmd = promptFor(in, "");
      } // while

    } // try
    catch (Exception ex) {
      ex.printStackTrace();
    } // catch
    finally {
      // TODO: HibernateUtil.closeSessionFactory();
    }
  }
}
