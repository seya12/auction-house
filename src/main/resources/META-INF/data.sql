@formatter:off
INSERT INTO APP.CUSTOMER (ID, EMAIL, FIRSTNAME, LASTNAME, PAYMENTCITY, PAYMENTSTREET, PAYMENTCODE, SHIPPINGCITY, SHIPPINGSTREET, SHIPPINGCODE) VALUES (next value for Customer_SEQ, 'max.mustermann@a1.at', 'Max', 'Mustermann', 'Linz', 'Hafenstraße', '4020', 'Perg', 'Herrenstraße', '4320');
INSERT INTO APP.CUSTOMER (ID, EMAIL, FIRSTNAME, LASTNAME, PAYMENTCITY, PAYMENTSTREET, PAYMENTCODE, SHIPPINGCITY,SHIPPINGSTREET, SHIPPINGCODE) VALUES (next value for Customer_SEQ, 'hanna.musterfrau@gmx.at', 'Hanna', 'Musterfrau', 'Perg', 'Hafenstraße', '4320', 'Perg', 'Herrenstraße','4320');
INSERT INTO APP.CUSTOMER (ID, EMAIL, FIRSTNAME, LASTNAME, PAYMENTCITY, PAYMENTSTREET, PAYMENTCODE, SHIPPINGCITY,SHIPPINGSTREET, SHIPPINGCODE) VALUES (next value for Customer_SEQ, 'bertm@gmx.de', 'Bert', 'Muller', 'Graz', 'Hafenstraße', '4441', 'Perg', 'Herrenstraße', '4320');
INSERT INTO APP.CUSTOMER (ID, EMAIL, FIRSTNAME, LASTNAME, PAYMENTCITY, PAYMENTSTREET, PAYMENTCODE, SHIPPINGCITY,SHIPPINGSTREET, SHIPPINGCODE) VALUES (next value for Customer_SEQ, 'hm@hm.at', 'Hermann', 'Maier', 'Salzburg', 'Hafenstraße', '2020', 'Perg', 'Herrenstraße', '4320');
INSERT INTO APP.CUSTOMER (ID, EMAIL, FIRSTNAME, LASTNAME, PAYMENTCITY, PAYMENTSTREET, PAYMENTCODE, SHIPPINGCITY,SHIPPINGSTREET, SHIPPINGCODE) VALUES (next value for Customer_SEQ, 'marcel@google.at', 'Marcel', 'Hirscher', 'Karnten', 'Hafenstraße', '1010', 'Perg', 'Herrenstraße', '4320');
INSERT INTO APP.CUSTOMER (ID, EMAIL, FIRSTNAME, LASTNAME, PAYMENTCITY, PAYMENTSTREET, PAYMENTCODE, SHIPPINGCITY, SHIPPINGSTREET, SHIPPINGCODE) VALUES (next value for Customer_SEQ, 'david@bm.de', 'David', 'Alaba', 'Munich', 'Hafenstraße', '5051', 'Perg', 'Herrenstraße', '4320');
INSERT INTO APP.CUSTOMER (ID, EMAIL, FIRSTNAME, LASTNAME, PAYMENTCITY, PAYMENTSTREET, PAYMENTCODE, SHIPPINGCITY, SHIPPINGSTREET, SHIPPINGCODE) VALUES (next value for Customer_SEQ, 'xs@l.de', 'Xaver', 'Schlager', 'Leipzig', 'Hafenstraße', '90000', 'Perg', 'Herrenstraße', '4320');

INSERT INTO APP.ARTICLE (ID, AUCTIONSTARTDATE, AUCTIONENDDATE, DESCRIPTION, HAMMERPRICE, NAME, RESERVEPRICE, STATUS, BUYER_ID, SELLER_ID) VALUES (next value for Article_SEQ, '2017-09-07 10:15:30.000000000', '2022-06-18 18:22:34.017000000', 'Beste Ski', 200, 'Ski', 50, 'SOLD', 1, 101);
INSERT INTO APP.ARTICLE (ID, AUCTIONSTARTDATE, AUCTIONENDDATE, DESCRIPTION, HAMMERPRICE, NAME, RESERVEPRICE, STATUS, BUYER_ID, SELLER_ID) VALUES (next value for Article_SEQ, '2021-05-01 10:15:30.000000000', '2023-01-15 18:22:34.017000000', 'Fuuußball', 50, 'Fußball', 15, 'SOLD', 101, 51);
INSERT INTO APP.ARTICLE (ID, AUCTIONSTARTDATE, AUCTIONENDDATE, DESCRIPTION, HAMMERPRICE, NAME, RESERVEPRICE, STATUS, BUYER_ID, SELLER_ID) VALUES (next value for Article_SEQ, '2016-12-03 10:15:30.000000000', '2023-04-13 18:22:34.017901600', 'Beste Produkt', 10, 'Helm', 5, 'SOLD', 151, 51);
INSERT INTO APP.ARTICLE (ID, AUCTIONSTARTDATE, AUCTIONENDDATE, DESCRIPTION, HAMMERPRICE, NAME, RESERVEPRICE, STATUS, BUYER_ID, SELLER_ID) VALUES (next value for Article_SEQ, '2021-01-03 13:15:30.000000000', '2023-03-30 18:22:34.017000000', 'Beste Produkt', 34, 'Schuhe', 10, 'SOLD', 201, 251);
INSERT INTO APP.ARTICLE (ID, AUCTIONSTARTDATE, AUCTIONENDDATE, DESCRIPTION, HAMMERPRICE, NAME, RESERVEPRICE, STATUS, BUYER_ID, SELLER_ID) VALUES (next value for Article_SEQ, '2021-01-03 13:15:30.000000000', null, 'Biniki', null, 'Bikini', 99, 'AUCTION_RUNNING', null, 1);

INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 25, '2023-04-13 18:22:50.507087600', 1, 1);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 27, '2022-04-13 19:22:50.507000000', 1, 51);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 50, '2020-07-17 17:25:50.507000000', 1, 101);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 1, '2022-11-19 15:29:51.507000000', 151, 201);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 5, '2024-10-02 18:22:50.507000000', 201, 51);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 11, '2021-02-02 18:22:50.507000000', 1, 51);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 500, '2021-08-28 18:22:50.507000000', 51, 101);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 37, '2023-04-13 18:22:50.507087600', 101, 151);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 22, '2023-04-13 18:22:50.507087600', 51, 1);
INSERT INTO APP.BID (ID, BID, DATE, ARTICLE_ID, CUSTOMER_ID) VALUES (next value for Bid_Seq, 90, '2023-04-13 18:22:50.507087600', 151, 151);
@formatter:on