package ru.sberbank.edu.repository;


import ru.sberbank.edu.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CarDbRepositoryImpl implements CarRepository {
    private final Connection connection;
    private static final String CREATE_CAR_SQL = "INSERT INTO car (id, model) VALUES (?,?)";
    private static final String UPDATE_CAR_SQL = "UPDATE car SET model = ? WHERE id = ?";
    private static final String SELECT_CAR_BY_ID = "SELECT * FROM car WHERE id = ?";
    private static final String SELECT_CAR_BY_MODEL = "SELECT * FROM car WHERE model = ?";
    private static final String SELECT_ALL_CAR = "SELECT * FROM car";
    private static final String DELETE_BY_ID = "DELETE FROM car WHERE id = ?";
    private static final String DELETE_ALL_CAR = "TRUNCATE TABLE car";

    private final PreparedStatement createPreStmt;
    private final PreparedStatement updatePreStmt;
    private final PreparedStatement findByIdPreStmt;
    private final PreparedStatement findByModelPreStmt;
    private final PreparedStatement findAllCars;
    private final PreparedStatement deleteById;
    private final PreparedStatement deleteAllCars;

    /**
     * Конструктор
     * @param connection - коннекшн
     * @throws SQLException
     */
    public CarDbRepositoryImpl(Connection connection) throws SQLException {
        this.connection = connection;
        this.createPreStmt = connection.prepareStatement(CREATE_CAR_SQL);
        this.updatePreStmt = connection.prepareStatement(UPDATE_CAR_SQL);
        this.findByIdPreStmt = connection.prepareStatement(SELECT_CAR_BY_ID);
        this.findByModelPreStmt = connection.prepareStatement(SELECT_CAR_BY_MODEL);
        this.findAllCars = connection.prepareStatement(SELECT_ALL_CAR);
        this.deleteById = connection.prepareStatement(DELETE_BY_ID);
        this.deleteAllCars = connection.prepareStatement(DELETE_ALL_CAR);
    }

    /**
     * Добавление или обновление данных в таблице car
     * @param car - объект машины
     * @return вновь добавленный объект
     * @throws SQLException
     */
    @Override
    public Car createOrUpdate(Car car) throws SQLException {
        Optional<Car> optCar = findById(car.getId());
        if (optCar.isEmpty()) {
            createPreStmt.setString(1, car.getId());
            createPreStmt.setString(2, car.getModel());
            createPreStmt.executeUpdate();
        } else {
            updatePreStmt.setString(1, car.getModel());
            updatePreStmt.setString(2, car.getId());
            updatePreStmt.executeUpdate();
        }
        return car;
    }

    /**
     * Добавление в БД всех элеменов коллеции
     * @param cars - коллекция объектов car
     * @return вновь добавленные эл-ты
     */
    @Override
    public Set<Car> createAll(Collection<Car> cars) {

        Set<Car> carSet = new HashSet<>();

        cars.forEach(car ->
        {
            try {
                createOrUpdate(car);
                carSet.add(car);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return carSet.isEmpty() ? Collections.emptySet() : carSet;
    }

    /**
     * Метод для вывода всех элементов таблицы
     * @return множество объектов car
     * @throws SQLException
     */
    @Override
    public Set<Car> findAll() throws SQLException {
        Set<Car> carSet = new HashSet<>();

        ResultSet resultSet = findAllCars.executeQuery();

        while (resultSet.next()) {
            Car car = new Car(resultSet.getString(1), resultSet.getString(2));
            carSet.add(car);
        }

        return carSet.isEmpty() ? Collections.emptySet() : carSet;
    }


    /**
     * Метод для поиска car по id
     * @param id - id объекта car
     * @return объект класса car
     * @throws SQLException
     */
    @Override
    public Optional<Car> findById(String id) throws SQLException {
        // validation
        int rowsCount = countRowsById(id);
        if (rowsCount > 1) {
            throw new RuntimeException("Car with id = " + id + " was found many times (" + rowsCount + ").");
        } else if (rowsCount == 0) {
            return Optional.empty();
        }

        findByIdPreStmt.setString(1, id);
        ResultSet resultSet = findByIdPreStmt.executeQuery();

        resultSet.next();
        Car car = new Car(resultSet.getString(1), resultSet.getString(2));
        return Optional.of(car);
    }

    /**
     * Удалить данные из БД по идентификатору id
     * @param id - id объекта car
     * @return булево значение (удалено или нет)
     * @throws SQLException
     */
    @Override
    public Boolean deleteById(String id) throws SQLException {
        deleteById.setString(1, id);
        return deleteById.execute();
    }

    /**
     * Удаление всех данных таблицы
     * @return булево значение (удалено или нет)
     * @throws SQLException
     */
    @Override
    public Boolean deleteAll() throws SQLException {
        return deleteAllCars.execute();
    }

    private int countRowsById(String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM car where id = ?");
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        int rowCount = 0;
        while (resultSet.next()) {
            rowCount = resultSet.getInt(1);
        }
        return rowCount;
    }

    /**
     * Поиск машин по марке
     * @param model модель объекта car
     * @return множество объектов car, подходящих под условие
     * @throws SQLException
     */
    @Override
    public Set<Car> findByModel(String model) throws SQLException {

        Set<Car> carSet = new HashSet<>();

        findByModelPreStmt.setString(1, model);
        ResultSet resultSet = findByModelPreStmt.executeQuery();

        while (resultSet.next()) {
            Car car = new Car(resultSet.getString(1), resultSet.getString(2));
            carSet.add(car);
        }

        return carSet.isEmpty() ? Collections.emptySet() : carSet;
    }
}
