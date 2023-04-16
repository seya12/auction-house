package swt.auction.tests;

import org.junit.jupiter.api.*;
import swt.auction.entities.*;
import swt.auction.repositories.impl.*;
import swt.auction.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class BidTests extends BaseTest {
  BidRepository bidRepository;

  @Override
  protected void initRepository() {
    bidRepository = new BidRepository(entityManager);
  }

  @Test
  void findBidWhenExistsIsFound() {
    Bid bid = getDefaultBid();

    Bid fetchedBid = bidRepository.find(bid.getId());

    assertThat(bid).usingRecursiveComparison().isEqualTo(fetchedBid);
  }

  @Test
  void findBidWhenNotExistsIsNotFound() {
    Bid fetchedBid = bidRepository.find(5L);

    assertNull(fetchedBid);
  }

  @Test
  void findAllBidsWhenNoExistSizeIsZero() {
    assertThat(bidRepository.findAll().size()).isEqualTo(0);
  }

  @Test
  void findAllBidsWhenTwoExistSizeIsTwo() {
    getDefaultBid();
    getDefaultBid();

    assertThat(bidRepository.findAll().size()).isEqualTo(2);
  }

  @Test
  void saveBidsWhenNewAreFound() {
    Bid bidOne = getDefaultBid();
    Bid bidTwo = getDefaultBid();

    Bid fetchedBidOne = bidRepository.find(bidOne.getId());
    Bid fetchedBidTwo = bidRepository.find(bidTwo.getId());

    assertThat(bidOne).usingRecursiveComparison().isEqualTo(fetchedBidOne);
    assertThat(bidTwo).usingRecursiveComparison().isEqualTo(fetchedBidTwo);
  }

  @Test
  void updateBidWhenExistsIsUpdated() {
    Bid bid = getDefaultBid();
    bid.setBid(2.0);
    bidRepository.update(bid);
    JpaUtil.commitAndBegin(entityManager);
    entityManager.detach(bid);
    Bid fetchedBid = bidRepository.find(bid.getId());
    assertThat(bid).usingRecursiveComparison().isEqualTo(fetchedBid);
  }

  @Test
  void updateBidWhenNotExistsIsNotFound() {
    Bid bid = getDefaultBid();
    bid.setId(5L);
    bidRepository.update(bid);
    JpaUtil.commitAndBegin(entityManager);
    entityManager.detach(bid);
    Bid fetchedBid = bidRepository.find(bid.getId());
    assertNull(fetchedBid);
  }

  @Test
  void deleteBidWhenExistsIsDeleted() {
    Bid bid = getDefaultBid();
    bidRepository.delete(bid);
    JpaUtil.commitAndBegin(entityManager);
    entityManager.detach(bid);
    Bid fetchedBid = bidRepository.find(bid.getId());
    assertNull(fetchedBid);
  }

  @Test
  void deleteBidWhenNotExistsIsNotFound() {
    Bid bid = getDefaultBid();
    bid.setId(5L);
    bidRepository.delete(bid);
    JpaUtil.commitAndBegin(entityManager);
    entityManager.detach(bid);
    Bid fetchedBid = bidRepository.find(bid.getId());
    assertNull(fetchedBid);
  }


  private Bid getDefaultBid() {
    Bid bid = Bid.builder()
      .bid(1.0)
      .build();
    bidRepository.save(bid);
    JpaUtil.commitAndBegin(entityManager);
    entityManager.detach(bid);
    return bid;
  }


}
