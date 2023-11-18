package ru.sberbank.edu.service;

import org.assertj.core.api.Assertions;
import org.assertj.db.type.Table;
import static org.assertj.db.api.Assertions.assertThat;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.*;
import ru.sberbank.edu.dbconnection.H2DbEmbedded;
import ru.sberbank.edu.model.Car;
import ru.sberbank.edu.repository.CarDbRepositoryImpl;
import ru.sberbank.edu.repository.CarRepository;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CarServiceImplTest {

    private CarService carService;
    private Table table;
    static H2DbEmbedded h2DbEmbedded;
    private JdbcConnectionPool dataSource;

    // Test Data
    Car car = new Car("1", "Pego");
    Car car2 = new Car("2", "Reno");
    Car car3 = new Car("3", "BMW");
    Car car4 = new Car("4", "Lada");

    // Один раз инициализирую БД перед тестами
    @BeforeAll
    static void initDb() throws SQLException {
        h2DbEmbedded = new H2DbEmbedded();
        H2DbEmbedded.initDb();
    }

    // Инициализирую сервис и коннект для тестов
    @BeforeEach
    void initAssertJDbTable() {
        try {
            CarRepository carRepository = new CarDbRepositoryImpl(H2DbEmbedded.getConnection());
            carService = new CarServiceImpl(carRepository);
            // Инициализирую datasource для пакета assertj.db
            dataSource = JdbcConnectionPool.create(h2DbEmbedded.getURL_MEM(), h2DbEmbedded.getUSER(), h2DbEmbedded.getPASSWD());
            table = new Table(dataSource, "car");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // После каждого теста очищаю таблицу и закрываю коннект
    @AfterEach
    void truncTable() throws Exception {
        dataSource.getConnection().createStatement().execute("TRUNCATE TABLE car");
        dataSource.getConnection().close();
    }

    @Test
    @DisplayName("Тест метода addCar, объект car появился в БД")
    void addCarTest() throws SQLException {
        carService.addCar(car.getId(), car.getModel());

        assertThat(table).column(0)
                .value().isEqualTo(car.getId());
        assertThat(table).column(1)
                .value().isEqualTo(car.getModel());
    }

    @Test
    @DisplayName("Тест метода editModel, у объекта car изменена модель в БД")
    void editModelTest() throws SQLException {
        carService.addCar(car.getId(), car.getModel());
        carService.editModel(car.getId(), car2.getModel());

        assertThat(table).column(0)
                .value().isEqualTo(car.getId());
        assertThat(table).column(1)
                .value().isEqualTo(car2.getModel());
    }

    @Test
    @DisplayName("Тест метода editModel, если нет элемента БД")
    void editModelNoDataTest() {
        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> carService.editModel(car.getId(), car2.getModel()));
    }

    @Test
    @DisplayName("Тест метода deleteCar, элемент удален из БД")
    void deleteCarTest() throws SQLException {
        carService.addCar(car.getId(), car.getModel());
        carService.deleteCar(car.getId());

        assertThat(table).isEmpty();
    }

    @Test
    @DisplayName("Тест метода deleteCar, id не представлен в БД")
    void deleteCarNoDataTest() throws SQLException {
        carService.deleteCar(car.getId());
        assertThat(table).isEmpty();
    }


}
