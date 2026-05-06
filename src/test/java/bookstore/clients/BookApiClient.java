package bookstore.clients;

import bookstore.pojo.Book;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class BookApiClient {

    private final RequestSpecification spec;
    private static final String BOOKS_API_PATH = "/api/books";
    private static final String BOOK_BY_ID_API_PATH = BOOKS_API_PATH + "/{id}";

    public BookApiClient() {
        this.spec = given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON);
    }

    @Step("Получить книгу по ID: {bookId}")
    public Response getBookById(int bookId) {
        return given(this.spec)
                .pathParam("id", bookId)
                .when()
                .get(BOOK_BY_ID_API_PATH)
                .then()
                .extract().response();
    }

    @Step("Создать новую книгу с названием: {bookToCreate.title}")
    public Response createBook(Book bookToCreate) {
        return given(this.spec)
                .body(bookToCreate)
                .when()
                .post(BOOKS_API_PATH)
                .then()
                .extract().response();
    }

    @Step("Обновить книгу с ID: {bookId}")
    public Response updateBook(int bookId, Book bookToUpdate) {
        return given(this.spec)
                .pathParam("id", bookId)
                .body(bookToUpdate)
                .when()
                .put(BOOK_BY_ID_API_PATH)
                .then()
                .extract().response();
    }

    @Step("Удалить книгу с ID: {bookId}")
    public Response deleteBook(int bookId) {
        return given(this.spec)
                .pathParam("id", bookId)
                .when()
                .delete(BOOK_BY_ID_API_PATH)
                .then()
                .extract().response();
    }
}