package bookstore.assertions;

import bookstore.pojo.Book;
import bookstore.pojo.ErrorResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {

    private ResponseAssert(Response actual) {
        super(actual, ResponseAssert.class);
    }

    public static ResponseAssert assertThat(Response actual) {
        return new ResponseAssert(actual);
    }

    @Step("Проверить статус код ответа")
    public ResponseAssert hasStatusCode(int expectedStatusCode) {
        isNotNull();
        int actualStatusCode = actual.getStatusCode();
        Assertions.assertThat(actualStatusCode)
                .withFailMessage("Ожидаемый статус код <%s> не соответствует реальному <%s>", expectedStatusCode, actualStatusCode)
                .isEqualTo(expectedStatusCode);
        return this;
    }

    @Step("Проверить тело ответа")
    public ResponseAssert hasBody(Book expectedBook) {
        isNotNull();
        Book actualBook = actual.as(Book.class);
        Assertions.assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
        return this;
    }

    @Step("Проверить отсутствие тела ответа")
    public ResponseAssert hasNoBody() {
        isNotNull();
        String body = actual.getBody().asString();
        Assertions.assertThat(body)
                .withFailMessage("Ожидаемое пустое тело не соответствует реальному <%s>", body)
                .isEmpty();
        return this;
    }

    @Step("Проверить тело ошибки")
    public ResponseAssert hasErrorBody(ErrorResponse expectedErrorResponse) {
        isNotNull();
        ErrorResponse actualError = actual.as(ErrorResponse.class);

        Assertions.assertThat(actualError)
                .usingRecursiveComparison()
                .isEqualTo(expectedErrorResponse);

        return this;
    }
}
