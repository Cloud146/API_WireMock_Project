package bookstore.Tests;

import bookstore.BaseTest;
import bookstore.pojo.Book;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static bookstore.assertions.ResponseAssert.assertThat;

@Feature("Book Store API Mock")
@Story("Позитивные кейсы")
public class PositiveTests extends BaseTest {

    @Test(description = "Успешное получение книги по ID", priority = 1)
    public void testGetBookById() {
        Book expectedBook = new Book(1, "The Fellowship of the Ring", "J.R.R. Tolkien", 1954);

        Response response = bookApiClient.getBookById(expectedBook.getId());

        assertThat(response)
                .hasStatusCode(200)
                .hasBody(expectedBook);
    }

    @Test(description = "Успешное создание новой книги", priority = 2)
    public void testCreateBook() {
        Book bookToCreate = new Book(2, "The Two Towers", "J.R.R. Tolkien", 1954);

        Response response = bookApiClient.createBook(bookToCreate);

        assertThat(response)
                .hasStatusCode(201)
                .hasBody(bookToCreate);
    }

    @Test(description = "Успешное обновление существующей книги", priority = 3)
    public void testUpdateBook() {
        int bookIdToUpdate = 1;
        Response getBookResponse = bookApiClient.getBookById(bookIdToUpdate);
        Book bookWithUpdates = getBookResponse.as(Book.class);
        bookWithUpdates.setTitle("The Fellowship of the Ring (Anniversary Edition)");
        bookWithUpdates.setYear(2004);

        Book expectedBook = new Book(1, "The Fellowship of the Ring (Anniversary Edition)", "J.R.R. Tolkien", 2004);

        Response response = bookApiClient.updateBook(bookIdToUpdate, bookWithUpdates);

        assertThat(response)
                .hasStatusCode(200)
                .hasBody(expectedBook);
    }

    @Test(description = "Успешное удаление книги по ID", priority = 4)
    public void testDeleteBook() {
        int bookIdToDelete = 10;
        Response deleteResponse = bookApiClient.deleteBook(bookIdToDelete);
        assertThat(deleteResponse)
                .hasStatusCode(204)
                .hasNoBody();
    }
}
