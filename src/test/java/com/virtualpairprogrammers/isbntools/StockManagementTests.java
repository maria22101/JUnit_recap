package com.virtualpairprogrammers.isbntools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StockManagementTests {
    ExternalISBNDataService testWebService;
    ExternalISBNDataService testDatabaseService;
    StockManager stockManager;

    //annotation @Before tells JUnit that this method to be run prior to each test.
    //if something is being instantiating here, there will be a "fresh" version prior to each test
    //we can use an object from setup() in any tests.
    @Before
    public void setup() {
        //instead of instantiating (and implementing) an object as in the first test, we ask Mockito to do it for us
        //at this point we have got a fake(or a dummy) as we have not had implementation so far:
        testWebService = mock(ExternalISBNDataService.class);
        testDatabaseService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDataBaseService(testDatabaseService);
    }

    //We are creating a TEST STUB (replacement for an object that the clause we are testing has a dependency on):

    //our StockManager class (where the business logic is implemented) has a dependency of an external service
    //(ExternalISBNDataService). In order to be able to run my test we say: "Do not use that external service,
    //rather create a mock-up of what that external service should be and use this version instead"

    //we are creating a minimal code of the interface implementation simulating a real service implementation

    //We are injecting the STUB into the clause (to override the use of the real external dependency)
    @Test
    public void testCanGetACorrectLocatorCodeUsingStubs() {
        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
            }
        };
        ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
            public Book lookup(String isbn) {
                return null;
            }
        };
        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDataBaseService(testDatabaseService);
        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        assertEquals("7396J4", locatorCode);
    }

    //the same test as above utilizing mockito library
    @Test
    public void testCanGetACorrectLocatorCodeUsingMocks() {
        String isbn = "0140177396";

        //here we give implementation to our constructed mocks constructed in the setup method:
        when(testWebService.lookup(anyString())).thenReturn(new Book(isbn, "Of Mice And Men", "J. Steinbeck"));
        when(testDatabaseService.lookup(anyString())).thenReturn(null);

        String locatorCode = stockManager.getLocatorCode(isbn);

        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        String isbn = "0140177396";

        //here we do not care what is returned, but we are interested whether lookup() is actually get called.
        when(testDatabaseService.lookup(isbn)).thenReturn(new Book("0140177396", "abc", "abc"));

        String locatorCode = stockManager.getLocatorCode(isbn);

        //we do not care what the method lookup(isbn) returns, but whether it is called by databaseService one time
        verify(testDatabaseService).lookup(isbn);
        verify(testWebService, never()).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresentInDatabase() {
        String isbn = "0140177396";

        when(testDatabaseService.lookup(isbn)).thenReturn(null);
        when(testWebService.lookup(isbn)).thenReturn(new Book("0140177396", "abc", "abc"));

        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(testDatabaseService).lookup(isbn);
        verify(testWebService).lookup(anyString());
    }
}
