# Business scenarios

This is a description of two companies which are used as a driver for how the UCommerce framework is developed.
The two companies require very different data to be stored in the UCommerce services. 

The business descriptions allows for discussion and reflection on where decisions should be made in the code in terms of business models 
in the company's code-base or in the UCommerce service definition itself. 

Not all requirements for the two companies can be made rules in the UCommerce framework, but all variations shold be possible
through implementation and injection of strategy interfaces.

Each company has a dedicated module in a client application. The reason they are in the same application is so that UCommerce should 
support multi-tenancy in a single application.

Note: for starters they will be made in two different application since a lot of the registrations are static.

___________________________________


Bob's Boat Rental
==========================================
Bob's Boat Rentail is a a small boat rental company. The company owns multiple vessels located at two different marinas.

The company has activities in a single country.

The boats are rented out using an online web-shop and receiving phone-calls directly from customers.

Business model
------------------
The business models contains two different ways of renting boats:

- one-off rentals.
  Each rental has a start and end date and time. The customer rents a specific vessel is for a specific vessel.
- subscription rental
  The customer pays a subscription amount for a class of vessel, which can be taken out of the marina for a fixed number of hours each billing cycle (month). The customer does not know which vessel will be available but is guaranteed a vessel in the same class or a better vessel will be made available.

Organisational units
----------------------
The company operates boats in 4 classes in two different marinas. One marina only has 3 classes of vessels.

Business use-cases
----------------------
- Phone rental - By calling the sales office of the company.
- Online rental - Online one-off reservation using the company's web-shop.
- Subscription creation - Customer enters relevant information on the company web-site or entered by a sales employee using an internal
- Subscription rental - customer's select the vessel they want to use using the company's web-shop. The webshop will show what is available at the selected marina on the selected date.

Customer information
-----------------------------
Boats are expensive vessels, which are dangerous to operate, and the level of detail regarding customer information is high.
This is because the vessel needs to be insured properly, and the company needs to have emergency contacts for all customers in case of an emergency.

The identity of customers must also be verifiable as they would need to be certified as being allowed to captain a vessel before renting it.
Customer can be B2B or private customers.


Frankie's Furniture Company
=================================================

Medium sized retail company with activities in multiple countries. The company has a series of stores in 5 countries with distribution centers (DC) in countries.

Business model
----------------------
The company sells products in two main ways.

 - Online purchases - customer selects products online which are fulfilled by either a store or a distribution center.

 - Store purchases - customers go to a store and selects products.
Organisational units
----------------------
The company has stores in 5 countries where private and B2B customer buys directly from.
The company has distribution centers in 3 countries. The distribution centers each supply a list of stores with goods weekly. The DCs also fulfill and ship orders created in the company's web-shop.


Business use-cases
----------------------
- Buy'n fetch - Place order online and pickup it up in the store. These orders can be paid in advance or in the store.
- Store purchase - buy directly from the store. Customers can have employees enter an online order which is paid in the store. This can be sent to the store or directly to the shipping address of the customer.

- Online purchase - buy from online shop and have it delivered to an address that the chosen carrier supports.

Customer information
----------------------
All orders require contact information to the customer.
When orders are delivered directly to the customer a delivery address is also required.
The customers can store their relevant information in the company's CRM system which makes it simpler to enter information on orders when making a purchase.
Customer can be B2B or private customers.