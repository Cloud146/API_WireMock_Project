package bookstore.Tests;

import bookstore.BaseTest;
import bookstore.pojo.Book;
import bookstore.pojo.ErrorResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static bookstore.assertions.ResponseAssert.assertThat;

@Feature("Book Store API Mock")
@Story("Негативные кейсы")
public class NegativeTests extends BaseTest {

    @Test(description = "Ошибка при получении книги по несуществующему ID", priority = 1)
    public void testErrorGetBookById() {
        int id = 999;
        ErrorResponse expectedError = new ErrorResponse("NOT FOUND", "Книга не найдена");

        Response response = bookApiClient.getBookById(id);

        assertThat(response)
                .hasStatusCode(404)
                .hasErrorBody(expectedError);
    }

    @Test(description = "Ошибка при создании книги без обязательного поля в теле запроса", priority = 2)
    public void testErrorCreateBook() {
        Book bookToCreate = new Book(2, null, "J.R.R. Tolkien", 1954);
        ErrorResponse expectedError = new ErrorResponse("BAD REQUEST", "Ошибка при валидации данных");
        Response response = bookApiClient.createBook(bookToCreate);

        assertThat(response)
                .hasStatusCode(400)
                .hasErrorBody(expectedError);
    }

    @Test(description = "Ошибка при создании уже существующей книги", priority = 3)
    public void testErrorCreateDuplicateBook() {
        Book bookToCreate = new Book(0, "The Fellowship of the Ring", "J.R.R. Tolkien", 1954);
        ErrorResponse expectedError = new ErrorResponse("CONFLICT", "Книга с такими данными уже существует");
        Response response = bookApiClient.createBook(bookToCreate);

        assertThat(response)
                .hasStatusCode(409)
                .hasErrorBody(expectedError);
    }

    @Test(description = "Ошибка при удалении несуществующей книги", priority = 4)
    public void testErrorDeleteBook() {
        int id = 998;
        ErrorResponse expectedError = new ErrorResponse("NOT FOUND", "Книга не найдена");
        Response response = bookApiClient.deleteBook(id);

        assertThat(response)
                .hasStatusCode(404)
                .hasErrorBody(expectedError);
    }

    @Test(description = "Ошибка при обновлении несуществующей книги", priority = 5)
    public void testErrorUpdateBook() {
        int id = 997;
        Book bookUpdates = new Book(id, "Some Title", "Some Author", 2025);
        ErrorResponse expectedError = new ErrorResponse("NOT FOUND", "Книга не найдена");
        Response response = bookApiClient.updateBook(id, bookUpdates);

        assertThat(response)
                .hasStatusCode(404)
                .hasErrorBody(expectedError);
    }

    @Test(description = "Ошибка сервера", priority = 6)
    public void testServerError() {
        ErrorResponse expectedError = new ErrorResponse("SERVICE UNAVAILABLE", "В настояще время сервис недоступен");
        Response response = bookApiClient.getBookById(666);

        assertThat(response)
                .hasStatusCode(503)
                .hasErrorBody(expectedError);
    }
}
