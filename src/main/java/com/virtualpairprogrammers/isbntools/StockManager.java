package com.virtualpairprogrammers.isbntools;

public class StockManager {
    private ExternalISBNDataService webService;
    private ExternalISBNDataService dataBaseService;

    public void setWebService(ExternalISBNDataService webService) {
        this.webService = webService;
    }

    public void setDataBaseService(ExternalISBNDataService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    //the method returns a unique internal "locator code" for the book made up of:
    //last 4 digits of ISBN number; first initial of the author surname and number of words in the title.
    public String getLocatorCode(String isbn) {
        Book book = dataBaseService.lookup(isbn);
        if(book == null) {
           book = webService.lookup(isbn);
        }
        StringBuilder locator = new StringBuilder();
        locator.append(isbn.substring(isbn.length() - 4));
        locator.append(book.getAuthor().substring(0, 1));
        locator.append(book.getTitle().split(" ").length);

        return locator.toString();
    }
}
