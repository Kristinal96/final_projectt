package Doska.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import Doska.pages.RegistrationPage;
import Doska.api.ApiClient;
import Doska.data.User;
import Doska.data.UserGenerator;
import Doska.pages.LoginPage;
import Doska.pages.MainPage;
import io.restassured.response.ValidatableResponse;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final RegistrationPage registrationPage = new RegistrationPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;


    @Given("Пользователь зарегистрирован")
    public void userIsRegistered() {
        user = UserGenerator.generateUser();
        // Регистрируем через API
        ValidatableResponse registerResponse = apiClient.registerUser(user);

        // Сохраняем учетные данные
        savedEmail = user.getEmail();
        savedPassword = user.getPassword();

        // логирование
        System.out.println("Сохраненный email при регистрации: " + user.getEmail());
        System.out.println("Сохраненный пароль при регистрации: " + user.getPassword());
    }

    @When("Пользователь открывает страницу входа")
    public void openLoginPage() {
        mainPage.openMainPage();
        mainPage.clickLoginAndRegisterButton();
    }

    @And("Пользователь вводит свои учетные данные")
    public void enterCorrectCredentials() {
        User registerUser = new User();
        registerUser.setEmail(savedEmail);
        registerUser.setPassword(savedPassword);

        System.out.println("Введенный email при авторизации: " + registerUser.getEmail());
        System.out.println("Введенный пароль при авторизации: " + registerUser.getPassword());

        loginPage.loginUser(registerUser);

        // Получаем токен и ID
        ValidatableResponse loginResponse = apiClient.loginUser(registerUser);
        accessToken = apiClient.getAccessToken(loginResponse);
        userId = apiClient.getUserId(loginResponse);

        System.out.println("Полученный accessToken: " + accessToken);
        System.out.println("Полученный userId: " + userId);

        Hooks.setUserCredentials(accessToken, userId, apiClient);

    }

    @Then("Пользователь успешно авторизуется")
    public void userSuccessfullyLoggedIn() {
        mainPage.succsessAuthorized();
    }
}
