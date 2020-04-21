package com.virtualpairprogrammers.isbntools;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StockManagementTests {

    //We are creating a TEST STUB (replacement for an object that the clause we are testing has a dependency on):

    //our StockManager class (where the business logic is implemented) has a dependency of an external service
    //(ExternalISBNDataService). In order to be able to run my test we say: "Do not use that external service,
    //rather create a mock-up of what that external service should be and use this version instead"

    //we are creating a minimal code of the interface implementation simulating a real service implementation

    //We are injecting the STUB into the clause (to override the use of the real external dependency)
    @Test
    public void testCanGetACorrectLocatorCode() {
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

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        String isbn = "0140177396";

        //Mockito helps us to create a dummy class that is an implementation of our interface
        //(has default implementation of our interface)
        //that we can supply into our object (stockManager) and we can call its methods.
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        //here we do not care what is returned, but we are interested whether lookup() is actually get called.
        when(databaseService.lookup(isbn)).thenReturn(new Book("0140177396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDataBaseService(databaseService);

        String locatorCode = stockManager.getLocatorCode(isbn);

        //we do not care what the method lookup(isbn) returns, but whether it is called by databaseService one time
        verify(databaseService).lookup(isbn);
        verify(webService, never()).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresentInDatabase() {
        String isbn = "0140177396";

        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup(isbn)).thenReturn(null);
        when(webService.lookup(isbn)).thenReturn(new Book("0140177396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDataBaseService(databaseService);

        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService).lookup(isbn);
        verify(webService).lookup(anyString());
    }
}
